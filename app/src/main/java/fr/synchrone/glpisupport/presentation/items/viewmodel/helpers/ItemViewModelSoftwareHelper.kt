package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.EntitledApi
import fr.synchrone.glpisupport.data.model.remote.items.StateApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.SoftwareApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.*

/**
 * Implementation of ItemViewModelHelper for softwares.
 *
 * @see ItemViewModelHelper
 */
class ItemViewModelSoftwareHelper(infos: ItemViewModelInfos): ItemViewModelHelper(infos = infos) {

    private enum class FieldsNames {
        NAME,
        LOCATION,
        USER,
        SOFTWARE_USER_TECH,
        SOFTWARE_GROUP_TECH,
        EDITOR,
        CATEGORY,
        COMMENT;

        fun displayableName(context: Context): String = when (this) {
            NAME -> context.getString(R.string.software_field_name)
            LOCATION -> context.getString(R.string.software_field_location)
            USER -> context.getString(R.string.software_field_user)
            SOFTWARE_USER_TECH -> context.getString(R.string.software_field_software_user_tech)
            SOFTWARE_GROUP_TECH -> context.getString(R.string.software_field_software_group_tech)
            EDITOR -> context.getString(R.string.software_field_editor)
            CATEGORY -> context.getString(R.string.software_field_category)
            COMMENT -> context.getString(R.string.software_field_comment)
        }
    }

    override fun getFieldsForDevice(context: Context, item: DeviceApi): List<ItemField> {
        if (item !is SoftwareApi)
            return listOf()

        return listOf(
            TextViewField(label = FieldsNames.NAME.displayableName(context = context), initialValue = item.name),
            LocationPickerField(label = FieldsNames.LOCATION.displayableName(context = context), initialValue = item.locationId.toString()),
            GroupPickerField(label = FieldsNames.SOFTWARE_GROUP_TECH.displayableName(context = context), initialValue = item.groupTechId.toString()),
            SuperAdminUserPickerField(label = FieldsNames.SOFTWARE_USER_TECH.displayableName(context = context), initialValue = item.userTechId.toString()),
            UserPickerField(label = FieldsNames.USER.displayableName(context = context), initialValue = item.userId.toString()),
            ManufacturerPickerField(label = FieldsNames.EDITOR.displayableName(context = context), initialValue = item.manufacturerId.toString()),
            ModelPickerField(label = FieldsNames.CATEGORY.displayableName(context = context), initialValue = item.softwareCategoryId.toString()),
            CommentTextField(label = FieldsNames.COMMENT.displayableName(context = context), initialValue = item.comment ?: "")
        )
    }

    override fun getUpdatedDeviceWithFields(
        context: Context,
        originalItem: DeviceApi,
        fields: Map<String, String>
    ): DeviceApi {
        val id = originalItem.id
        val name = fields[FieldsNames.NAME.displayableName(context = context)]!!
        val locationId = fields[FieldsNames.LOCATION.displayableName(context = context)]!!.toInt()
        val groupTechId = fields[FieldsNames.SOFTWARE_GROUP_TECH.displayableName(context = context)]!!.toInt()
        val userId = fields[FieldsNames.USER.displayableName(context = context)]!!.toInt()
        val userTechId = fields[FieldsNames.SOFTWARE_USER_TECH.displayableName(context = context)]!!.toInt()
        val manufacturerId = fields[FieldsNames.EDITOR.displayableName(context = context)]!!.toInt()
        val softwareCategoryId = fields[FieldsNames.CATEGORY.displayableName(context = context)]!!.toInt()
        val comment = fields[FieldsNames.COMMENT.displayableName(context = context)]!!

        return (originalItem as SoftwareApi).copy(
            id = id,
            name = name,
            locationId = locationId,
            groupTechId = groupTechId,
            userId = userId,
            userTechId = userTechId,
            manufacturerId = manufacturerId,
            softwareCategoryId = softwareCategoryId,
            comment = comment
        )
    }

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
            needTypes = false,
            needSoftwaresLicenses = true
        )
    }

    override fun getEmptyItem(): DeviceApi = ItemViewModelEmptiesItems.emptySoftwareApi

    override suspend fun fetchDevice(itemsUseCase: ItemsUseCase, deviceId: Int): DeviceApi = itemsUseCase.getSoftwareById(deviceId)

    override suspend fun fetchModels(itemsUseCase: ItemsUseCase): List<EntitledApi> = listOf()

    override suspend fun fetchTypes(itemsUseCase: ItemsUseCase): List<EntitledApi> = listOf()

    override suspend fun fetchPossiblesStates(itemsUseCase: ItemsUseCase): List<StateApi> = listOf()
}