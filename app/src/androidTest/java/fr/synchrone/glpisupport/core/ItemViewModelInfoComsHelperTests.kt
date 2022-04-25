package fr.synchrone.glpisupport.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelInfoComsHelper
import fr.synchrone.glpisupport.resources.BaseItemsContent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ItemViewModelInfoComsHelperTests {

    enum class FieldsNames(val displayableName: String) {
        ORDER_DATE(displayableName = "Date de commande"),
        BUY_DATE(displayableName = "Date d'achat"),
        DELIVERY_DATE(displayableName = "Date de livraison"),
        USE_DATE(displayableName = "Date de mise en service"),
        INVENTORY_DATE(displayableName = "Date du dernier inventaire physique"),
        DECOMMISSION_DATE(displayableName = "Date de réforme"),
        SUPPLIER(displayableName = "Fournisseur"),
        ORDER_NUMBER(displayableName = "Numéro de commande"),
        BILL(displayableName = "Numéro de facture"),
        VALUE(displayableName = "Valeur"),
        SINK_TYPE(displayableName = "Type d'amortissement"),
        SINK_COEFF(displayableName = "Coefficient d'amortissement"),
        SINK_TIME(displayableName = "Durée de l'amortissement (années)"),
        BUSINESS_CRITICITY(displayableName = "Criticité business"),
        BUDGET(displayableName = "Budget"),
        DELIVERY_NUMBER(displayableName = "Bon de livraison"),
        IMMO_NUMBER(displayableName = "Numéro d'immobilisation"),
        WARRANTY_VALUE(displayableName = "Valeur extension garantie"),
        WARRANTY_DATE(displayableName = "Date de début de garantie"),
        WARRANTY_INFO(displayableName = "Informations sur la garantie"),
        ALERT(displayableName = "Alerte sur les info. fi. & admin.")
    }

    enum class SinkTypeValues(val id: Int, val displayableName: String) {
        NONE(id = 0, displayableName = "----"),
        LINEAR(id = 2, displayableName = "Linéaire"),
        DECREASING(id = 1, displayableName = "Dégressif")
    }

    enum class AlertValues(val id: Int, val displayableName: String) {
        NONE(id = 0, displayableName = "----"),
        EXPIRATION_DATE(id = 4, displayableName = "Date d'expiration de la garantie")
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Inject
    lateinit var itemsUseCase: ItemsUseCase

    val context: Context = ApplicationProvider.getApplicationContext()

    private val helper = ItemViewModelInfoComsHelper

    private val itemToTest = BaseItemsContent.infoComsApi

    @Test
    fun test_if_getFieldsForItem_works() {
        val deviceItem = ItemViewModelEmptiesItems.emptyComputerApi.copy(_infocoms = itemToTest)
        val result = helper.getFieldsForItem(context = context, item = deviceItem)

        assert(result.first { it.label == FieldsNames.ORDER_DATE.displayableName }.initialValue == "01-01-2021")
        assert(result.first { it.label == FieldsNames.BUY_DATE.displayableName }.initialValue == "02-01-2021")
        assert(result.first { it.label == FieldsNames.DELIVERY_DATE.displayableName }.initialValue == "03-01-2021")
        assert(result.first { it.label == FieldsNames.USE_DATE.displayableName }.initialValue == "04-01-2021")
        assert(result.first { it.label == FieldsNames.INVENTORY_DATE.displayableName }.initialValue == "")
        assert(result.first { it.label == FieldsNames.DECOMMISSION_DATE.displayableName }.initialValue == "")
        assert(result.first { it.label == FieldsNames.SUPPLIER.displayableName }.initialValue == 3.toString())
        assert(result.first { it.label == FieldsNames.ORDER_NUMBER.displayableName }.initialValue == "")
        assert(result.first { it.label == FieldsNames.BILL.displayableName }.initialValue == "")
        assert(result.first { it.label == FieldsNames.VALUE.displayableName }.initialValue == "123")
        assert(result.first { it.label == FieldsNames.SINK_TYPE.displayableName }.initialValue == 1.toString())
        assert(result.first { it.label == FieldsNames.SINK_COEFF.displayableName }.initialValue == 3f.toString())
        assert(result.first { it.label == FieldsNames.SINK_TIME.displayableName }.initialValue == 2.toString())
        assert(result.first { it.label == FieldsNames.BUSINESS_CRITICITY.displayableName }.initialValue == 4.toString())
        assert(result.first { it.label == FieldsNames.BUDGET.displayableName }.initialValue == 5.toString())
        assert(result.first { it.label == FieldsNames.DELIVERY_NUMBER.displayableName }.initialValue == "05-01-2021")
        assert(result.first { it.label == FieldsNames.IMMO_NUMBER.displayableName }.initialValue == "")
        assert(result.first { it.label == FieldsNames.WARRANTY_VALUE.displayableName }.initialValue == 6f.toString())
        assert(result.first { it.label == FieldsNames.WARRANTY_DATE.displayableName }.initialValue == "")
        assert(result.first { it.label == FieldsNames.WARRANTY_INFO.displayableName }.initialValue == "")
        assert(result.first { it.label == FieldsNames.ALERT.displayableName }.initialValue == 7.toString())
    }

    @Test
    fun test_if_getUpdatedDeviceWithField() {
        val orderDate = "01-01-2022"
        val buyDate = "02-01-2022"
        val deliveryDate = "03-01-2022"
        val useDate = ""
        val decommissionDate = "04-01-2022"
        val inventoryDate = "05-01-2022"
        val supplierId = 101
        val orderNumber = "new order number"
        val bill = "new bill"
        val value = "new value"
        val sinkType = 104
        val sinkCoeff = 105f
        val sinkTime = 106
        val businessCriticityId = 107
        val budgetId = 108
        val deliveryNumber = "new delivery number"
        val immoNumber = null
        val warrantyValue = 110f
        val warrantyDate = "06-01-2022"
        val warrantyInfo = "new warranty info"
        val alertId = 111

        val result = helper.getUpdatedInfoComsWithFields(
            context = context,
            originalItem = itemToTest,
            fields = mapOf(
                FieldsNames.ORDER_DATE.displayableName to orderDate,
                FieldsNames.BUY_DATE.displayableName to buyDate,
                FieldsNames.DELIVERY_DATE.displayableName to deliveryDate,
                FieldsNames.USE_DATE.displayableName to useDate,
                FieldsNames.DECOMMISSION_DATE.displayableName to decommissionDate,
                FieldsNames.INVENTORY_DATE.displayableName to inventoryDate,
                FieldsNames.SUPPLIER.displayableName to supplierId.toString(),
                FieldsNames.ORDER_NUMBER.displayableName to orderNumber,
                FieldsNames.BILL.displayableName to bill,
                FieldsNames.VALUE.displayableName to value,
                FieldsNames.SINK_TYPE.displayableName to sinkType.toString(),
                FieldsNames.SINK_COEFF.displayableName to sinkCoeff.toString(),
                FieldsNames.SINK_TIME.displayableName to sinkTime.toString(),
                FieldsNames.BUSINESS_CRITICITY.displayableName to businessCriticityId.toString(),
                FieldsNames.BUDGET.displayableName to budgetId.toString(),
                FieldsNames.DELIVERY_NUMBER.displayableName to deliveryNumber,
                FieldsNames.IMMO_NUMBER.displayableName to "",
                FieldsNames.WARRANTY_VALUE.displayableName to warrantyValue.toString(),
                FieldsNames.WARRANTY_DATE.displayableName to warrantyDate,
                FieldsNames.WARRANTY_INFO.displayableName to warrantyInfo,
                FieldsNames.ALERT.displayableName to alertId.toString(),
            )
        )

        val expectedItem = itemToTest.copy(
            orderDate = orderDate,
            buyDate = buyDate,
            deliveryDate = deliveryDate,
            useDate = null,
            decommissionDate = decommissionDate,
            inventoryDate = inventoryDate,
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

        assert(result.hashCode() == expectedItem.hashCode())
    }

    @Test
    fun text_if_getStaticsFieldsForLabel_works_with_sink_type() {
        val result = helper.getStaticsFieldsForLabel(context = context, label = FieldsNames.SINK_TYPE.displayableName).values.first()

        assert(result.map { it.id }.sorted() == SinkTypeValues.values().map { it.id }.sorted())
        assert(result.map { it.name }.sorted() == SinkTypeValues.values().map { it.displayableName }.sorted())
    }

    @Test
    fun text_if_getStaticsFieldsForLabel_works_with_alert_type() {
        val result = helper.getStaticsFieldsForLabel(context = context, label = FieldsNames.ALERT.displayableName).values.first()

        assert(result.map { it.id }.sorted() == AlertValues.values().map { it.id }.sorted())
        assert(result.map { it.name }.sorted() == AlertValues.values().map { it.displayableName }.sorted())
    }

    @Test
    fun text_if_getStaticsFieldsForLabel_works_with_wrong_value() {
        val result = helper.getStaticsFieldsForLabel(context = context, label = "this is not a good value :(")

        assert(result.isEmpty())
    }
}