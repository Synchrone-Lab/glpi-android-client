package fr.synchrone.glpisupport.data.model.remote.user.myentities

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class MyEntitiesResponseApi(
    @Json(name = "myentities")
    val myEntities: List<EntityApi>
) : Parcelable