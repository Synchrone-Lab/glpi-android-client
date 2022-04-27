package fr.synchrone.glpisupport.data.model.remote.items

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class PhoneTypeApi(
    @Json(name = "id")
    override val id: Int,
    @Json(name = "name")
    override val name: String
): Parcelable, EntitledApi {
    companion object: ItemApiCompanionObject {
        override val typeName: String = "PhoneType"
    }
}