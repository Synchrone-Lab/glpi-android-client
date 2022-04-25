package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.EntitledApi
import fr.synchrone.glpisupport.data.model.remote.items.StateApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.MonitorApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.*

/**
 * Implementation of ItemViewModelHelper for monitors.
 *
 * @see ItemViewModelHelper
 */
class ItemViewModelMonitorHelper(infos: ItemViewModelInfos): ItemViewModelHelper(infos = infos) {

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
        MICRO,
        SPEAKERS,
        SUBD,
        DVI,
        HDMI,
        BNC,
        PIVOT,
        DISPLAY_PORT,
        COMMENT;

        fun displayableName(context: Context): String = when (this) {
            NAME -> context.getString(R.string.monitor_field_name)
            SERIAL_NUMBER -> context.getString(R.string.monitor_field_serial_number)
            LOCATION -> context.getString(R.string.monitor_field_location)
            USER -> context.getString(R.string.monitor_field_user)
            USER_TECH -> context.getString(R.string.monitor_field_user_tech)
            STATE -> context.getString(R.string.monitor_field_state)
            MANUFACTURER -> context.getString(R.string.monitor_field_manufacturer)
            TYPE -> context.getString(R.string.monitor_field_type)
            MODEL -> context.getString(R.string.monitor_field_model)
            MICRO -> context.getString(R.string.monitor_field_micro)
            SPEAKERS -> context.getString(R.string.monitor_field_speakers)
            SUBD -> context.getString(R.string.monitor_field_subd)
            DVI -> context.getString(R.string.monitor_field_dvi)
            HDMI -> context.getString(R.string.monitor_field_hdmi)
            BNC -> context.getString(R.string.monitor_field_bnc)
            PIVOT -> context.getString(R.string.monitor_field_pivot)
            DISPLAY_PORT -> context.getString(R.string.monitor_field_display_port)
            COMMENT -> context.getString(R.string.monitor_field_comment)
        }
    }

    override fun getFieldsForDevice(context: Context, item: DeviceApi): List<ItemField> {
        if (item !is MonitorApi)
            return listOf()

        return listOf(
            TextViewField(label = FieldsNames.NAME.displayableName(context = context), initialValue = item.name),
            TextViewField(label = FieldsNames.SERIAL_NUMBER.displayableName(context = context), initialValue = item.serial ?: ""),
            LocationPickerField(label = FieldsNames.LOCATION.displayableName(context = context), initialValue = item.locationId.toString()),
            UserPickerField(label = FieldsNames.USER.displayableName(context = context), initialValue = item.userId.toString()),
            SuperAdminUserPickerField(label = FieldsNames.USER_TECH.displayableName(context = context), initialValue = item.userTechId.toString()),
            StatePickerField(label = FieldsNames.STATE.displayableName(context = context), initialValue = item.stateId.toString()),
            ManufacturerPickerField(label = FieldsNames.MANUFACTURER.displayableName(context = context), initialValue = item.manufacturerId.toString()),
            ModelPickerField(label = FieldsNames.MODEL.displayableName(context = context), initialValue = item.monitorModelId.toString()),
            TypePickerField(label = FieldsNames.TYPE.displayableName(context = context), initialValue = item.monitorTypeId.toString()),
            CommentTextField(label = FieldsNames.COMMENT.displayableName(context = context), initialValue = item.comment ?: ""),
            BooleanField(label = FieldsNames.MICRO.displayableName(context = context), initialValue = item.haveMicro.toString()),
            BooleanField(label = FieldsNames.SUBD.displayableName(context = context), initialValue = item.haveSubd.toString()),
            BooleanField(label = FieldsNames.DVI.displayableName(context = context), initialValue = item.haveDvi.toString()),
            BooleanField(label = FieldsNames.HDMI.displayableName(context = context), initialValue = item.haveHdmi.toString()),
            BooleanField(label = FieldsNames.SPEAKERS.displayableName(context = context), initialValue = item.haveSpeaker.toString()),
            BooleanField(label = FieldsNames.BNC.displayableName(context = context), initialValue = item.haveBnc.toString()),
            BooleanField(label = FieldsNames.PIVOT.displayableName(context = context), initialValue = item.havePivot.toString()),
            BooleanField(label = FieldsNames.DISPLAY_PORT.displayableName(context = context), initialValue = item.haveDisplayPort.toString())
        )
    }

    override fun getUpdatedDeviceWithFields(
        context: Context,
        originalItem: DeviceApi,
        fields: Map<String, String>
    ): DeviceApi {

        if (originalItem !is MonitorApi) {
            throw Exception("Device must be a monitor.")
        }

        val name = fields[FieldsNames.NAME.displayableName(context = context)] ?: ""
        val locationId = fields[FieldsNames.LOCATION.displayableName(context = context)]?.toIntOrNull() ?: 0
        val userTechId = fields[FieldsNames.USER_TECH.displayableName(context = context)]?.toIntOrNull() ?: 0
        val userId = fields[FieldsNames.USER.displayableName(context = context)]?.toIntOrNull() ?: 0
        val stateId = fields[FieldsNames.STATE.displayableName(context = context)]?.toIntOrNull() ?: 0
        val manufacturerId = fields[FieldsNames.MANUFACTURER.displayableName(context = context)]?.toIntOrNull() ?: 0
        val monitorTypeId = fields[FieldsNames.TYPE.displayableName(context = context)]!!.toIntOrNull() ?: 0
        val monitorModelId = fields[FieldsNames.MODEL.displayableName(context = context)]!!.toIntOrNull() ?: 0
        val serial = fields[FieldsNames.SERIAL_NUMBER.displayableName(context = context)] ?: ""
        val haveMicro = parseStringBooleanToInt(string = fields[FieldsNames.MICRO.displayableName(context = context)] ?: "")
        val haveSpeakers = parseStringBooleanToInt(string = fields[FieldsNames.SPEAKERS.displayableName(context = context)] ?: "")
        val haveSubd = parseStringBooleanToInt(string = fields[FieldsNames.SUBD.displayableName(context = context)] ?: "")
        val haveBnc = parseStringBooleanToInt(string = fields[FieldsNames.BNC.displayableName(context = context)] ?: "")
        val haveDvi = parseStringBooleanToInt(string = fields[FieldsNames.DVI.displayableName(context = context)] ?: "")
        val haveHdmi = parseStringBooleanToInt(string = fields[FieldsNames.HDMI.displayableName(context = context)] ?: "")
        val havePivot = parseStringBooleanToInt(string = fields[FieldsNames.PIVOT.displayableName(context = context)] ?: "")
        val haveDisplayport = parseStringBooleanToInt(string = fields[FieldsNames.DISPLAY_PORT.displayableName(context = context)] ?: "")
        val comment = fields[FieldsNames.COMMENT.displayableName(context = context)]
        
        return MonitorApi(
            id = originalItem.id,
            name = name,
            entityId = originalItem.entityId,
            locationId = locationId,
            _is_template = originalItem._is_template,
            userTechId = userTechId,
            userId = userId,
            stateId = stateId,
            manufacturerId = manufacturerId,
            monitorModelId = monitorModelId,
            monitorTypeId = monitorTypeId,
            serial = serial,
            _have_micro = haveMicro,
            _have_speaker = haveSpeakers,
            _have_subd = haveSubd,
            _have_bnc = haveBnc,
            _have_dvi = haveDvi,
            _have_hdmi = haveHdmi,
            _have_pivot = havePivot,
            _have_displayport = haveDisplayport,
            comment = comment,
            _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
        )
    }

    override fun getEmptyItem(): DeviceApi = ItemViewModelEmptiesItems.emptyMonitorApi

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

    override suspend fun fetchModels(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllMonitorsModels()

    override suspend fun fetchTypes(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllMonitorsTypes()

    override suspend fun fetchDevice(itemsUseCase: ItemsUseCase, deviceId: Int): DeviceApi = itemsUseCase.getMonitorById(id = deviceId)

    override suspend fun fetchPossiblesStates(itemsUseCase: ItemsUseCase): List<StateApi> = itemsUseCase.getPossiblesStatesToMonitors()

    private fun parseStringBooleanToInt(string: String) = if (string.toIntOrNull() == 1 || string.toBoolean()) 1 else 0
}