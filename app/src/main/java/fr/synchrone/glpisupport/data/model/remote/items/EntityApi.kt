package fr.synchrone.glpisupport.data.model.remote.items


import android.annotation.SuppressLint
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class EntityApi(
    @Json(name = "id")
    val entityId: Int,
    @Json(name = "is_recursive")
    val isRecursive: Int?,
    @Json(name = "name")
    val name: String,
    @Json(name = "ancestors_cache")
    val _ancestorsCache: String?
) : Parcelable {
    val ancestorsCache: List<Int>? = run {
        val ancestorCacheString = _ancestorsCache ?: return@run null
        val regex = """(\d+)""".toRegex()
        val matchesResults = regex.findAll(ancestorCacheString)
        val numbersMatched = mutableListOf<Int>()
        matchesResults.forEach {
            numbersMatched.add(it.groupValues[1].toInt())
        }
        numbersMatched.distinct()
    }
}