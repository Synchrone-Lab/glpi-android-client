package fr.synchrone.glpisupport.data.model.remote.user.myprofiles

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)

data class MyProfilesResponseApi(
    @Json(name = "myprofiles")
    val myProfiles: List<MyProfilesApi>
) : Parcelable