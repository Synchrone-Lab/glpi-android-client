package fr.synchrone.glpisupport.data.model.remote.search


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class SearchSpecificItemTypeApi(
    @field:Json(name = "data")
    val _searchItems: List<SearchSpecificItemTypeItemApi>?,
    @field:Json(name = "totalcount")
    val totalcount: Int
) : Parcelable {
    val searchItems : List<SearchSpecificItemTypeItemApi> = _searchItems ?: listOf()
}