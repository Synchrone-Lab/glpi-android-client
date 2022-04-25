package fr.synchrone.glpisupport.data.model.remote.items.responses

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class PostItemResponseApi(
    @Json(name = "id")
    val id: Int,
    @Json(name = "message")
    val message: String
): Parcelable