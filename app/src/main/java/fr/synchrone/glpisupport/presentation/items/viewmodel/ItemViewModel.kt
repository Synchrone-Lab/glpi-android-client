package fr.synchrone.glpisupport.presentation.items.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.core.ViewState
import fr.synchrone.glpisupport.data.model.local.devices.DeviceHistoryDb
import fr.synchrone.glpisupport.data.model.remote.items.*
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import fr.synchrone.glpisupport.data.model.remote.items.ProfileApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.home.HomeViewModel
import fr.synchrone.glpisupport.presentation.items.*
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.*
import fr.synchrone.glpisupport.presentation.utilities.functions.DateUtilities
import fr.synchrone.glpisupport.presentation.utilities.functions.ItemsUtilities
import fr.synchrone.glpisupport.presentation.utilities.functions.toyyyyMMddString
import fr.synchrone.glpisupport.presentation.utilities.functions.toyyyyMMddDate
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerElement
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

/**
 * All informations an PickerComposable should know to correctly display elements.
 *
 * This is a map where :
 * Key is a PickerElement, this represent a section of the picker.
 * Values are a list of PickerElement, this represents all elements to display inside a section.
 */
typealias PickersElementsGrouped = Map<PickerElement, List<PickerElement>>

/**
 * View model associated to ItemComposable.
 *
 * @param itemType Type of the item. Should be one of the different ItemApiCompanionObject typeName property
 *
 * @see ItemComposable
 * @see ItemApiCompanionObject
 */
@SuppressLint("StaticFieldLeak")
class ItemViewModel @AssistedInject constructor(
    @Assisted("itemType") val itemType: String,
    @Assisted("itemId") val itemId: Int,
    @Assisted("isItemCreation") val isItemCreation: Boolean,
    @Assisted("overrideSerial") val overrideSerial: String?,
    @ApplicationContext private val context: Context,
    private val itemsUseCase: ItemsUseCase
): ViewModel() {

    enum class Tab {
        ITEM_INFOS,
        INFO_COMS;

        fun displayableName(context: Context): String = when (this) {
            ITEM_INFOS -> context.getString(R.string.item_tab_infos)
            INFO_COMS -> context.getString(R.string.item_tab_infocoms)
        }
    }

    private enum class Errors {
        UNKNOWN;

        fun message(context: Context): String = when (this) {
            UNKNOWN -> context.getString(R.string.unknown_error_happened)
        }
    }

    private var helper: ItemViewModelHelper

    private var currentItem: DeviceApi? = null

    private var _selectedTab: MutableLiveData<Tab> = MutableLiveData(Tab.ITEM_INFOS)
    val selectedTab: LiveData<Tab> get() = _selectedTab
    val allTabs get() = Tab.values().filter {
        val currentItemRef = currentItem
        if (currentItemRef != null && helper.areInfosComsEnabled(item = currentItemRef))
            true
        else
            it.name != Tab.INFO_COMS.name
    }

    private val _fetchingErrorMessage: MutableLiveData<String?> = MutableLiveData()
    val fetchingErrorMessage: LiveData<String?> get() = _fetchingErrorMessage
    val isFetchingErrorDialogVisible: LiveData<Boolean> get() = _fetchingErrorMessage.map { it != null }

    private val _savingErrorMessage: MutableLiveData<String?> = MutableLiveData()
    val savingErrorMessage: LiveData<String?> get() = _savingErrorMessage
    val isSavingErrorMessageVisible: LiveData<Boolean> get() = _savingErrorMessage.map { it != null }

    val saveButtonString = if (isItemCreation) context.getString(R.string.save_button_create) else context.getString(R.string.save_button_edit)

    private var _currentItemName: MutableLiveData<String> = MutableLiveData("")
    val currentItemName: LiveData<String> get() = _currentItemName

    private var entities: MutableList<EntityApi> = mutableListOf()
    private var profiles: MutableList<ProfileApi> = mutableListOf()
    private var superAdminProfilesUser: MutableList<ProfileUserApi> = mutableListOf()

    private val _locations: MutableLiveData<List<LocationApi>> = MutableLiveData()
    val locationsByEntity: LiveData<PickersElementsGrouped> get() = _locations.map { locations -> groupItemsByEntity(locations) }

    private val _suppliers: MutableLiveData<List<SupplierApi>> = MutableLiveData()
    val suppliersByEntity: LiveData<PickersElementsGrouped> get() = _suppliers.map { locations -> groupItemsByEntity(locations) }

    private val _users: MutableLiveData<List<UserApi>> = MutableLiveData(listOf())
    private val _superAdminUsers: LiveData<List<UserApi>> get() = _users.map { users ->
        return@map users.filter { user ->
            val currentItem = currentItem ?: return@filter false
            val entities = entities
            val profiles = profiles
            if (entities.isEmpty()) return@filter false
            if (profiles.isEmpty()) return@filter false

            val superAdminProfileUserOfCurrentEntity = superAdminProfilesUser.filter { it.entityId == currentItem.entityId }
            val superAdminProfileUserOfCurrentEntityUsersIds = superAdminProfileUserOfCurrentEntity.map { it.userId }
            if (superAdminProfileUserOfCurrentEntityUsersIds.contains(user.id)) {
                return@filter true
            } else { //we check ancestors
                val ancestorsEntitiesIds = entities.first { it.entityId == currentItem.entityId }.ancestorsCache ?: listOf()
                val superAdminProfilesUserOfAncestors = superAdminProfilesUser.filter { ancestorsEntitiesIds.contains(it.entityId) && it.isRecursive }
                val superAdminProfilesUserOfAncestorsUsersIds = superAdminProfilesUserOfAncestors.map { it.userId }
                return@filter superAdminProfilesUserOfAncestorsUsersIds.contains(user.id)
            }
        }
    }
    val usersByEntity: LiveData<PickersElementsGrouped> get() = _users.map { users -> groupItemsByEntity(users, name = { getUserApiDisplayName(user = it) }) }
    val superAdminUsersByEntity: LiveData<PickersElementsGrouped> get() = _superAdminUsers.map { users -> groupItemsByEntity(users, name = { getUserApiDisplayName(user = it) }) }

    private val _states: MutableLiveData<List<StateApi>> = MutableLiveData()
    val statesByEntity: LiveData<PickersElementsGrouped> get() = _states.map { states -> groupItemsByEntity(states) }

    private val _types: MutableLiveData<List<EntitledApi>> = MutableLiveData()
    val typesByEntity: LiveData<PickersElementsGrouped> get() = _types.map { groupEntitiesByNothing(it) }

    private val _manufacturers: MutableLiveData<List<ManufacturerApi>> = MutableLiveData()
    val manufacturersByEntity: LiveData<PickersElementsGrouped> get() = _manufacturers.map { groupEntitiesByNothing(it) }

    private val _models: MutableLiveData<List<EntitledApi>> = MutableLiveData()
    val modelsByEntity: LiveData<PickersElementsGrouped> get() = _models.map { groupEntitiesByNothing(it) }

    private val _groups: MutableLiveData<List<GroupApi>> = MutableLiveData()
    val groupsByEntities: LiveData<PickersElementsGrouped> get() = _groups.map { groupItemsByEntity(it) }

    private val _softwares: MutableLiveData<List<SoftwareApi>> = MutableLiveData()
    val softwaresByEntities: LiveData<PickersElementsGrouped> get() = _softwares.map { groupItemsByEntity(it) }

    private val _softwaresLicenses: MutableLiveData<List<SoftwareLicenseApi>> = MutableLiveData()
    val softwaresLicensesByEntities: LiveData<PickersElementsGrouped> get() = _softwaresLicenses.map { groupItemsByEntity(it) }

    private val _businessCriticities: MutableLiveData<List<BusinessCriticityApi>> = MutableLiveData()
    val businessCriticitiesByEntities: LiveData<PickersElementsGrouped> get() = _businessCriticities.map { groupEntitiesByNothing(it) }

    private val _budgets: MutableLiveData<List<BudgetApi>> = MutableLiveData()
    val budgetsByEntities: LiveData<PickersElementsGrouped> get() = _budgets.map { groupEntitiesByNothing(it) }

    private val _dcrooms: MutableLiveData<List<DCRoomApi>> = MutableLiveData()
    val dcrooms: LiveData<PickersElementsGrouped> get() = _dcrooms.map { groupItemsByEntity(it) }

    private val _displayedFields: MutableLiveData<List<ItemField>> = MutableLiveData()
    val displayedFields: LiveData<List<ItemField>> get() = _displayedFields
    private val _displayedFieldsValue: MutableLiveData<Map<String, String>> = MutableLiveData()
    val displayedFieldsValue: LiveData<Map<String, String>> get() = _displayedFieldsValue

    private var itemsStaticsValuesByLabel: Map<String, PickersElementsGrouped> = mapOf()
        set(value) {
            field = value
            refreshDisplayablesFields()
        }

    private var infoComsStaticsValuesByLabel: Map<String, PickersElementsGrouped> = mapOf()
        set(value) {
            field = value
            refreshDisplayablesFields()
        }

    private val _staticsPossiblesValuesByLabel: MutableLiveData<Map<String, PickersElementsGrouped>> = MutableLiveData()
    val staticsPossiblesValuesByLabel: LiveData<Map<String, PickersElementsGrouped>> get() = _staticsPossiblesValuesByLabel

    private var itemInfosFields: List<ItemField> = listOf()
    set(value) {
        field = value
        itemInfosFieldsValue = getFieldsValuesFromItemsFields(itemFields = value)
    }
    private var itemInfosFieldsValue: Map<String, String> = mapOf()
    set(value) {
        field = value
        refreshDisplayablesFields()
    }

    private var infoComsFields: List<ItemField> = listOf()
    set(value) {
        field = value
        infoComsFieldsValue = getFieldsValuesFromItemsFields(itemFields = value)
        refreshDisplayablesFields()
    }
    private var infoComsFieldsValue: Map<String, String> = mapOf()
    set(value) {
        field = value
        refreshDisplayablesFields()
    }

    init {
        helper = getDeviceHelper()
        val fetchConfig = helper.getFetchConfig()
        viewModelScope.launch {
            try {
                var item = fetchItem()
                if (overrideSerial != null && item is DeviceWithSerialApi) {
                    item = item.newInstanceWithSerial(serial = overrideSerial)
                }
                _currentItemName.value = item.name
                currentItem = item

                if (!isItemCreation) {
                    saveDeviceHistoryIntoDb(
                        id = item.id,
                        name = item.name
                    )
                }

                val requests = mutableListOf<Deferred<Unit>>()

                requests.add(async {
                    if (fetchConfig.needEntities) {
                        entities = itemsUseCase.getAllEntities().toMutableList()
                        entities.add(ItemViewModelEmptiesItems.emptyEntityApi)
                    }

                    val subRequests = mutableListOf<Deferred<Unit>>()

                    if (fetchConfig.needSuperAdminProfilesUsers) {
                        for (entity in entities) {
                            subRequests.add(async {
                                superAdminProfilesUser.addAll(itemsUseCase.getProfilesUserByProfileIdAndEntityName(entityName = entity.name, profileName = ProfileApi.superAdminName))
                                Unit
                            })
                        }
                    }

                    for (request in subRequests) {
                        request.await()
                    }
                })

                if (fetchConfig.needProfiles) {
                    requests.add(async {
                        profiles = itemsUseCase.getAllProfiles().toMutableList()
                        profiles.add(ItemViewModelEmptiesItems.emptyProfileApi)
                        Unit
                    })

                }

                if (fetchConfig.needUsers) {
                    requests.add(async {
                        val users = itemsUseCase.getAllUsers().toMutableList()
                        users.add(ItemViewModelEmptiesItems.emptyUserApi)
                        _users.value = users
                    })
                }

                if (fetchConfig.needLocations) {
                    requests.add(async {
                        val locations = itemsUseCase.getAllLocations().toMutableList()
                        locations.add(ItemViewModelEmptiesItems.emptyLocationApi)
                        _locations.value = locations
                    })
                }

                if (fetchConfig.needSuppliers) {
                    requests.add(async {
                        val suppliers = itemsUseCase.getAllSuppliers().toMutableList()
                        suppliers.add(ItemViewModelEmptiesItems.emptySupplierApi)
                        _suppliers.value = suppliers
                    })
                }

                if (fetchConfig.needStates) {
                    requests.add(async {
                        val states = helper.fetchPossiblesStates(itemsUseCase = itemsUseCase).toMutableList()
                        states.add(ItemViewModelEmptiesItems.emptyStateApi)
                        _states.value = states
                    })
                }

                if (fetchConfig.needManufacturers) {
                    requests.add(async {
                        val manufacturers = itemsUseCase.getAllManufacturers().toMutableList()
                        manufacturers.add(ItemViewModelEmptiesItems.emptyManufacturerApi)
                        _manufacturers.value = manufacturers
                    })
                }

                if (fetchConfig.needModels) {
                    requests.add(async {
                        val models = helper.fetchModels(itemsUseCase = itemsUseCase).toMutableList()
                        models.add(ItemViewModelEmptiesItems.emptyModelApi)
                        _models.value = models
                    })
                }

                if (fetchConfig.needTypes) {
                    requests.add(async {
                        val types = helper.fetchTypes(itemsUseCase = itemsUseCase).toMutableList()
                        types.add(ItemViewModelEmptiesItems.emptyTypeApi)
                        _types.value = types
                    })
                }

                if (fetchConfig.needGroups) {
                    requests.add(async {
                        val groups = itemsUseCase.getAllGroups().toMutableList()
                        groups.add(ItemViewModelEmptiesItems.emptyGroupApi)
                        _groups.value = groups
                    })
                }

                if(fetchConfig.needSoftwares) {
                    requests.add(async {
                        val softwares = itemsUseCase.getAllSoftwares().toMutableList()
                        softwares.add(ItemViewModelEmptiesItems.emptySoftwareApi)
                        _softwares.value = softwares
                    })
                }

                if(fetchConfig.needSoftwaresLicenses) {
                    requests.add(async {
                        val softwaresLicenses = itemsUseCase.getAllSoftwaresLicenses().toMutableList()

                        if (item is SoftwareLicenseApi) {
                            softwaresLicenses.removeAll { it.softwareId != item.softwareId || it.id == item.id }
                        }

                        softwaresLicenses.add(ItemViewModelEmptiesItems.emptySoftwareLicenseApi)

                        _softwaresLicenses.value = softwaresLicenses
                    })
                }

                if(fetchConfig.needDCRooms) {
                    requests.add(async {
                        val dcrooms = itemsUseCase.getAllDCRooms().toMutableList()
                        dcrooms.add(ItemViewModelEmptiesItems.emptyDcRoomApi)
                        _dcrooms.value = dcrooms
                    })
                }

                requests.add(async {
                    val businessCritities = itemsUseCase.getAllBusinessCriticities().toMutableList()
                    businessCritities.add(ItemViewModelEmptiesItems.emptyBusinessCriticityApi)
                    _businessCriticities.value = businessCritities
                })


                requests.add(async {
                    val budgets = itemsUseCase.getAllBudgets().toMutableList()
                    budgets.add(ItemViewModelEmptiesItems.emptyBudgetApi)
                    _budgets.value = budgets
                })

                for (request in requests) {
                    request.await()
                }


                itemInfosFields = helper.getFieldsForDevice(context = context, item = item)
                itemInfosFields.filterIsInstance(StaticPickerField::class.java).forEach {
                    val staticsOriginalValues = itemsStaticsValuesByLabel.toMutableMap()
                    staticsOriginalValues[it.label] = helper.getStaticsFieldsForLabel(context = context, label = it.label)
                    itemsStaticsValuesByLabel = staticsOriginalValues
                }

                if (helper.areInfosComsEnabled(item = item)) {
                    infoComsFields = ItemViewModelInfoComsHelper.getFieldsForItem(context = context, item = item)
                    infoComsFields.filterIsInstance(StaticPickerField::class.java).forEach {
                        val staticsOriginalValues = infoComsStaticsValuesByLabel.toMutableMap()
                        staticsOriginalValues[it.label] = ItemViewModelInfoComsHelper.getStaticsFieldsForLabel(context = context, label = it.label)
                        infoComsStaticsValuesByLabel = staticsOriginalValues
                    }
                }
            } catch(e: Exception) {
                println(e)
               _fetchingErrorMessage.value = Errors.UNKNOWN.message(context = context)
            }

        }
    }

    fun setSelectedTab(tab: Tab) {
        _selectedTab.value = tab
        refreshDisplayablesFields()
    }

    /**
     * Called from ItemComposable when "save" button is tapped.
     *
     * @see ItemComposable
     */
    fun save(): Flow<ViewState<Nothing>> {
        _savingErrorMessage.value = null
        return if (isItemCreation)
            addItem()
        else
            updateItem()
    }

    private fun addItem(): Flow<ViewState<Nothing>> {
        val currentItem = currentItem

        return flow {
            emit(ViewState.Loading())

            if (currentItem == null) {
                emit(ViewState.Success<Nothing>())
            } else {
                try {
                    val updatedItem = getUpdatedItem(originalItem = currentItem)
                    val postResponse = itemsUseCase.addItem(item = updatedItem)

                    saveDeviceHistoryIntoDb(
                        id = postResponse.id,
                        name = updatedItem.name
                    )

                    emit(ViewState.Success<Nothing>())
                } catch(e: Exception) {
                    _savingErrorMessage.value = Errors.UNKNOWN.message(context = context)
                    emit(ViewState.Exception(e))
                }
            }
        }
    }

    private fun updateItem(): Flow<ViewState<Nothing>> {
        val currentItem = currentItem

        return flow {
            emit(ViewState.Loading())

            if (currentItem == null) {
                emit(ViewState.Success<Nothing>())
            } else {
                //try {
                    val updatedItem = getUpdatedItem(originalItem = currentItem)
                    itemsUseCase.updateItem(item = updatedItem)

                    saveDeviceHistoryIntoDb(
                        id = updatedItem.id,
                        name = updatedItem.name
                    )

                    emit(ViewState.Success<Nothing>())


            }
        }
    }

    fun getIconDrawableIdForItem(): Int = ItemsUtilities.getIconDrawableIdForItem(itemType = itemType)

    fun getDisplayableDeviceTypeNameForItem(): String = ItemsUtilities.getDisplayableDeviceTypeNameForItem(itemType = itemType)

    fun getBooleanForField(fieldValue: String) = fieldValue.toIntOrNull() == 1 || (fieldValue.toBoolean())

    fun getLocationById(id: Int): LocationApi {
        return _locations.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptyLocationApi.copy(name = context.getString(R.string.no_item_name))
    }

    fun getGroupById(id: Int): GroupApi {
        return _groups.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptyGroupApi.copy(name = context.getString(R.string.no_item_name))
    }

    fun getSupplierById(id: Int): SupplierApi {
        return _suppliers.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptySupplierApi.copy(name = context.getString(R.string.no_item_name))
    }

    fun getStateById(id: Int): StateApi {
        return _states.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptyStateApi.copy(name = context.getString(R.string.no_item_name))
    }

    fun getTypeById(id: Int): EntitledApi {
        return _types.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.getUnknownEntitledApi(context = context)
    }

    fun getManufacturerById(id: Int): ManufacturerApi {
        return _manufacturers.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptyManufacturerApi.copy(name = context.getString(R.string.no_item_name))
    }

    fun getModelById(id: Int): EntitledApi {
        return _models.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.getUnknownEntitledApi(context = context)
    }

    fun getSoftwareById(id: Int): SoftwareApi {
        return _softwares.value?.firstOrNull { it.id == id } ?: (ItemViewModelEmptiesItems.emptySoftwareApi).copy(name = context.getString(R.string.no_item_name))
    }

    fun getSoftwareLicenseById(id: Int): SoftwareLicenseApi {
        return _softwaresLicenses.value?.firstOrNull { it.id == id } ?: (ItemViewModelEmptiesItems.emptySoftwareLicenseApi).copy(name = context.getString(R.string.no_item_name))
    }

    fun getUserNameById(id: Int): String {
        val user = _users.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptyUserApi.copy(name = context.getString(R.string.no_item_name), realname = null, firstname = null)
        return getUserApiDisplayName(user = user)
    }

    fun getBusinessCriticityById(id: Int): BusinessCriticityApi {
        return _businessCriticities.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptyBusinessCriticityApi.copy(name = context.getString(R.string.no_item_name))
    }

    fun getBudgetById(id: Int): BudgetApi {
        return _budgets.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptyBudgetApi.copy(name = context.getString(R.string.no_item_name))
    }

    fun getDCRoomById(id: Int): DCRoomApi {
        return _dcrooms.value?.firstOrNull { it.id == id } ?: ItemViewModelEmptiesItems.emptyDcRoomApi.copy(name = context.getString(R.string.no_item_name))
    }

    fun getStaticFieldsNameWithLabelAndId(label: String, id: Int): String {
        when(selectedTab.value!!) {
            Tab.ITEM_INFOS -> {
                val pickersElementsGrouped = (itemsStaticsValuesByLabel)[label] ?: return ""
                val allPickersElements = pickersElementsGrouped.values.flatten()
                return allPickersElements.firstOrNull { it.id == id }?.name ?: ""
            }
            Tab.INFO_COMS -> {
                val pickersElementsGrouped = (infoComsStaticsValuesByLabel)[label] ?: return ""
                val allPickersElements = pickersElementsGrouped.values.flatten()
                return allPickersElements.firstOrNull { it.id == id }?.name ?: ""
            }
        }
    }

    private fun getUserApiDisplayName(user: UserApi): String {
        return user.fullname
    }

    private suspend fun fetchItem(): DeviceApi {
        return if (itemId == HomeViewModel.NO_TEMPLATE_ID)
            helper.getEmptyItem()
        else
            helper.fetchDevice(itemsUseCase = itemsUseCase, deviceId = itemId)
    }

    fun setValueForField(fieldLabel: String, fieldValue: String) {
        setValueForField(
            fieldLabel = fieldLabel,
            fieldValue = fieldValue,
            tab = selectedTab.value!!
        )
    }

    fun setValueForField(fieldLabel: String, fieldValue: Date) {
        setValueForField(
            fieldLabel = fieldLabel,
            fieldValue = fieldValue.toyyyyMMddString(),
            tab = selectedTab.value!!
        )
    }

    fun setValueForField(fieldLabel: String, fieldValue: Boolean) {
        setValueForField(
            fieldLabel = fieldLabel,
            fieldValue = fieldValue.toString(),
            tab = selectedTab.value!!
        )
    }

    fun deleteDateFieldValue(label: String) {
        setValueForField(fieldLabel = label, fieldValue = "")
    }

    fun getDateFromFieldValue(value: String) = value.toyyyyMMddDate()

    private fun setValueForField(fieldLabel: String, fieldValue: String, tab: Tab) {
        val original = getFieldsValuesForTab(tab = tab).toMutableMap()
        original[fieldLabel] = fieldValue
        setFieldsValuesForTab(values = original, tab = tab)
    }

    private fun getFieldsValuesForTab(tab: Tab): Map<String, String> {
        return when(tab) {
            Tab.ITEM_INFOS -> itemInfosFieldsValue
            Tab.INFO_COMS -> infoComsFieldsValue
        }
    }

    private fun setFieldsValuesForTab(values: Map<String, String>, tab: Tab) {
        return when(tab) {
            Tab.ITEM_INFOS -> itemInfosFieldsValue = values
            Tab.INFO_COMS -> infoComsFieldsValue = values
        }
    }

    private fun getFieldsValuesFromItemsFields(itemFields: List<ItemField>): Map<String, String> {
        val fieldsValueMap = mutableMapOf<String, String>()
        itemFields.forEach { fieldsValueMap[it.label] = it.initialValue }

        return fieldsValueMap
    }

    private fun refreshDisplayablesFields() {
        _displayedFields.value = when(selectedTab.value!!) {
            Tab.ITEM_INFOS -> itemInfosFields
            Tab.INFO_COMS -> infoComsFields
        }

        _displayedFieldsValue.value = when(selectedTab.value!!) {
            Tab.ITEM_INFOS -> itemInfosFieldsValue
            Tab.INFO_COMS -> infoComsFieldsValue
        }

        _staticsPossiblesValuesByLabel.value = when(selectedTab.value!!) {
            Tab.ITEM_INFOS -> itemsStaticsValuesByLabel
            Tab.INFO_COMS -> infoComsStaticsValuesByLabel
        }
    }

    private fun getUpdatedItem(originalItem: DeviceApi): DeviceApi {
        var newItem = helper.getUpdatedDeviceWithFields(context = context, originalItem = originalItem, fields = itemInfosFieldsValue)

        if (helper.areInfosComsEnabled(item = originalItem)) {
            newItem = newItem.newInstanceWithInfoComs(infoComs = ItemViewModelInfoComsHelper.getUpdatedInfoComsWithFields(context = context, originalItem = originalItem.infoComs!!, fields = infoComsFieldsValue))
        }

        return newItem
    }

    private fun getDeviceHelper(): ItemViewModelHelper {
        val infos = ItemViewModelInfos(
            isCreation = isItemCreation
        )

        return when {
            itemType.equals(ComputerApi.typeName, ignoreCase = true) -> ItemViewModelComputerHelper(infos = infos)
            itemType.equals(PhoneApi.typeName, ignoreCase = true) -> ItemViewModelPhoneHelper(infos = infos)
            itemType.equals(SoftwareApi.typeName, ignoreCase = true) -> ItemViewModelSoftwareHelper(infos = infos)
            itemType.equals(MonitorApi.typeName, ignoreCase = true) -> ItemViewModelMonitorHelper(infos = infos)
            itemType.equals(PrinterApi.typeName, ignoreCase = true) -> ItemViewModelPrinterHelper(infos = infos)
            itemType.equals(NetworkEquipmentApi.typeName, ignoreCase = true) -> ItemViewModelNetworkEquipmentHelper(infos = infos)
            itemType.equals(SoftwareLicenseApi.typeName, ignoreCase = true) -> ItemViewModelSoftwareLicenseHelper(infos = infos)
            itemType.equals(RackApi.typeName, ignoreCase = true) -> ItemViewModelRackHelper(infos = infos)
            else -> throw Exception(context.getString(R.string.unknown_item_type))
        }
    }

    private suspend fun saveDeviceHistoryIntoDb(id: Int, name: String) {
        val deviceHistoryName = if (name.trim() == "") id.toString() else name.trim()
        itemsUseCase.insertDeviceHistory(
            deviceHistory = DeviceHistoryDb(
                id = id,
                itemType = itemType,
                name = deviceHistoryName,
                insertionDate = DateUtilities.getISO8061StringFromDate(Calendar.getInstance().time)
            )
        )
    }

    private fun <T: ItemApi> groupItemsByEntity(items: List<T>, id: (T) -> Int = { it.id }, name: (T) -> String = { it.name }): PickersElementsGrouped {
        val entitiesIds = entities.map { it.entityId }
        return items
            .filter {
                    location -> entitiesIds.contains(location.entityId)
            }
            .sortedBy {
                it.name
            }
            .groupBy { location ->
                val entity = entities.first { it.entityId == location.entityId }
                val entityPickerElement = PickerElement(id = entity.entityId, name = entity.name)
                entityPickerElement
            }.mapValues { locations ->
                locations.value.map { PickerElement(id = id(it), name = name(it)) }
            }
    }

    private fun <T: EntitledApi> groupEntitiesByNothing(items: List<T>, id: (T) -> Int = { it.id }, name: (T) -> String = { it.name }): PickersElementsGrouped {
        return mapOf(
            PickerElement(id = 0, name = "") to items.map { item -> PickerElement(id = id(item), name = name(item)) }
        )
    }
}