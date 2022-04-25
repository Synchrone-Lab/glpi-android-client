package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.EntitledApi
import fr.synchrone.glpisupport.data.model.remote.items.StateApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.SoftwareLicenseApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.*
import fr.synchrone.glpisupport.presentation.utilities.functions.toddMMyyyyDate
import fr.synchrone.glpisupport.presentation.utilities.functions.toddMMyyyyString
import fr.synchrone.glpisupport.presentation.utilities.functions.toyyyyMMddDate
import fr.synchrone.glpisupport.presentation.utilities.functions.toyyyyMMddString

/**
 * Implementation of ItemViewModelHelper for softwares licenses.
 *
 * @see ItemViewModelHelper
 */
class ItemViewModelSoftwareLicenseHelper(infos: ItemViewModelInfos): ItemViewModelHelper(infos = infos) {

    private enum class FieldsNames {
        NAME,
        SOFTWARE,
        SOFTWARE_LICENSE,
        LOCATION,
        USER,
        STATE,
        USER_TECH,
        GROUP_TECH,
        EDITOR,
        NUMBER,
        GROUP,
        TYPE,
        ALLOW_OVER_QUOTA,
        EXPIRE,
        COMMENT;

        fun displayableName(context: Context): String = when (this) {
            NAME -> context.getString(R.string.software_license_field_name)
            SOFTWARE -> context.getString(R.string.software_license_field_software)
            SOFTWARE_LICENSE -> context.getString(R.string.software_license_field_software_license)
            LOCATION -> context.getString(R.string.software_license_field_location)
            USER -> context.getString(R.string.software_license_field_user)
            STATE -> context.getString(R.string.software_license_field_state)
            USER_TECH -> context.getString(R.string.software_license_field_user_tech)
            GROUP_TECH -> context.getString(R.string.software_license_field_group_tech)
            EDITOR -> context.getString(R.string.software_license_field_editor)
            NUMBER -> context.getString(R.string.software_license_field_number)
            GROUP -> context.getString(R.string.software_license_field_group)
            TYPE -> context.getString(R.string.software_license_field_type)
            ALLOW_OVER_QUOTA -> context.getString(R.string.software_license_field_allow_over_quota)
            EXPIRE -> context.getString(R.string.software_license_field_expire)
            COMMENT -> context.getString(R.string.software_license_field_comment)
        }
    }

    override fun getFieldsForDevice(context: Context, item: DeviceApi): List<ItemField> {
        if (item !is SoftwareLicenseApi)
            return listOf()

        val fields = mutableListOf<ItemField>(
            TextViewField(label = FieldsNames.NAME.displayableName(context = context), initialValue = item.name)
        )

        if (infos.isCreation) {
            fields.add(
                SoftwarePickerField(label = FieldsNames.SOFTWARE.displayableName(context = context), initialValue = item.softwareId.toString()),
            )
        }

        fields.addAll(
            listOf(
                SoftwareLicensePickerField(label = FieldsNames.SOFTWARE_LICENSE.displayableName(context = context), initialValue = item.softwareLicenseId.toString()),
                LocationPickerField(label = FieldsNames.LOCATION.displayableName(context = context), initialValue = item.locationId.toString()),
                GroupPickerField(label = FieldsNames.GROUP_TECH.displayableName(context = context), initialValue = item.groupTechId.toString()),
                SuperAdminUserPickerField(label = FieldsNames.USER_TECH.displayableName(context = context), initialValue = item.userTechId.toString()),
                UserPickerField(label = FieldsNames.USER.displayableName(context = context), initialValue = item.userId.toString()),
                BooleanField(label = FieldsNames.ALLOW_OVER_QUOTA.displayableName(context = context), initialValue = item.allowOverquota.toString()),
                StatePickerField(label = FieldsNames.STATE.displayableName(context = context), initialValue = item.stateId.toString()),
                ManufacturerPickerField(label = FieldsNames.EDITOR.displayableName(context = context), initialValue = item.manufacturerId.toString()),
                GroupPickerField(label = FieldsNames.GROUP.displayableName(context = context), initialValue = item.groupId.toString()),
                TypePickerField(label = FieldsNames.TYPE.displayableName(context = context), initialValue = item.softwareLicenceTypeId.toString()),
                TextViewField(label = FieldsNames.NUMBER.displayableName(context = context), initialValue = item.number.toString()),
                DatePickerField(label = FieldsNames.EXPIRE.displayableName(context = context), initialValue = getDisplayableDateFromInfoComs(dateString = item.expire)),
                CommentTextField(label = FieldsNames.COMMENT.displayableName(context = context), initialValue = item.comment ?: "")
            )
        )

        return fields
    }

    override fun getUpdatedDeviceWithFields(
        context: Context,
        originalItem: DeviceApi,
        fields: Map<String, String>
    ): DeviceApi {
        if (originalItem !is SoftwareLicenseApi)
            throw Exception("Unknown type to update.")

        val id = originalItem.id
        val name = fields[FieldsNames.NAME.displayableName(context = context)] ?: ""
        val entityId = originalItem.entityId
        val softwareId = if (infos.isCreation) fields[FieldsNames.SOFTWARE.displayableName(context = context)]?.toIntOrNull() ?: 0 else originalItem.softwareId
        val softwareLicenseId = fields[FieldsNames.SOFTWARE_LICENSE.displayableName(context = context)]?.toIntOrNull() ?: 0
        val softwareLicenceTypeId = fields[FieldsNames.TYPE.displayableName(context = context)]?.toIntOrNull() ?: 0
        val locationId = fields[FieldsNames.LOCATION.displayableName(context = context)]?.toIntOrNull() ?: 0
        val isTemplate = originalItem._is_template
        val userTechId = fields[FieldsNames.USER_TECH.displayableName(context = context)]?.toIntOrNull() ?: 0
        val groupTechId = fields[FieldsNames.GROUP_TECH.displayableName(context = context)]?.toIntOrNull() ?: 0
        val userId = fields[FieldsNames.USER.displayableName(context = context)]?.toIntOrNull() ?: 0
        val number = fields[FieldsNames.NUMBER.displayableName(context = context)]?.toIntOrNull() ?: 0
        val groupId = fields[FieldsNames.GROUP.displayableName(context = context)]?.toIntOrNull() ?: 0
        val allowOverquota = parseStringBooleanToInt(string = fields[FieldsNames.ALLOW_OVER_QUOTA.displayableName(context = context)] ?: "")
        val stateId = fields[FieldsNames.STATE.displayableName(context = context)]?.toIntOrNull() ?: 0
        val manufacturerId = fields[FieldsNames.EDITOR.displayableName(context = context)]?.toIntOrNull() ?: 0
        val expire = getApiFormatDateFromDisplayableDate(dateString = fields[FieldsNames.EXPIRE.displayableName(context = context)])
        val comment = fields[FieldsNames.COMMENT.displayableName(context = context)] ?: ""
        val infoComs = originalItem.infoComs

        return SoftwareLicenseApi(
            id = id,
            name = name,
            entityId = entityId,
            softwareId = softwareId,
            softwareLicenseId = softwareLicenseId,
            softwareLicenceTypeId = softwareLicenceTypeId,
            locationId = locationId,
            _is_template = isTemplate,
            userTechId = userTechId,
            groupTechId = groupTechId,
            userId = userId,
            number = number,
            groupId = groupId,
            _allow_overquota = allowOverquota,
            stateId = stateId,
            manufacturerId = manufacturerId,
            expire = expire,
            comment = comment,
            _infocoms = infoComs
        )
    }

    override fun getEmptyItem(): DeviceApi = ItemViewModelEmptiesItems.emptySoftwareLicenseApi

    override fun getFetchConfig(): ItemViewModelFetchConfig {
        return ItemViewModelFetchConfig(
            needEntities = true,
            needProfiles = true,
            needSuperAdminProfilesUsers = true,
            needUsers = true,
            needLocations = true,
            needGroups = true,
            needSuppliers = false,
            needStates = true,
            needManufacturers = true,
            needModels = false,
            needTypes = true,
            needSoftwares = true,
            needSoftwaresLicenses = true
        )
    }

    override suspend fun fetchModels(itemsUseCase: ItemsUseCase): List<EntitledApi> = listOf()

    override suspend fun fetchTypes(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllSoftwareLicensesTypesApi()

    override suspend fun fetchDevice(itemsUseCase: ItemsUseCase, deviceId: Int): DeviceApi = itemsUseCase.getSoftwareLicenceApiById(id = deviceId)

    override suspend fun fetchPossiblesStates(itemsUseCase: ItemsUseCase): List<StateApi> = itemsUseCase.getPossiblesStatesToSoftwareLicenses()

    private fun parseStringBooleanToInt(string: String) = if (string.toIntOrNull() == 1 || string.toBoolean()) 1 else 0

    /**
     * Take an optional yyyy-MM-dd date string and convert it to displayable french date format dd-MM-yyyy.
     *
     * @param dateString yyyy-MM-dd string that need to be converted.
     * @return An dd-MM-yyyy string french displayable format or an empty String if dateString is null or invalid.
     */
    private fun getDisplayableDateFromInfoComs(dateString: String?): String {
        val dateStringNotNull = dateString ?: return ""
        val date = dateStringNotNull.toyyyyMMddDate() ?: return ""
        return date.toddMMyyyyString()
    }

    /**
     * Take an optional date string and convert it to yyyy-MM-dd format.
     *
     * @param dateString dd-MM-yyyy or yyyy-MM-dd string that need to be converted.
     * @return An yyyy-MM-dd string that match api format or null if dateString is null or invalid.
     */
    private fun getApiFormatDateFromDisplayableDate(dateString: String?): String? {
        val dateStringNotNull = dateString ?: return null
        return if (dateString.toyyyyMMddDate() != null)
            dateString
        else {
            val date = dateStringNotNull.toddMMyyyyDate()
            date?.toyyyyMMddString()
        }
    }
}