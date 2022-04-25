package fr.synchrone.glpisupport.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.core.ViewState
import fr.synchrone.glpisupport.data.model.local.devices.DeviceHistoryDb
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import fr.synchrone.glpisupport.data.model.remote.items.ItemApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.data.model.remote.user.myprofiles.MyProfilesApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.domain.LoginUseCase
import fr.synchrone.glpisupport.domain.SearchUseCase
import fr.synchrone.glpisupport.presentation.items.viewmodel.PickersElementsGrouped
import fr.synchrone.glpisupport.presentation.utilities.functions.ItemsUtilities
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerElement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model used by home composable.
 *
 * @see HomeComposable
 */
@HiltViewModel
@SuppressLint("StaticFieldLeak")
class HomeViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val itemsUseCase: ItemsUseCase,
    private val loginUseCase: LoginUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        const val NO_TEMPLATE_ID = -1
    }

    private enum class DeviceSelection(val id: Int, val typeName: String) {
        COMPUTER(id = 1, typeName = ComputerApi.typeName),
        PHONE(id = 2, typeName = PhoneApi.typeName),
        MONITOR(id = 3, typeName = MonitorApi.typeName),
        NETWORK_EQUIPMENT(id = 4, typeName = NetworkEquipmentApi.typeName),
        PRINTER(id = 5, typeName = PrinterApi.typeName),
        SOFTWARE(id = 6, typeName = SoftwareApi.typeName),
        SOFTWARE_LICENSE(id = 7, typeName = SoftwareLicenseApi.typeName),
        RACK(id = 8, typeName = RackApi.typeName);

        fun displayableName(context: Context): String = when (this) {
            COMPUTER -> context.getString(R.string.device_selection_computer_displayable_name)
            PHONE -> context.getString(R.string.device_selection_phone_displayable_name)
            MONITOR -> context.getString(R.string.device_selection_monitor_displayable_name)
            NETWORK_EQUIPMENT -> context.getString(R.string.device_selection_network_equipment_displayable_name)
            PRINTER -> context.getString(R.string.device_selection_printer_displayable_name)
            SOFTWARE -> context.getString(R.string.device_selection_software_displayable_name)
            SOFTWARE_LICENSE -> context.getString(R.string.device_selection_software_license_displayable_name)
            RACK -> context.getString(R.string.device_selection_rack_displayable_name)
        }
    }

    data class ItemIdAndType(val itemId: Int, val itemType: String)

    private val defaultDeviceSelection = DeviceSelection.COMPUTER

    private val _isSearchLoading: MutableLiveData<Boolean> = MutableLiveData()

    private val _searchResult: MutableLiveData<ItemIdAndType?> = MutableLiveData()
    val searchResult: LiveData<ItemIdAndType?> get() = _searchResult

    private val _showAddItemDialog : MutableLiveData<Boolean> = MutableLiveData()
    val showAddItemDialog : LiveData<Boolean> get() = _showAddItemDialog

    private val _scanSerial : MutableLiveData<String> = MutableLiveData()
    val scanSerial : LiveData<String> get() = _scanSerial

    private var computersTemplates: List<ComputerApi> = listOf()
    set(value) {
        field = value
        if (selectedItemTypeToCreateId == DeviceSelection.COMPUTER.id) {
            _selectablesTemplates.value = groupEntitiesByNothing(items = value)
        }
    }

    private var phonesTemplates: List<PhoneApi> = listOf()
    set(value) {
        field = value
        if (selectedItemTypeToCreateId == DeviceSelection.PHONE.id) {
            _selectablesTemplates.value = groupEntitiesByNothing(items = value)
        }
    }

    private var monitorsTemplates: List<MonitorApi> = listOf()
        set(value) {
            field = value
            if (selectedItemTypeToCreateId == DeviceSelection.MONITOR.id) {
                _selectablesTemplates.value = groupEntitiesByNothing(items = value)
            }
        }

    private var networkEquipmentsTemplates: List<NetworkEquipmentApi> = listOf()
        set(value) {
            field = value
            if (selectedItemTypeToCreateId == DeviceSelection.NETWORK_EQUIPMENT.id) {
                _selectablesTemplates.value = groupEntitiesByNothing(items = value)
            }
        }

    private var printersTemplates: List<PrinterApi> = listOf()
        set(value) {
            field = value
            if (selectedItemTypeToCreateId == DeviceSelection.PRINTER.id) {
                _selectablesTemplates.value = groupEntitiesByNothing(items = value)
            }
        }

    private var softwaresTemplates: List<SoftwareApi> = listOf()
        set(value) {
            field = value
            if (selectedItemTypeToCreateId == DeviceSelection.SOFTWARE.id) {
                _selectablesTemplates.value = groupEntitiesByNothing(items = value)
            }
        }

    private var softwareLicensesTemplates: List<SoftwareLicenseApi> = listOf()
        set(value) {
            field = value
            if (selectedItemTypeToCreateId == DeviceSelection.SOFTWARE_LICENSE.id) {
                _selectablesTemplates.value = groupEntitiesByNothing(items = value)
            }
        }

    private val _areCurrentUserProfileAndEntityLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val areCurrentUserProfileAndEntityLoading: LiveData<Boolean> get() = _areCurrentUserProfileAndEntityLoading

    private val _isMyProfilesLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isMyProfilesLoading: LiveData<Boolean> get() = _isMyProfilesLoading

    private val _isMyEntitiesLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val isMyEntitiesLoading: LiveData<Boolean> get() = _isMyEntitiesLoading

    private val _selectablesUsersProfiles: MutableLiveData<List<MyProfilesApi>> = MutableLiveData()
    val selectablesUserProfiles: LiveData<PickersElementsGrouped> get() = _selectablesUsersProfiles.map { value -> groupObjectsByNothing(value, id = { it.id }, name = { it.name }) }

    private val _selectablesUserEntities: MutableLiveData<List<EntityApi>> = MutableLiveData()
    val selectablesUserEntities: LiveData<PickersElementsGrouped> get() = _selectablesUserEntities.map { value -> groupObjectsByNothing(value, id = { it.entityId }, name = { it.name }) }

    private val _currentUserProfile: MutableLiveData<MyProfilesApi?> = MutableLiveData()
    val currentUserProfileName: LiveData<String?> get() = _currentUserProfile.map { if (areCurrentUserProfileAndEntityLoading.value!!) context.getString(R.string.loading) else it?.name }

    private val _currentUserEntity: MutableLiveData<EntityApi?> = MutableLiveData()
    val currentUserEntityName: LiveData<String?> get() = _currentUserEntity.map { if (areCurrentUserProfileAndEntityLoading.value!!) context.getString(R.string.loading) else it?.name }

    private val _selectedUserProfile: MutableLiveData<MyProfilesApi?> = MutableLiveData()
    val selectedUserProfileName: LiveData<String?> get() = _selectedUserProfile.map { if (isMyProfilesLoading.value!!) context.getString(R.string.loading) else it?.name }

    private val _selectedUserEntity: MutableLiveData<EntityApi?> = MutableLiveData()
    val selectedUserEntityName: LiveData<String?> get() = _selectedUserEntity.map { if (isMyEntitiesLoading.value!!) context.getString(R.string.loading) else it?.name }

    val selectablesTypesElements = groupObjectsByNothing(items = DeviceSelection.values().toList(), id = { it.id }, name = { it.displayableName(context = context) })

    private val _devicesHistories: LiveData<List<DeviceHistoryDb>> get() = itemsUseCase.getAllDevicesHistories()
    val devicesHistories: LiveData<List<DeviceHistoryDb>> get() = _devicesHistories.map { devicesHistories ->
        devicesHistories.sortedByDescending { it.insertionDate }
    }

    private val _multiplesDevicesFound: MutableLiveData<List<ItemIdAndType>?> = MutableLiveData()
    val multiplesDevicesFound: LiveData<List<PickerElement>?> get() = _multiplesDevicesFound.map { items ->
        items?.map { getPickerElementForMultipleDevicesElement(item = it) }
    }
    val isMultiplesDevicesFound: LiveData<Boolean> get() = _multiplesDevicesFound.map { it != null && it.isNotEmpty() }

    private val _noDeviceFound: MutableLiveData<Boolean> = MutableLiveData()
    val noDeviceFound: LiveData<Boolean> get() = _noDeviceFound

    private val _selectedDeviceTypeName: MutableLiveData<String> = MutableLiveData()
    val selectedDeviceTypeName: LiveData<String> get() = _selectedDeviceTypeName

    private val _selectablesTemplates: MutableLiveData<PickersElementsGrouped> = MutableLiveData()
    val selectablesTemplates: LiveData<PickersElementsGrouped> get() = _selectablesTemplates.map { liveDataValue ->
        if (liveDataValue.keys.isEmpty()) {
            return@map liveDataValue
        } else {
            val noneElement = PickerElement(id = NO_TEMPLATE_ID, name = context.getString(R.string.none))
            return@map groupObjectsByNothing(listOf(noneElement) + liveDataValue.values.flatten(), id = { it.id }, name = { it.name })
        }
    }

    private val _selectedTemplateName: MutableLiveData<String> = MutableLiveData()
    val selectedTemplateName: LiveData<String> get() = _selectedTemplateName

    private val _errorOccurred: MutableLiveData<String?> = MutableLiveData(null)
    val errorOccurred: LiveData<String?> get() = _errorOccurred

    private var selectedItemTypeToCreateId: Int = defaultDeviceSelection.id
    set(value) {
        field = value
        selectedTemplateToCreateId = NO_TEMPLATE_ID
        _selectablesTemplates.value = mapOf()
        _selectedDeviceTypeName.value = getSelectedDeviceTypeNameWithDevice()
        viewModelScope.launch {
            try {
                when(value) {
                    DeviceSelection.COMPUTER.id -> getComputersTemplates()
                    DeviceSelection.PHONE.id -> getPhonesTemplates()
                    DeviceSelection.MONITOR.id -> getMonitorsTemplates()
                    DeviceSelection.NETWORK_EQUIPMENT.id -> getNetworkEquipmentsTemplates()
                    DeviceSelection.PRINTER.id -> getPrintersTemplates()
                    DeviceSelection.SOFTWARE.id -> getSoftwaresTemplates()
                    DeviceSelection.SOFTWARE_LICENSE.id -> getSoftwaresLicensesTemplates()
                }
            } catch (e: Exception) {}
        }
    }

    private var selectedTemplateToCreateId: Int = NO_TEMPLATE_ID
    set(value) {
        field = value
        _selectedTemplateName.value = getSelectedTemplateName()
    }

    init {
        init()
    }

    private fun init(notifyMyProfilesLoading: Boolean = true) {
        resetAddItemDialogValues()
        _areCurrentUserProfileAndEntityLoading.value = true
        _isMyEntitiesLoading.value = true
        if (notifyMyProfilesLoading) {
            _isMyProfilesLoading.value = true
        }
        viewModelScope.launch {
            try {
                val activesProfileAndEntity = loginUseCase.getActivesProfilesAndEntities()
                _selectablesUsersProfiles.value = loginUseCase.getMyProfiles()
                _selectablesUserEntities.value = loginUseCase.getMyEntities()
                _selectedUserProfile.value = activesProfileAndEntity.activeProfile
                _selectedUserEntity.value = activesProfileAndEntity.activeEntity
                _currentUserProfile.value = activesProfileAndEntity.activeProfile
                _currentUserEntity.value = activesProfileAndEntity.activeEntity

                _areCurrentUserProfileAndEntityLoading.value = false
                _isMyProfilesLoading.value = false
                _isMyEntitiesLoading.value = false
            } catch(e: Exception) {
                _errorOccurred.value = context.getString(R.string.unknown_error_happened)
            }
        }
    }

    fun searchItemBySerialNumber(searchValue: String, isScan: Boolean) {
        _noDeviceFound.value = false
        _isSearchLoading.value = true
        viewModelScope.launch {
            try {
                val userSearch = if (searchValue.trim().contains(" ")) {
                    val userFullnameList = searchValue.trim().split(' ')
                    val surnameFirstLetterLowercased = userFullnameList[0].firstOrNull()?.lowercaseChar() ?: ""
                    val lastnameLowercased = userFullnameList[1].lowercase()
                    "${surnameFirstLetterLowercased}${lastnameLowercased}"
                } else
                    searchValue
                val searchResults = searchUseCase.searchBy(serial = searchValue, user = userSearch)
                if (searchResults.searchItems.isEmpty()){
                    if (isScan) {
                        _scanSerial.value = searchValue
                        _showAddItemDialog.value = true
                    } else {
                        _noDeviceFound.value = true
                    }
                } else if (searchResults.searchItems.size == 1) {
                    val itemType = searchResults.searchItems.first().itemtype
                    val itemId = searchResults.searchItems.first().id
                    _searchResult.value = ItemIdAndType(itemId = itemId, itemType = itemType)
                } else {
                    _multiplesDevicesFound.value = searchResults.searchItems.map { ItemIdAndType(itemId = it.id, itemType = it.itemtype) }
                }
            } catch(e: Exception) {
                if (isScan){
                    _scanSerial.value = searchValue
                    _showAddItemDialog.value = true
                } else {
                    _noDeviceFound.value = true
                }
            } finally {
                _isSearchLoading.value = false
            }
        }
    }

    fun notifyTapOnAddDevice() {
        val areCurrentUserProfileAndEntityLoading = _areCurrentUserProfileAndEntityLoading.value ?: true
        if (!areCurrentUserProfileAndEntityLoading) {
            _showAddItemDialog.value = true
        }
    }

    fun notifyCameraPermissionDenied() {
        _errorOccurred.value = context.getString(R.string.camera_permission_denied)
    }

    fun cancelSearch() {
        _isSearchLoading.value = false
        _noDeviceFound.value = false
        _multiplesDevicesFound.value = null
        _searchResult.value = null
        _scanSerial.value = ""
        _showAddItemDialog.value = false
        resetAddItemDialogValues()
    }

    fun setSelectedDeviceFromMultipleDevicesFound(selectedDevicePickerElement: PickerElement) {
        val multiplesDevicesFoundValuesRef = _multiplesDevicesFound.value
        if (multiplesDevicesFoundValuesRef != null) {
            val selectedDevice = _multiplesDevicesFound.value!!.first { itemIdAndType ->
                val pickerElement = getPickerElementForMultipleDevicesElement(item = itemIdAndType)

                pickerElement.id == selectedDevicePickerElement.id && pickerElement.name == selectedDevicePickerElement.name
            }

            _searchResult.value = selectedDevice
        }
    }

    private fun getPickerElementForMultipleDevicesElement(item: ItemIdAndType) = PickerElement(
        id = item.itemId,
        name = getDisplayableNameForMultipleDevicesElement(item = item),
        iconResourceId = ItemsUtilities.getIconDrawableIdForItem(itemType = item.itemType)
    )

    private fun getDisplayableNameForMultipleDevicesElement(item: ItemIdAndType): String = "${ItemsUtilities.getDisplayableDeviceTypeNameForItem(itemType = item.itemType)} ${item.itemId}"

    private fun resetAddItemDialogValues() {
        selectedTemplateToCreateId = NO_TEMPLATE_ID
        selectedItemTypeToCreateId = defaultDeviceSelection.id

        computersTemplates = listOf()
        phonesTemplates = listOf()
        monitorsTemplates = listOf()
        networkEquipmentsTemplates = listOf()
        printersTemplates = listOf()
        softwaresTemplates = listOf()
        softwareLicensesTemplates = listOf()


        _selectablesTemplates.value = mapOf()
    }

    fun setSelectedTypeId(id: Int) {
        selectedItemTypeToCreateId = id
    }

    fun setSelectedTemplateId(id: Int) {
        selectedTemplateToCreateId = id
    }

    fun setUserProfileIdSelected(id: Int) {
        val profile = _selectablesUsersProfiles.value!!.first { it.id == id }
        _areCurrentUserProfileAndEntityLoading.value = true
        _isMyEntitiesLoading.value = true
        viewModelScope.launch {
            try {
                loginUseCase.changeActiveProfile(profile = profile)
            } catch(e: Exception) {
                _errorOccurred.value = context.getString(R.string.home_error_while_profile_change)
            }


            init()
        }
    }

    fun setUserEntityIdSelected(id: Int) {
        val entity = _selectablesUserEntities.value!!.first { it.entityId == id }
        _areCurrentUserProfileAndEntityLoading.value = true
        _isMyEntitiesLoading.value = true
        viewModelScope.launch {
            try {
                loginUseCase.changeActiveEntity(entity = entity)
            } catch(e: Exception) {
                _errorOccurred.value = context.getString(R.string.home_error_while_entity_change)
            }

            init(notifyMyProfilesLoading = false)
        }
    }

    fun getAddItemResult(): ItemIdAndType {
        val deviceSelection = DeviceSelection.values().firstOrNull { it.id == selectedItemTypeToCreateId } ?: defaultDeviceSelection

        return ItemIdAndType(itemType = deviceSelection.typeName, itemId = selectedTemplateToCreateId)
    }

    fun logout(): Flow<ViewState<Nothing>> {
        return flow {
            emit(ViewState.Loading())

            try {
                loginUseCase.logout()

                emit(ViewState.Success<Nothing>())
            } catch (e: Exception) {
                emit(ViewState.Exception(e))
            }
        }
    }

    fun setScan(scanCode : String){
        searchItemBySerialNumber(scanCode, true)
    }

    private fun getSelectedDeviceTypeNameWithDevice(): String {
        val selectablesDevices = DeviceSelection.values()
        val device = selectablesDevices.firstOrNull { it.id == selectedItemTypeToCreateId }
        return device?.displayableName(context = context) ?: ""
    }

    private fun getSelectedTemplateName(): String {
        val selectedItem: ItemApi? = when(DeviceSelection.values().firstOrNull { it.id == selectedItemTypeToCreateId } ?: defaultDeviceSelection) {
            DeviceSelection.COMPUTER -> computersTemplates.firstOrNull { it.id == selectedTemplateToCreateId }
            DeviceSelection.PHONE -> phonesTemplates.firstOrNull { it.id == selectedTemplateToCreateId }
            DeviceSelection.MONITOR -> monitorsTemplates.firstOrNull { it.id == selectedTemplateToCreateId }
            DeviceSelection.NETWORK_EQUIPMENT -> networkEquipmentsTemplates.firstOrNull { it.id == selectedTemplateToCreateId }
            DeviceSelection.PRINTER -> printersTemplates.firstOrNull { it.id == selectedTemplateToCreateId }
            DeviceSelection.SOFTWARE -> softwaresTemplates.firstOrNull { it.id == selectedTemplateToCreateId }
            DeviceSelection.SOFTWARE_LICENSE -> softwareLicensesTemplates.firstOrNull { it.id == selectedTemplateToCreateId }
            DeviceSelection.RACK -> softwareLicensesTemplates.firstOrNull { it.id == selectedTemplateToCreateId }
        }

        return selectedItem?.name ?: ""
    }

    private suspend fun getComputersTemplates() {
        computersTemplates = itemsUseCase.getComputersTemplates()
    }

    private suspend fun getPhonesTemplates() {
        phonesTemplates = itemsUseCase.getPhonesTemplates()
    }

    private suspend fun getMonitorsTemplates() {
        monitorsTemplates = itemsUseCase.getMonitorsTemplates()
    }

    private suspend fun getNetworkEquipmentsTemplates() {
        networkEquipmentsTemplates = itemsUseCase.getNetworkEquipmentsTemplates()
    }

    private suspend fun getPrintersTemplates() {
        printersTemplates = itemsUseCase.getPrintersTemplates()
    }

    private suspend fun getSoftwaresTemplates() {
        softwaresTemplates = itemsUseCase.getSoftwaresTemplates()
    }

    private suspend fun getSoftwaresLicensesTemplates() {
        softwareLicensesTemplates = itemsUseCase.getSoftwaresLicensesTemplates()
    }

    private fun <T> groupObjectsByNothing(items: List<T>, id: (T) -> Int, name: (T) -> String): PickersElementsGrouped {
        return mapOf(
            PickerElement(id = 0, name = "") to items.map { item -> PickerElement(id = id(item), name = name(item)) }
        )
    }

    private fun <T: ItemApi> groupEntitiesByNothing(items: List<T>, id: (T) -> Int = { it.id }, name: (T) -> String = { it.name }): PickersElementsGrouped {
        return mapOf(
            PickerElement(id = 0, name = "") to items.map { item -> PickerElement(id = id(item), name = name(item)) }
        )
    }

}