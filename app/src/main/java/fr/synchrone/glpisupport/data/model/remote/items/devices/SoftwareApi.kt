package fr.synchrone.glpisupport.data.model.remote.items.devices

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.synchrone.glpisupport.data.model.remote.items.InfoComsApi
import fr.synchrone.glpisupport.data.model.remote.items.ItemApiCompanionObject

@SuppressLint("ParcelCreator")
@JsonClass(generateAdapter = true)
data class SoftwareApi(
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
    @Json(name = "groups_id_tech")
    val groupTechId: Int,
    @Json(name = "users_id")
    val userId: Int,
    @Json(name = "manufacturers_id")
    val manufacturerId: Int,
    @Json(name = "softwarecategories_id")
    val softwareCategoryId: Int,
    @Json(name = "is_helpdesk_visible")
    val _isHelpdeskVisible: Int,
    @Json(name = "comment")
    val comment: String?,
    @Json(name = "_infocoms")
    override val _infocoms: Any?
): DeviceApi() {
    val isTemplate = _is_template == 1

    override fun newInstanceWithInfoComs(infoComs: InfoComsApi): DeviceApi = copy(_infocoms = infoComs)

    companion object: ItemApiCompanionObject {
        override val typeName: String = "software"
    }
}