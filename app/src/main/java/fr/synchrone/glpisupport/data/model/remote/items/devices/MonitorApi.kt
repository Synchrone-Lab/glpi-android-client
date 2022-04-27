package fr.synchrone.glpisupport.data.model.remote.items.devices

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.synchrone.glpisupport.data.model.remote.items.InfoComsApi
import fr.synchrone.glpisupport.data.model.remote.items.ItemApiCompanionObject

@SuppressLint("ParcelCreator")
@JsonClass(generateAdapter = true)
data class MonitorApi(
    @Json(name = "id")
    override val id: Int,
    @Json(name = "name")
    override val name: String,
    @Json(name = "entities_id")
    override val entityId: Int,
    @Json(name = "locations_id")
    val locationId: Int,
    @Json(name = "is_template")
    val _is_template: Int,
    @Json(name = "users_id_tech")
    val userTechId: Int,
    @Json(name = "users_id")
    val userId: Int,
    @Json(name = "states_id")
    val stateId: Int,
    @Json(name = "manufacturers_id")
    val manufacturerId: Int,
    @Json(name = "monitormodels_id")
    val monitorModelId: Int,
    @Json(name = "monitortypes_id")
    val monitorTypeId: Int,
    @Json(name = "serial")
    override val serial: String?,
    @Json(name = "have_micro")
    val _have_micro: Int,
    @Json(name = "have_speaker")
    val _have_speaker: Int,
    @Json(name = "have_subd")
    val _have_subd: Int,
    @Json(name = "have_bnc")
    val _have_bnc: Int,
    @Json(name = "have_dvi")
    val _have_dvi: Int,
    @Json(name = "have_pivot")
    val _have_pivot: Int,
    @Json(name = "have_hdmi")
    val _have_hdmi: Int,
    @Json(name = "have_displayport")
    val _have_displayport: Int,
    @Json(name = "comment")
    val comment: String?,
    @Json(name = "_infocoms")
    override val _infocoms: Any?
): DeviceWithSerialApi() {
    val isTemplate = _is_template == 1
    val haveMicro = _have_micro == 1
    val haveSpeaker = _have_speaker == 1
    val haveSubd = _have_subd == 1
    val haveBnc = _have_bnc == 1
    val haveDvi = _have_dvi == 1
    val haveHdmi = _have_hdmi == 1
    val havePivot = _have_pivot == 1
    val haveDisplayPort = _have_displayport == 1

    override fun newInstanceWithSerial(serial: String): DeviceWithSerialApi = copy(serial = serial)

    override fun newInstanceWithInfoComs(infoComs: InfoComsApi): DeviceApi = copy(_infocoms = infoComs)

    companion object: ItemApiCompanionObject {
        override val typeName: String = "Monitor"
    }
}