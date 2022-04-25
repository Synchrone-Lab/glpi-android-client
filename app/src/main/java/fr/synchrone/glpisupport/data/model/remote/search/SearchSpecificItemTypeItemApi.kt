package fr.synchrone.glpisupport.data.model.remote.search


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class SearchSpecificItemTypeItemApi(
    @field:Json(name = "2")
    val id: Int
) : Parcelable