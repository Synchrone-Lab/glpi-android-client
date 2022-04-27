package fr.synchrone.glpisupport.data.model.remote.items

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class StateApi(
    @Json(name = "id")
    override val id: Int,
    @Json(name = "name")
    override val name: String,
    @Json(name = "entities_id")
    override val entityId: Int,
    @Json(name = "is_visible_computer")
    val _is_visible_computer: Int,
    @Json(name = "is_visible_phone")
    val _is_visible_phone: Int,
    @Json(name = "is_visible_monitor")
    val _is_visible_monitor: Int,
    @Json(name = "is_visible_printer")
    val _is_visible_printer : Int,
    @Json(name = "is_visible_softwarelicense")
    val _is_visible_softwarelicense: Int,
    @Json(name = "is_visible_rack")
    val _is_visible_rack: Int,
    @Json(name = "is_visible_networkequipment")
    val _is_visible_networkequipment: Int
): Parcelable, ItemApi {

    val isVisibleComputer: Boolean = _is_visible_computer == 1
    val isVisiblePhone: Boolean = _is_visible_phone == 1
    val isVisibleMonitor: Boolean = _is_visible_monitor == 1
    val isVisiblePrinter: Boolean = _is_visible_printer == 1
    val isVisibleSoftwareLicense = _is_visible_softwarelicense == 1
    val isVisibleRack: Boolean = _is_visible_rack == 1
    val isVisibleNetworkEquipment: Boolean = _is_visible_networkequipment == 1

    companion object: ItemApiCompanionObject {
        override val typeName: String = "State"
    }
}