package fr.synchrone.glpisupport.data.model.remote.search

import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@SuppressLint("ParcelCreator")
@JsonClass(generateAdapter = true)
data class MultiCriteriaSearchApi(
    @field:Json(name = "data")
    val searchItems: List<Map<String, Any>>?
)