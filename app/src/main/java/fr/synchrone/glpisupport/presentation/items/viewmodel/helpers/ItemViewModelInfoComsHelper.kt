package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.data.model.remote.items.InfoComsApi
import fr.synchrone.glpisupport.presentation.items.*
import fr.synchrone.glpisupport.presentation.items.viewmodel.PickersElementsGrouped
import fr.synchrone.glpisupport.presentation.utilities.functions.toddMMyyyyDate
import fr.synchrone.glpisupport.presentation.utilities.functions.toyyyyMMddDate
import fr.synchrone.glpisupport.presentation.utilities.functions.toddMMyyyyString
import fr.synchrone.glpisupport.presentation.utilities.functions.toyyyyMMddString
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerElement

/**
 * Tell to item view model how to manage item type.
 *
 * @see ItemViewModelHelper
 */
object ItemViewModelInfoComsHelper {

    private enum class FieldsNames {
        ORDER_DATE,
        BUY_DATE,
        DELIVERY_DATE,
        USE_DATE,
        INVENTORY_DATE,
        DECOMMISSION_DATE,
        SUPPLIER,
        ORDER_NUMBER,
        BILL,
        VALUE,
        SINK_TYPE,
        SINK_COEFF,
        SINK_TIME,
        BUSINESS_CRITICITY,
        BUDGET,
        DELIVERY_NUMBER,
        IMMO_NUMBER,
        WARRANTY_VALUE,
        WARRANTY_DATE,
        WARRANTY_INFO,
        ALERT;

        fun displayableName(context: Context): String = when (this) {
            ORDER_DATE -> context.getString(R.string.info_coms_field_order_date)
            BUY_DATE -> context.getString(R.string.info_coms_field_buy_date)
            DELIVERY_DATE -> context.getString(R.string.info_coms_field_delivery_date)
            USE_DATE -> context.getString(R.string.info_coms_field_use_date)
            INVENTORY_DATE -> context.getString(R.string.info_coms_field_inventory_date)
            DECOMMISSION_DATE -> context.getString(R.string.info_coms_field_decommission_date)
            SUPPLIER -> context.getString(R.string.info_coms_field_supplier)
            ORDER_NUMBER -> context.getString(R.string.info_coms_field_order_number)
            BILL -> context.getString(R.string.info_coms_field_bill)
            VALUE -> context.getString(R.string.info_coms_field_value)
            SINK_TYPE -> context.getString(R.string.info_coms_field_sink_type)
            SINK_COEFF -> context.getString(R.string.info_coms_field_sink_coeff)
            SINK_TIME -> context.getString(R.string.info_coms_field_sink_time)
            BUSINESS_CRITICITY -> context.getString(R.string.info_coms_field_business_criticity)
            BUDGET -> context.getString(R.string.info_coms_field_budget)
            DELIVERY_NUMBER -> context.getString(R.string.info_coms_field_delivery_number)
            IMMO_NUMBER -> context.getString(R.string.info_coms_field_immo_number)
            WARRANTY_VALUE -> context.getString(R.string.info_coms_field_warranty_value)
            WARRANTY_DATE -> context.getString(R.string.info_coms_field_warranty_date)
            WARRANTY_INFO -> context.getString(R.string.info_coms_field_warranty_info)
            ALERT -> context.getString(R.string.info_coms_field_alert)
        }
    }

    private enum class SinkTypeValues(val id: Int, val displayableName: String) {
        NONE(id = 0, displayableName = "----"),
        LINEAR(id = 2, displayableName = "Linéaire"),
        DECREASING(id = 1, displayableName = "Dégressif")
    }

    private enum class AlertValues(val id: Int, val displayableName: String) {
        NONE(id = 0, displayableName = "----"),
        EXPIRATION_DATE(id = 4, displayableName = "Date d'expiration de la garantie")
    }

    fun getFieldsForItem(context: Context, item: DeviceApi): List<ItemField> {
        val infoComs = item.infoComs ?: return listOf()
        val orderDateString = getDisplayableDateFromInfoComs(infoComs.orderDate)
        val buyDateString = getDisplayableDateFromInfoComs(infoComs.buyDate)
        val deliveryDate = getDisplayableDateFromInfoComs(infoComs.deliveryDate)
        val useDate = getDisplayableDateFromInfoComs(infoComs.useDate)
        val decommissionDate = getDisplayableDateFromInfoComs(infoComs.decommissionDate)
        val inventoryDate = getDisplayableDateFromInfoComs(infoComs.inventoryDate)
        val supplier = infoComs.supplierId.toString()
        val orderNumber = infoComs.orderNumber ?: ""
        val bill = infoComs.bill ?: ""
        val value = infoComs.value ?: ""
        val sinkType = infoComs.sinkType.toString()
        val sinkCoeff = infoComs.sinkCoeff.toString()
        val sinkTime = infoComs.sinkTime.toString()
        val businessCriticityId = infoComs.businessCriticityId.toString()
        val budgetId = infoComs.budgetId.toString()
        val deliveryNumber = getDisplayableDateFromInfoComs(infoComs.deliveryNumber)
        val immoNumber = infoComs.immoNumber ?: ""
        val warrantyValue = infoComs.warrantyValue.toString()
        val warrantyDate = getDisplayableDateFromInfoComs(infoComs.warrantyDate)
        val warrantyInfo = infoComs.warrantyInfo ?: ""
        val alertId = infoComs.alertId.toString()

        return listOf(
            DatePickerField(label = FieldsNames.ORDER_DATE.displayableName(context = context), initialValue = orderDateString),
            DatePickerField(label = FieldsNames.BUY_DATE.displayableName(context = context), initialValue =  buyDateString),
            DatePickerField(label = FieldsNames.DELIVERY_DATE.displayableName(context = context), initialValue = deliveryDate),
            DatePickerField(label = FieldsNames.USE_DATE.displayableName(context = context), initialValue = useDate),
            DatePickerField(label = FieldsNames.INVENTORY_DATE.displayableName(context = context), initialValue = inventoryDate),
            DatePickerField(label = FieldsNames.DECOMMISSION_DATE.displayableName(context = context), initialValue = decommissionDate),
            SupplierPickerField(label = FieldsNames.SUPPLIER.displayableName(context = context), initialValue = supplier),
            TextViewField(label = FieldsNames.ORDER_NUMBER.displayableName(context = context), initialValue = orderNumber),
            TextViewField(label = FieldsNames.BILL.displayableName(context = context), initialValue = bill),
            TextViewField(label = FieldsNames.VALUE.displayableName(context = context), initialValue = value),
            StaticPickerField(label = FieldsNames.SINK_TYPE.displayableName(context = context), initialValue = sinkType),
            TextViewField(label = FieldsNames.SINK_COEFF.displayableName(context = context), initialValue = sinkCoeff),
            TextViewField(label = FieldsNames.SINK_TIME.displayableName(context = context), initialValue = sinkTime),
            BusinessCriticityPickerField(label = FieldsNames.BUSINESS_CRITICITY.displayableName(context = context), initialValue = businessCriticityId),
            BudgetPickerField(label = FieldsNames.BUDGET.displayableName(context = context), initialValue = budgetId),
            TextViewField(label = FieldsNames.DELIVERY_NUMBER.displayableName(context = context), initialValue = deliveryNumber),
            TextViewField(label = FieldsNames.IMMO_NUMBER.displayableName(context = context), initialValue = immoNumber),
            TextViewField(label = FieldsNames.WARRANTY_VALUE.displayableName(context = context), initialValue = warrantyValue),
            DatePickerField(label = FieldsNames.WARRANTY_DATE.displayableName(context = context), initialValue = warrantyDate),
            TextViewField(label = FieldsNames.WARRANTY_INFO.displayableName(context = context), initialValue = warrantyInfo),
            StaticPickerField(label = FieldsNames.ALERT.displayableName(context = context), initialValue = alertId),
        )
    }

    fun getStaticsFieldsForLabel(context: Context, label: String): PickersElementsGrouped {
        return when (label) {
            FieldsNames.SINK_TYPE.displayableName(context = context) -> {
                mapOf(PickerElement(id = 0, name = "") to SinkTypeValues.values().map {
                    PickerElement(id = it.id, name = it.displayableName)
                })
            }
            FieldsNames.ALERT.displayableName(context = context) -> {
                mapOf(PickerElement(id = 0, name = "") to AlertValues.values().map {
                    PickerElement(id = it.id, name = it.displayableName)
                })
            }
            else -> mapOf()
        }
    }

    fun getUpdatedInfoComsWithFields(context: Context, originalItem: InfoComsApi, fields: Map<String, String>): InfoComsApi {
        val orderDate = getApiFormatDateFromDisplayableDate(fields[FieldsNames.ORDER_DATE.displayableName(context = context)])
        val buyDate = getApiFormatDateFromDisplayableDate(fields[FieldsNames.BUY_DATE.displayableName(context = context)])
        val deliveryDate = getApiFormatDateFromDisplayableDate(fields[FieldsNames.DELIVERY_DATE.displayableName(context = context)])
        val useDate = getApiFormatDateFromDisplayableDate(fields[FieldsNames.USE_DATE.displayableName(context = context)])
        val decommissionDate = getApiFormatDateFromDisplayableDate(fields[FieldsNames.DECOMMISSION_DATE.displayableName(context = context)])
        val inventoryDate = getApiFormatDateFromDisplayableDate(fields[FieldsNames.INVENTORY_DATE.displayableName(context = context)])
        val supplierId = getApiCompatibleStringFromField(fields[FieldsNames.SUPPLIER.displayableName(context = context)])?.toIntOrNull() ?: 0
        val orderNumber = getApiCompatibleStringFromField(fields[FieldsNames.ORDER_NUMBER.displayableName(context = context)])
        val bill = getApiCompatibleStringFromField(fields[FieldsNames.BILL.displayableName(context = context)])
        val value = getApiCompatibleStringFromField(fields[FieldsNames.VALUE.displayableName(context = context)])
        val sinkType = getApiCompatibleStringFromField(fields[FieldsNames.SINK_TYPE.displayableName(context = context)])?.toIntOrNull() ?: 0
        val sinkCoeff = getApiCompatibleStringFromField(fields[FieldsNames.SINK_COEFF.displayableName(context = context)])?.toFloatOrNull() ?: 0f
        val sinkTime = getApiCompatibleStringFromField(fields[FieldsNames.SINK_TIME.displayableName(context = context)])?.toIntOrNull() ?: 0
        val businessCriticityId = getApiCompatibleStringFromField(fields[FieldsNames.BUSINESS_CRITICITY.displayableName(context = context)])?.toIntOrNull() ?: 0
        val budgetId = getApiCompatibleStringFromField(fields[FieldsNames.BUDGET.displayableName(context = context)])?.toIntOrNull() ?: 0
        val deliveryNumber = getApiCompatibleStringFromField(fields[FieldsNames.DELIVERY_NUMBER.displayableName(context = context)])
        val immoNumber = getApiCompatibleStringFromField(fields[FieldsNames.IMMO_NUMBER.displayableName(context = context)])
        val warrantyValue = getApiCompatibleStringFromField(fields[FieldsNames.WARRANTY_VALUE.displayableName(context = context)])?.toFloatOrNull() ?: 0f
        val warrantyDate = getApiFormatDateFromDisplayableDate(fields[FieldsNames.WARRANTY_DATE.displayableName(context = context)])
        val warrantyInfo = getApiCompatibleStringFromField(fields[FieldsNames.WARRANTY_INFO.displayableName(context = context)])
        val alertId = getApiCompatibleStringFromField(fields[FieldsNames.ALERT.displayableName(context = context)])?.toIntOrNull() ?: 0

        return InfoComsApi(
            id = originalItem.id,
            entityId = originalItem.entityId,
            orderDate = orderDate,
            buyDate = buyDate,
            deliveryDate = deliveryDate,
            useDate = useDate,
            inventoryDate = inventoryDate,
            decommissionDate = decommissionDate,
            supplierId = supplierId,
            orderNumber = orderNumber,
            bill = bill,
            value = value,
            sinkType = sinkType,
            sinkCoeff = sinkCoeff,
            sinkTime = sinkTime,
            businessCriticityId = businessCriticityId,
            budgetId = budgetId,
            deliveryNumber = deliveryNumber,
            immoNumber = immoNumber,
            warrantyValue = warrantyValue,
            warrantyDate = warrantyDate,
            warrantyInfo = warrantyInfo,
            alertId = alertId
        )
    }

    /**
     * Take an optional yyyy-MM-dd date string and convert it to displayable french date format dd-MM-yyyy.
     *
     * @param dateString yyyy-MM-dd string that need to be converted.
     * @return An dd-MM-yyyy string french displayable format or an empty String if dateString is null or invalid.
     */
    private fun getDisplayableDateFromInfoComs(dateString: String?): String {
        val dateStringNotNull = dateString ?: return ""
        val date = dateStringNotNull.toyyyyMMddDate() ?: return ""
        return date.toddMMyyyyString()
    }

    /**
     * Take an optional date string and convert it to yyyy-MM-dd format.
     *
     * @param dateString dd-MM-yyyy or yyyy-MM-dd string that need to be converted.
     * @return An yyyy-MM-dd string that match api format or null if dateString is null or invalid.
     */
    private fun getApiFormatDateFromDisplayableDate(dateString: String?): String? {
        val dateStringNotNull = dateString ?: return null
        return if (dateString.toyyyyMMddDate() != null)
            dateString
        else {
            val date = dateStringNotNull.toddMMyyyyDate()
            date?.toyyyyMMddString()
        }
    }

    private fun getApiCompatibleStringFromField(string: String?): String? = string?.trim()


}