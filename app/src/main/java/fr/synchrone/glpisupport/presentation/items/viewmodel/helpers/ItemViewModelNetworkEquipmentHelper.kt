package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.EntitledApi
import fr.synchrone.glpisupport.data.model.remote.items.StateApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.NetworkEquipmentApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.*

/**
 * Implementation of ItemViewModelHelper for network equipments.
 *
 * @see ItemViewModelHelper
 */
class ItemViewModelNetworkEquipmentHelper(infos: ItemViewModelInfos): ItemViewModelHelper(infos = infos) {

    private enum class FieldsNames {
        NAME,
        SERIAL_NUMBER,
        PHONE_NUMBER,
        LOCATION,
        MEMORY,
        USER,
        USER_TECH,
        STATE,
        MANUFACTURER,
        TYPE,
        MODEL,
        COMMENT;

        fun displayableName(context: Context): String = when (this) {
            NAME -> context.getString(R.string.network_equipment_field_name)
            SERIAL_NUMBER -> context.getString(R.string.network_equipment_field_serial_number)
            PHONE_NUMBER -> context.getString(R.string.network_equipment_field_phone_number)
            LOCATION -> context.getString(R.string.network_equipment_field_location)
            MEMORY -> context.getString(R.string.network_equipment_field_memory)
            USER -> context.getString(R.string.network_equipment_field_user)
            USER_TECH -> context.getString(R.string.network_equipment_field_user_tech)
            STATE -> context.getString(R.string.network_equipment_field_state)
            MANUFACTURER -> context.getString(R.string.network_equipment_field_manufacturer)
            TYPE -> context.getString(R.string.network_equipment_field_type)
            MODEL -> context.getString(R.string.network_equipment_field_model)
            COMMENT -> context.getString(R.string.network_equipment_field_comment)
        }
    }

    override fun getFieldsForDevice(context: Context, item: DeviceApi): List<ItemField> {
        if (item !is NetworkEquipmentApi)
            return listOf()

        return listOf(
            TextViewField(label = FieldsNames.NAME.displayableName(context = context), initialValue = item.name),
            TextViewField(label = FieldsNames.PHONE_NUMBER.displayableName(context = context), initialValue = item.userNumber),
            TextViewField(label = FieldsNames.SERIAL_NUMBER.displayableName(context = context), initialValue = item.serial ?: ""),
            LocationPickerField(label = FieldsNames.LOCATION.displayableName(context = context), initialValue = item.locationId.toString()),
            UserPickerField(label = FieldsNames.USER.displayableName(context = context), initialValue = item.userId.toString()),
            SuperAdminUserPickerField(label = FieldsNames.USER_TECH.displayableName(context = context), initialValue = item.userTechId.toString()),
            StatePickerField(label = FieldsNames.STATE.displayableName(context = context), initialValue = item.stateId.toString()),
            TextViewField(label = FieldsNames.MEMORY.displayableName(context = context), initialValue = item.ram),
            ManufacturerPickerField(label = FieldsNames.MANUFACTURER.displayableName(context = context), initialValue = item.manufacturerId.toString()),
            TypePickerField(label = FieldsNames.TYPE.displayableName(context = context), initialValue = item.networkEquipmentTypeId.toString()),
            ModelPickerField(label = FieldsNames.MODEL.displayableName(context = context), initialValue = item.networkEquipmentModelId.toString()),
            CommentTextField(label = FieldsNames.COMMENT.displayableName(context = context), initialValue = item.comment ?: "")
        )
    }

    override fun getUpdatedDeviceWithFields(
        context: Context,
        originalItem: DeviceApi,
        fields: Map<String, String>
    ): DeviceApi {
        val id = originalItem.id
        val entityId = originalItem.entityId
        val userNumber = fields[FieldsNames.PHONE_NUMBER.displayableName(context = context)]!!
        val networkEquipmentModelIdModelId = fields[FieldsNames.MODEL.displayableName(context = context)]!!.toInt()
        val networkEquipmentTypeId = fields[FieldsNames.TYPE.displayableName(context = context)]!!.toInt()
        val name = fields[FieldsNames.NAME.displayableName(context = context)]!!
        val ram = fields[FieldsNames.MEMORY.displayableName(context = context)]!!
        val serialNumber = fields[FieldsNames.SERIAL_NUMBER.displayableName(context = context)]!!
        val locationId = fields[FieldsNames.LOCATION.displayableName(context = context)]!!.toInt()
        val userId = fields[FieldsNames.USER.displayableName(context = context)]!!.toInt()
        val userTechId = fields[FieldsNames.USER_TECH.displayableName(context = context)]!!.toInt()
        val stateId = fields[FieldsNames.STATE.displayableName(context = context)]!!.toInt()
        val manufacturerId = fields[FieldsNames.MANUFACTURER.displayableName(context = context)]!!.toInt()
        val comment = fields[FieldsNames.COMMENT.displayableName(context = context)]!!
        val infoComs = ItemViewModelInfoComsHelper.getUpdatedInfoComsWithFields(
            context = context,
            originalItem = originalItem.infoComs!!,
            fields = fields
        )

        return NetworkEquipmentApi(
            id = id,
            entityId = entityId,
            userNumber = userNumber,
            name = name,
            serial = serialNumber,
            locationId = locationId,
            _is_template = 0,
            ram = ram,
            userId = userId,
            userTechId = userTechId,
            stateId = stateId,
            networkEquipmentModelId = networkEquipmentModelIdModelId,
            networkEquipmentTypeId = networkEquipmentTypeId,
            manufacturerId = manufacturerId,
            comment = comment,
            _infocoms = infoComs
        )
    }

    override fun getFetchConfig(): ItemViewModelFetchConfig {
        return ItemViewModelFetchConfig(
            needEntities = true,
            needProfiles = true,
            needSuperAdminProfilesUsers = true,
            needUsers = true,
            needLocations = true,
            needGroups = false,
            needSuppliers = false,
            needStates = true,
            needManufacturers = true,
            needModels = true,
            needTypes = true
        )
    }

    override fun getEmptyItem(): DeviceApi = ItemViewModelEmptiesItems.emptyNetworkEquipmentApi

    override suspend fun fetchModels(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllNetworkEquipmentsModels()

    override suspend fun fetchTypes(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllNetworkEquipmentsTypes()

    override suspend fun fetchDevice(itemsUseCase: ItemsUseCase, deviceId: Int): DeviceApi = itemsUseCase.getNetworkEquipmentById(deviceId)

    override suspend fun fetchPossiblesStates(itemsUseCase: ItemsUseCase): List<StateApi> = itemsUseCase.getPossiblesStatesToNetworkEquipments()
}