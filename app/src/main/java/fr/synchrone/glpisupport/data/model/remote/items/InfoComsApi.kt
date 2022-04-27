package fr.synchrone.glpisupport.data.model.remote.items

import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class InfoComsApi(
    @Json(name = "id")
    override val id: Int,
    @Json(name = "entities_id")
    override val entityId: Int,
    @Json(name = "order_date")
    val orderDate: String?,
    @Json(name = "buy_date")
    val buyDate: String?,
    @Json(name = "delivery_date")
    val deliveryDate: String?,
    @Json(name = "use_date")
    val useDate: String?,
    @Json(name = "inventory_date")
    val inventoryDate: String?,
    @Json(name = "decommission_date")
    val decommissionDate: String?,
    @Json(name = "suppliers_id")
    val supplierId: Int,
    @Json(name = "order_number")
    val orderNumber: String?,
    @Json(name = "bill")
    val bill: String?,
    @Json(name = "value")
    val value: String?,
    @Json(name = "sink_type")
    val sinkType: Int,
    @Json(name = "sink_coeff")
    val sinkCoeff: Float,
    @Json(name = "sink_time")
    val sinkTime: Int,
    @Json(name = "businesscriticities_id")
    val businessCriticityId: Int,
    @Json(name = "budgets_id")
    val budgetId: Int,
    @Json(name = "delivery_number")
    val deliveryNumber: String?,
    @Json(name = "immo_number")
    val immoNumber: String?,
    @Json(name = "warranty_value")
    val warrantyValue: Float,
    @Json(name = "warranty_date")
    val warrantyDate: String?,
    @Json(name = "warranty_info")
    val warrantyInfo: String?,
    @Json(name = "alert")
    val alertId: Int
): Parcelable, ItemApi {

    override val name = ""

    companion object: ItemApiCompanionObject {
        override val typeName: String = "InfoCom"
    }
}