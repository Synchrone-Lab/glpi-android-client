package fr.synchrone.glpisupport.data.model.remote.user.myprofiles

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class MyProfilesApi (
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "entities")
    val entities: List<EntityApi>
) : Parcelable