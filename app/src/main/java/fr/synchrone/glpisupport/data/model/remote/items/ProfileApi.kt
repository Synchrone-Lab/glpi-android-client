package fr.synchrone.glpisupport.data.model.remote.items


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class ProfileApi(
    @Json(name = "id")
    val profileId: Int,
    @Json(name = "name")
    val name: String
) : Parcelable {
    companion object {
        const val superAdminName = "Super-Admin"
    }
}