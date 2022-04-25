package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.EntitledApi
import fr.synchrone.glpisupport.data.model.remote.items.StateApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.RackApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.*
import fr.synchrone.glpisupport.presentation.items.viewmodel.PickersElementsGrouped
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerElement

/**
 * Implementation of ItemViewModelHelper for racks.
 *
 * @see ItemViewModelHelper
 */
class ItemViewModelRackHelper(infos: ItemViewModelInfos): ItemViewModelHelper(infos = infos) {

    private enum class FieldsNames {
        NAME,
        SERIAL_NUMBER,
        LOCATION,
        USER_TECH,
        STATE,
        MANUFACTURER,
        TYPE,
        MODEL,
        ROOM,
        DOORORIENTATION,
        NUMUNITS,
        HEIGHT,
        WIDTH,
        DEPTH,
        MAXPOWER,
        MESUREDPOWER,
        MAXWEIGHT,
        COMMENT;

        fun displayableName(context: Context): String = when (this) {
            NAME -> context.getString(R.string.rack_field_name)
            SERIAL_NUMBER -> context.getString(R.string.rack_field_serial_number)
            LOCATION -> context.getString(R.string.rack_field_location)
            USER_TECH -> context.getString(R.string.rack_field_user_tech)
            STATE -> context.getString(R.string.rack_field_state)
            MANUFACTURER -> context.getString(R.string.rack_field_manufacturer)
            TYPE -> context.getString(R.string.rack_field_type)
            MODEL -> context.getString(R.string.rack_field_model)
            ROOM -> context.getString(R.string.rack_field_room)
            DOORORIENTATION -> context.getString(R.string.rack_field_doororientation)
            NUMUNITS -> context.getString(R.string.rack_field_numunits)
            HEIGHT -> context.getString(R.string.rack_field_height)
            WIDTH -> context.getString(R.string.rack_field_width)
            DEPTH -> context.getString(R.string.rack_field_depth)
            MAXPOWER -> context.getString(R.string.rack_field_maxpower)
            MESUREDPOWER -> context.getString(R.string.rack_field_mesuredpower)
            MAXWEIGHT -> context.getString(R.string.rack_field_maxweight)
            COMMENT -> context.getString(R.string.rack_field_comment)
        }
    }

    private enum class OrientationValues(val id: Int){
        EMPTY(id = 0),
        NORTH(id = 1),
        EAST(id = 2),
        SOUTH(id = 3),
        WEST(id = 4);

        fun displayableName(context: Context): String = when (this) {
            EMPTY -> "---"
            NORTH -> context.getString(R.string.rack_room_orientation_north)
            EAST -> context.getString(R.string.rack_room_orientation_east)
            SOUTH -> context.getString(R.string.rack_room_orientation_south)
            WEST -> context.getString(R.string.rack_room_orientation_west)
        }
    }

    override fun areInfosComsEnabled(item: DeviceApi): Boolean = false

    override fun getFieldsForDevice(context: Context, item: DeviceApi): List<ItemField> {
        if (item !is RackApi)
            return listOf()

        return listOf(
            TextViewField(label = FieldsNames.NAME.displayableName(context = context), initialValue = item.name),
            TextViewField(label = FieldsNames.SERIAL_NUMBER.displayableName(context = context), initialValue = item.serial ?: ""),
            LocationPickerField(label = FieldsNames.LOCATION.displayableName(context = context), initialValue = item.locationId.toString()),
            SuperAdminUserPickerField(label = FieldsNames.USER_TECH.displayableName(context = context), initialValue = item.userTechId.toString()),
            DCRoomPickerField(label = FieldsNames.ROOM.displayableName(context = context), initialValue = item.dcroomsId.toString()),
            StaticPickerField(label = FieldsNames.DOORORIENTATION.displayableName(context = context), initialValue = item.roomOrientation.toString()),
            TextViewField(label = FieldsNames.NUMUNITS.displayableName(context = context), initialValue = item.numberUnits.toString()),
            TextViewField(label = FieldsNames.HEIGHT.displayableName(context = context), initialValue = item.height.toString()),
            TextViewField(label = FieldsNames.WIDTH.displayableName(context = context), initialValue = item.width.toString()),
            TextViewField(label = FieldsNames.DEPTH.displayableName(context = context), initialValue = item.depth.toString()),
            TextViewField(label = FieldsNames.MAXWEIGHT.displayableName(context = context), initialValue = item.maxWeight.toString()),
            TextViewField(label = FieldsNames.MAXPOWER.displayableName(context = context), initialValue = item.maxPower.toString()),
            TextViewField(label = FieldsNames.MESUREDPOWER.displayableName(context = context), initialValue = item.mesuredPower.toString()),
            StatePickerField(label = FieldsNames.STATE.displayableName(context = context), initialValue = item.stateId.toString()),
            ManufacturerPickerField(label = FieldsNames.MANUFACTURER.displayableName(context = context), initialValue = item.manufacturerId.toString()),
            TypePickerField(label = FieldsNames.TYPE.displayableName(context = context), initialValue = item.rackTypeId.toString()),
            ModelPickerField(label = FieldsNames.MODEL.displayableName(context = context), initialValue = item.rackModelId.toString()),
            CommentTextField(label = FieldsNames.COMMENT.displayableName(context = context), initialValue = item.comment ?: "")
        )
    }

    override fun getStaticsFieldsForLabel(context: Context, label: String): PickersElementsGrouped {
        when(label){
            FieldsNames.DOORORIENTATION.displayableName(context = context) -> {
                return mapOf(
                    PickerElement(0, "") to OrientationValues.values().map {
                        PickerElement(it.id, it.displayableName(context = context))
                    })
            }
        }
        return mapOf()
    }

    override fun getUpdatedDeviceWithFields(
        context: Context,
        originalItem: DeviceApi,
        fields: Map<String, String>
    ): DeviceApi {
        val id = originalItem.id
        val entityId = originalItem.entityId
        val modelId = fields[FieldsNames.MODEL.displayableName(context = context)]!!.toInt()
        val typeId = fields[FieldsNames.TYPE.displayableName(context = context)]!!.toInt()
        val name = fields[FieldsNames.NAME.displayableName(context = context)]!!
        val serialNumber = fields[FieldsNames.SERIAL_NUMBER.displayableName(context = context)]!!
        val roomOrientation = OrientationValues.values().firstOrNull{
            it.id == fields[FieldsNames.DOORORIENTATION.displayableName(context = context)]?.toIntOrNull()
        }?.id ?: OrientationValues.EMPTY.id
        val room = fields[FieldsNames.ROOM.displayableName(context = context)]?.toIntOrNull() ?: 0
        val height = fields[FieldsNames.HEIGHT.displayableName(context = context)]!!.toInt()
        val width = fields[FieldsNames.WIDTH.displayableName(context = context)]!!.toInt()
        val depth = fields[FieldsNames.DEPTH.displayableName(context = context)]!!.toInt()
        val maxWeight = fields[FieldsNames.MAXWEIGHT.displayableName(context = context)]!!.toInt()
        val maxPower = fields[FieldsNames.MAXPOWER.displayableName(context = context)]!!.toInt()
        val mesuredPower = fields[FieldsNames.MESUREDPOWER.displayableName(context = context)]!!.toInt()
        val numUnits = fields[FieldsNames.NUMUNITS.displayableName(context = context)]!!.toInt()
        val locationId = fields[FieldsNames.LOCATION.displayableName(context = context)]!!.toInt()
        val userTechId = fields[FieldsNames.USER_TECH.displayableName(context = context)]!!.toInt()
        val stateId = fields[FieldsNames.STATE.displayableName(context = context)]!!.toInt()
        val manufacturerId = fields[FieldsNames.MANUFACTURER.displayableName(context = context)]!!.toInt()
        val comment = fields[FieldsNames.COMMENT.displayableName(context = context)]!!

        return RackApi(
            id = id,
            entityId = entityId,
            name = name,
            serial = serialNumber,
            locationId = locationId,
            _is_template = 0,
            userTechId = userTechId,
            stateId = stateId,
            rackTypeId = typeId,
            rackModelId = modelId,
            width = width,
            height = height,
            depth = depth,
            numberUnits = numUnits,
            dcroomsId = room,
            roomOrientation = roomOrientation,
            maxPower = maxPower,
            mesuredPower = mesuredPower,
            maxWeight = maxWeight,
            manufacturerId = manufacturerId,
            comment = comment,
            _infocoms = null
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
            needTypes = true,
            needDCRooms = true
        )
    }

    override fun getEmptyItem(): DeviceApi = ItemViewModelEmptiesItems.emptyRackApi

    override suspend fun fetchModels(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllRacksModels()

    override suspend fun fetchTypes(itemsUseCase: ItemsUseCase): List<EntitledApi> = itemsUseCase.getAllRacksTypes()

    override suspend fun fetchDevice(itemsUseCase: ItemsUseCase, deviceId: Int): DeviceApi = itemsUseCase.getRackById(deviceId)

    override suspend fun fetchPossiblesStates(itemsUseCase: ItemsUseCase): List<StateApi> = itemsUseCase.getPossiblesStatesToRacks()
}