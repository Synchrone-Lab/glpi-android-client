package fr.synchrone.glpisupport.data.model.remote.items.devices

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.synchrone.glpisupport.data.model.remote.items.InfoComsApi
import fr.synchrone.glpisupport.data.model.remote.items.ItemApiCompanionObject

@SuppressLint("ParcelCreator")
@JsonClass(generateAdapter = true)
data class SoftwareLicenseApi(
    @Json(name = "id")
    override val id: Int,
    @Json(name = "name")
    override val name: String,
    @Json(name = "entities_id")
    override val entityId: Int,
    @Json(name = "softwares_id")
    val softwareId: Int,
    @Json(name = "softwarelicenses_id")
    val softwareLicenseId: Int,
    @Json(name = "locations_id")
    val locationId: Int,
    @Json(name = "is_template")
    val _is_template: Int,
    @Json(name = "users_id_tech")
    val userTechId: Int,
    @Json(name = "groups_id_tech")
    val groupTechId: Int,
    @Json(name = "groups_id")
    val groupId: Int,
    @Json(name = "number")
    val number: Int,
    @Json(name = "allow_overquota")
    val _allow_overquota: Int,
    @Json(name = "users_id")
    val userId: Int,
    @Json(name = "states_id")
    val stateId: Int,
    @Json(name = "manufacturers_id")
    val manufacturerId: Int,
    @Json(name = "softwarelicensetypes_id")
    val softwareLicenceTypeId: Int,
    @Json(name = "expire")
    val expire: String?,
    @Json(name = "comment")
    val comment: String?,
    @Json(name = "_infocoms")
    override val _infocoms: Any?
): DeviceApi() {
    val isTemplate = _is_template == 1
    val allowOverquota = _allow_overquota == 1

    override fun newInstanceWithInfoComs(infoComs: InfoComsApi): DeviceApi = copy(_infocoms = infoComs)

    companion object: ItemApiCompanionObject {
        override val typeName: String = "softwarelicense"
    }
}