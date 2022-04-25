package fr.synchrone.glpisupport.data.model.remote.items

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class UserApi(
    @Json(name = "id")
    override val id: Int,
    @Json(name = "name")
    override val name: String,
    @Json(name = "entities_id")
    override val entityId: Int,
    @Json(name = "profiles_id")
    val profileId: Int,
    @Json(name = "realname")
    val realname: String?,
    @Json(name = "firstname")
    val firstname: String?,
): Parcelable, ItemApi {

    val fullname: String
    get() {
        return if (realname != null && firstname != null) {
            "$realname $firstname"
        } else if (realname == null && firstname == null) {
            name
        } else {
            realname ?: firstname ?: ""
        }
    }

    companion object: ItemApiCompanionObject {
        override val typeName: String = "user"
    }
}