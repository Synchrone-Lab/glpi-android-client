package fr.synchrone.glpisupport.data.model.remote.items

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class DCRoomApi (
    @Json(name = "id")
    override val id: Int,
    @Json(name = "name")
    override val name: String,
    @Json(name = "entities_id")
    override val entityId: Int,
    @Json(name = "locations_id")
    val locationId: Int,
    @Json(name = "vis_cols")
    val visCols : Int,
    @Json(name = "vis_rows")
    val visRows : Int,
    @Json(name = "datacenters_id")
    val datacenterId : Int
): Parcelable, ItemApi {
    companion object: ItemApiCompanionObject {
        override val typeName: String = "dcroom"
    }
}