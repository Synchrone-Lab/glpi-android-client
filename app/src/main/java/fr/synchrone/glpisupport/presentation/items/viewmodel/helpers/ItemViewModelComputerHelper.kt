package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.EntitledApi
import fr.synchrone.glpisupport.data.model.remote.items.StateApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.ComputerApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.*

/**
 * Implementation of ItemViewModelHelper for computers.
 *
 * @see ItemViewModelHelper
 */
class ItemViewModelComputerHelper(infos: ItemViewModelInfos): ItemViewModelHelper(infos = infos) {

    private enum class FieldsNames {
        NAME,
        SERIAL_NUMBER,
        LOCATION,
        USER,
        USER_TECH,
        STATE,
        MANUFACTURER,
        TYPE,
        MODEL,
        COMMENT;

        fun displayableName(context: Context): String = when (this) {
            NAME -> context.getString(R.string.computer_field_name)
            SERIAL_NUMBER -> context.getString(R.string.computer_field_serial_number)
            LOCATION -> context.getString(R.string.computer_field_location)
            USER -> context.getString(R.string.computer_field_user)
            USER_TECH -> context.getString(R.string.computer_field_user_tech)
            STATE -> context.getString(R.string.computer_field_state)
            MANUFACTURER -> context.getString(R.string.computer_field_manufacturer)
            TYPE -> context.getString(R.string.computer_field_type)
            MODEL -> context.getString(R.string.computer_field_model)
            COMMENT -> context.getString(R.string.computer_field_comment)
        }
    }

    override fun getFieldsForDevice(context: Context, item: DeviceApi): List<ItemField> {
        if (item !is ComputerApi)
            return listOf()

        return listOf(
            TextViewField(label = FieldsNames.NAME.displayableName(context = context), initialValue = item.name),
            TextViewField(label = FieldsNames.SERIAL_NUMBER.displayableName(context = context), initialValue = item.serial ?: ""),
            LocationPickerField(label = FieldsNames.LOCATION.displayableName(context = context), initialValue = item.locationId.toString()),
            UserPickerField(label = FieldsNames.USER.displayableName(context = context), initialValue = item.userId.toString()),
            SuperAdminUserPickerField(label = FieldsNames.USER_TECH.displayableName(context = context), initialValue = item.userTechId.toString()),
            StatePickerField(label = FieldsNames.STATE.displayableName(context = context), initialValue = item.stateId.toString()),
            TypePickerField(label = FieldsNames.TYPE.displayableName(context = context), initialValue = item.computerTypeId.toString()),
            ManufacturerPickerField(label = FieldsNames.MANUFACTURER.displayableName(context = context), initialValue = item.manufacturerId.toString()),
            ModelPickerField(label = FieldsNames.MODEL.displayableName(context = context), initialValue = item.computerModelId.toString()),
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
        val name = fields[FieldsNames.NAME.displayableName(context = context)]!!
        val serialNumber = fields[FieldsNames.SERIAL_NUMBER.displayableName(context = context)]!!
        val locationId = fields[FieldsNames.LOCATION.displayableName(context = context)]!!.toInt()
        val userId = fields[FieldsNames.USER.displayableName(context = context)]!!.toInt()
        val userTechId = fields[FieldsNames.USER_TECH.displayableName(context = context)]!!.toInt()
        val stateId = fields[FieldsNames.STATE.displayableName(context = context)]!!.toInt()
        val computerTypeId = fields[FieldsNames.TYPE.displayableName(context = context)]!!.toInt()
        val manufacturerId = fields[FieldsNames.MANUFACTURER.displayableName(context = context)]!!.toInt()
        val computerModelId = fields[FieldsNames.MODEL.displayableName(context = context)]!!.toInt()
        val comment = fields[FieldsNames.COMMENT.displayableName(context = context)]!!
        val infoComs = originalItem.infoComs

        return ComputerApi(
            id = id,
            entityId = entityId,
            name = name,
            serial = serialNumber,
            locationId = locationId,
            _is_template = 0,
            userId = userId,
            userTechId = userTechId,
            stateId = stateId,
            computerTypeId = computerTypeId,
            computerModelId = computerModelId,
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
            needTypes = true,
            needModels = true
        )
    }

    override suspend fun fetchModels(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllComputersModels()

    override suspend fun fetchTypes(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllComputersTypes()

    override fun getEmptyItem(): DeviceApi = ItemViewModelEmptiesItems.emptyComputerApi

    override suspend fun fetchDevice(itemsUseCase: ItemsUseCase, deviceId: Int): DeviceApi = itemsUseCase.getComputerById(deviceId)

    override suspend fun fetchPossiblesStates(itemsUseCase: ItemsUseCase): List<StateApi> = itemsUseCase.getPossiblesStatesToComputers()
}