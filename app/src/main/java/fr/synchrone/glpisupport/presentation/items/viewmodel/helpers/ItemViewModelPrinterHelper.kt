package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.EntitledApi
import fr.synchrone.glpisupport.data.model.remote.items.StateApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.PrinterApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.*

/**
 * Implementation of ItemViewModelHelper for printers.
 *
 * @see ItemViewModelHelper
 */
class ItemViewModelPrinterHelper(infos: ItemViewModelInfos): ItemViewModelHelper(infos = infos) {

    private enum class FieldsNames {
        NAME,
        SERIAL_NUMBER,
        PHONE_NUMBER,
        LOCATION,
        USER,
        USER_TECH,
        GROUPID,
        GROUP,
        MEMORY,
        INITPAGECOUNTER,
        LASTPAGECOUNTER,
        STATE,
        MANUFACTURER,
        TYPE,
        MODEL,
        COMMENT,
        HAVESERIAL,
        PARALLEL,
        USB,
        WIFI,
        ETHERNET;

        fun displayableName(context: Context): String = when (this) {
            NAME -> context.getString(R.string.printer_field_name)
            SERIAL_NUMBER -> context.getString(R.string.printer_field_serial_number)
            PHONE_NUMBER -> context.getString(R.string.printer_field_phone_number)
            LOCATION -> context.getString(R.string.printer_field_location)
            USER -> context.getString(R.string.printer_field_user)
            USER_TECH -> context.getString(R.string.printer_field_user_tech)
            GROUPID -> context.getString(R.string.printer_field_groupid)
            GROUP -> context.getString(R.string.printer_field_group)
            MEMORY -> context.getString(R.string.printer_field_memory)
            INITPAGECOUNTER -> context.getString(R.string.printer_field_initpagecounter)
            LASTPAGECOUNTER -> context.getString(R.string.printer_field_lastpagecounter)
            STATE -> context.getString(R.string.printer_field_state)
            MANUFACTURER -> context.getString(R.string.printer_field_manufacturer)
            TYPE -> context.getString(R.string.printer_field_type)
            MODEL -> context.getString(R.string.printer_field_model)
            COMMENT -> context.getString(R.string.printer_field_comment)
            HAVESERIAL -> context.getString(R.string.printer_field_haveserial)
            PARALLEL -> context.getString(R.string.printer_field_parallel)
            USB -> context.getString(R.string.printer_field_usb)
            WIFI -> context.getString(R.string.printer_field_wifi)
            ETHERNET -> context.getString(R.string.printer_field_ethernet)
        }
    }

    override fun getFieldsForDevice(context: Context, item: DeviceApi): List<ItemField> {
        if (item !is PrinterApi){
            return emptyList()
        }
        return listOf(
            TextViewField(FieldsNames.NAME.displayableName(context = context), initialValue = item.name),
            TextViewField(FieldsNames.SERIAL_NUMBER.displayableName(context = context), initialValue = item.serial ?: ""),
            TextViewField(FieldsNames.PHONE_NUMBER.displayableName(context = context), initialValue = item.userNumber ?: ""),
            UserPickerField(FieldsNames.USER.displayableName(context = context), initialValue = item.userId.toString()),
            SuperAdminUserPickerField(FieldsNames.USER_TECH.displayableName(context = context), initialValue = item.userTechId.toString()),
            LocationPickerField(FieldsNames.LOCATION.displayableName(context = context), initialValue = item.locationId.toString()),
            GroupPickerField(FieldsNames.GROUPID.displayableName(context = context), initialValue = item.groupIdTech.toString()),
            GroupPickerField(FieldsNames.GROUP.displayableName(context = context), initialValue = item.groupId.toString()),
            StatePickerField(FieldsNames.STATE.displayableName(context = context), initialValue = item.stateId.toString()),
            TextViewField(FieldsNames.MEMORY.displayableName(context = context), initialValue = item.memorySize ?: ""),
            TextViewField(FieldsNames.INITPAGECOUNTER.displayableName(context = context), initialValue = item.initPagesCounter.toString()),
            TextViewField(FieldsNames.LASTPAGECOUNTER.displayableName(context = context), initialValue = item.lastPagesCounter.toString()),
            TypePickerField(FieldsNames.TYPE.displayableName(context = context), initialValue = item.printerTypeId.toString()),
            ManufacturerPickerField(FieldsNames.MANUFACTURER.displayableName(context = context), initialValue = item.manufacturerId.toString()),
            ModelPickerField(FieldsNames.MODEL.displayableName(context = context), initialValue = item.printerModelId.toString()),
            BooleanField(FieldsNames.HAVESERIAL.displayableName(context = context), initialValue = item.haveSerial.toString()),
            BooleanField(FieldsNames.PARALLEL.displayableName(context = context), initialValue = item.haveParallel.toString()),
            BooleanField(FieldsNames.USB.displayableName(context = context), initialValue = item.haveUsb.toString()),
            BooleanField(FieldsNames.WIFI.displayableName(context = context), initialValue = item.haveWifi.toString()),
            BooleanField(FieldsNames.ETHERNET.displayableName(context = context), initialValue = item.haveEthernet.toString()),
            TextViewField(FieldsNames.COMMENT.displayableName(context = context), initialValue = item.comment ?: ""),
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
        val userNumber = fields[FieldsNames.PHONE_NUMBER.displayableName(context = context)]
        val serialNumber = fields[FieldsNames.SERIAL_NUMBER.displayableName(context = context)]
        val printerType = fields[FieldsNames.TYPE.displayableName(context = context)]?.toIntOrNull() ?: 0
        val printerModel = fields[FieldsNames.MODEL.displayableName(context = context)]?.toIntOrNull() ?: 0
        val locationId = fields[FieldsNames.LOCATION.displayableName(context = context)]?.toIntOrNull() ?: 0
        val groupIdTech = fields[FieldsNames.GROUPID.displayableName(context = context)]?.toIntOrNull() ?: 0
        val groupId = fields[FieldsNames.GROUP.displayableName(context = context)]?.toIntOrNull() ?: 0
        val memorySize = fields[FieldsNames.MEMORY.displayableName(context = context)]!!
        val initPageCounter = fields[FieldsNames.INITPAGECOUNTER.displayableName(context = context)]?.toIntOrNull() ?: 0
        val lastPageCounter = fields[FieldsNames.LASTPAGECOUNTER.displayableName(context = context)]?.toIntOrNull() ?: 0
        val userId = fields[FieldsNames.USER.displayableName(context = context)]?.toIntOrNull() ?: 0
        val userTechId = fields[FieldsNames.USER_TECH.displayableName(context = context)]?.toIntOrNull() ?: 0
        val stateId = fields[FieldsNames.STATE.displayableName(context = context)]?.toIntOrNull() ?: 0
        val manufacturerId = fields[FieldsNames.MANUFACTURER.displayableName(context = context)]?.toIntOrNull() ?: 0
        val haveSerial = parseStringBooleanToInt(fields[FieldsNames.HAVESERIAL.displayableName(context = context)] ?: "")
        val haveParallel = parseStringBooleanToInt(fields[FieldsNames.PARALLEL.displayableName(context = context)] ?: "")
        val haveUsb = parseStringBooleanToInt(fields[FieldsNames.USB.displayableName(context = context)] ?: "")
        val haveWifi = parseStringBooleanToInt(fields[FieldsNames.WIFI.displayableName(context = context)] ?: "")
        val haveEthernet = parseStringBooleanToInt(fields[FieldsNames.ETHERNET.displayableName(context = context)] ?: "")
        val comment = fields[FieldsNames.COMMENT.displayableName(context = context)]
        val infoComs = originalItem.infoComs

        return PrinterApi(
            id = id,
            entityId = entityId,
            userNumber = userNumber,
            name = name,
            serial = serialNumber,
            locationId = locationId,
            groupIdTech = groupIdTech,
            groupId = groupId,
            memorySize = memorySize,
            initPagesCounter = initPageCounter,
            lastPagesCounter = lastPageCounter,
            printerTypeId = printerType,
            printerModelId = printerModel,
            _is_template = 0,
            userId = userId,
            userTechId = userTechId,
            stateId = stateId,
            manufacturerId = manufacturerId,
            haveSerial = haveSerial,
            haveParallel = haveParallel,
            haveUsb = haveUsb,
            haveWifi = haveWifi,
            haveEthernet = haveEthernet,
            comment = comment,
            _infocoms = infoComs
        )
    }

    override fun getEmptyItem(): DeviceApi {
        return ItemViewModelEmptiesItems.emptyPrinterApi
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
            needTypes = true,
        )
    }

    override suspend fun fetchDevice(itemsUseCase: ItemsUseCase, deviceId: Int): DeviceApi {
        return itemsUseCase.getPrinterById(deviceId)
    }

    override suspend fun fetchModels(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllPrintersModels()

    override suspend fun fetchTypes(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllPrintersTypes()

    override suspend fun fetchPossiblesStates(itemsUseCase: ItemsUseCase): List<StateApi> {
        return itemsUseCase.getPossiblesStatesToPrinters()
    }

    private fun parseStringBooleanToInt(string: String) = if (string.toIntOrNull() == 1 || string.toBoolean()) 1 else 0
}