package fr.synchrone.glpisupport.data.model.remote.user.myentities

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class ActiveEntitiesResponseApi(
    @Json(name = "active_entity")
    val activeEntity: ActiveEntityApi
) : Parcelable