package fr.synchrone.glpisupport.data.model.remote.items

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class ProfileUserApi(
    @Json(name = "id")
    val id: Int,
    @Json(name = "profiles_id")
    val profileId: Int,
    @Json(name = "users_id")
    val userId: Int,
    @Json(name = "entities_id")
    val entityId: Int,
    @Json(name = "is_recursive")
    val _is_recursive: Int
) : Parcelable {

    val isRecursive: Boolean = _is_recursive == 1

    companion object
}