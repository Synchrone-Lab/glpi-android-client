package fr.synchrone.glpisupport.data.model.remote.items.devices

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.synchrone.glpisupport.data.model.remote.items.InfoComsApi
import fr.synchrone.glpisupport.data.model.remote.items.ItemApiCompanionObject

@SuppressLint("ParcelCreator")
@JsonClass(generateAdapter = true)
data class RackApi (
    @Json(name = "id")
    override val id: Int,
    @Json(name = "name")
    override val name: String,
    @Json(name = "entities_id")
    override val entityId: Int,
    @Json(name = "locations_id")
    val locationId: Int,
    @Json(name = "racktypes_id")
    val rackTypeId : Int,
    @Json(name = "rackmodels_id")
    val rackModelId : Int,
    @Json(name = "width")
    val width : Int,
    @Json(name = "height")
    val height : Int,
    @Json(name = "depth")
    val depth : Int,
    @Json(name = "number_units")
    val numberUnits : Int,
    @Json(name = "dcrooms_id")
    val dcroomsId : Int,
    @Json(name = "room_orientation")
    val roomOrientation : Int,
    @Json(name = "max_power")
    val maxPower : Int,
    @Json(name = "mesured_power")
    val mesuredPower : Int,
    @Json(name = "max_weight")
    val maxWeight : Int,

    @Json(name = "users_id_tech")
    val userTechId: Int,
    @Json(name = "is_template")
    val _is_template: Int,
    @Json(name = "states_id")
    val stateId: Int,
    @Json(name = "manufacturers_id")
    val manufacturerId: Int,
    @Json(name = "serial")
    override val serial: String?,
    @Json(name = "comment")
    val comment: String?,
    override val _infocoms: Any? = null
): DeviceWithSerialApi() {
    val isTemplate = _is_template == 1

    override fun newInstanceWithSerial(serial: String): DeviceWithSerialApi = copy(serial = serial)

    override fun newInstanceWithInfoComs(infoComs: InfoComsApi): DeviceApi = copy(_infocoms = null) //No infocoms for rack

    companion object: ItemApiCompanionObject {
        override val typeName: String = "rack"
    }
}