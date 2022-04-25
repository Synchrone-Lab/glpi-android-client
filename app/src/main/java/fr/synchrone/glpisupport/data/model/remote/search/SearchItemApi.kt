package fr.synchrone.glpisupport.data.model.remote.search


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class SearchItemApi(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "itemtype")
    val itemtype: String
) : Parcelable