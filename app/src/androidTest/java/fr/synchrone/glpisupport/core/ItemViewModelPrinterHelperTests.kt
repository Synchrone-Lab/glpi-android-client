package fr.synchrone.glpisupport.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.model.remote.items.PrinterModelApi
import fr.synchrone.glpisupport.data.model.remote.items.PrinterTypeApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.PrinterApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelInfos
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelPrinterHelper
import fr.synchrone.glpisupport.resources.BaseItemsContent
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ItemViewModelPrinterHelperTests {

    private enum class FieldsNames(val displayableName: String) {
        NAME(displayableName = "Nom"),
        SERIAL_NUMBER(displayableName = "Numéro de série"),
        PHONE_NUMBER(displayableName = "Usager Numéro"),
        LOCATION(displayableName = "Lieu"),
        USER(displayableName = "Utilisateur"),
        USER_TECH(displayableName = "Responsable technique"),
        GROUPID(displayableName = "Groupe technique"),
        GROUP(displayableName = "Groupe"),
        MEMORY(displayableName = "Mémoire"),
        INITPAGECOUNTER(displayableName = "Compteur de page initial"),
        LASTPAGECOUNTER(displayableName = "Compteur de page actuel"),
        STATE(displayableName = "Statut"),
        MANUFACTURER(displayableName = "Fabricant"),
        TYPE(displayableName = "Type"),
        MODEL(displayableName = "Modèle"),
        COMMENT(displayableName = "Commentaire"),
        HAVESERIAL(displayableName = "Port Série"),
        PARALLEL(displayableName = "Port Parallel"),
        USB(displayableName = "Port USB"),
        WIFI(displayableName = "Port WIFI"),
        ETHERNET(displayableName = "Port Ethernet")
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

    private val helper = ItemViewModelPrinterHelper(infos = ItemViewModelInfos(isCreation = false))

    private val itemToTest = BaseItemsContent.printerApi

    @Test
    fun test_if_getFieldsForDevice_works() {
        val result = helper.getFieldsForDevice(context = context, item = itemToTest)

        assert(result.first { it.label == FieldsNames.NAME.displayableName }.initialValue == "printer test")
        assert(result.first { it.label == FieldsNames.SERIAL_NUMBER.displayableName }.initialValue == "123456789")
        assert(result.first { it.label == FieldsNames.PHONE_NUMBER.displayableName }.initialValue == "0601020304")
        assert(result.first { it.label == FieldsNames.LOCATION.displayableName }.initialValue == 3.toString())
        assert(result.first { it.label == FieldsNames.USER.displayableName }.initialValue == 5.toString())
        assert(result.first { it.label == FieldsNames.USER_TECH.displayableName }.initialValue == 6.toString())
        assert(result.first { it.label == FieldsNames.GROUPID.displayableName }.initialValue == 8.toString())
        assert(result.first { it.label == FieldsNames.GROUP.displayableName }.initialValue == 7.toString())
        assert(result.first { it.label == FieldsNames.STATE.displayableName }.initialValue == 9.toString())
        assert(result.first { it.label == FieldsNames.MEMORY.displayableName }.initialValue == "128")
        assert(result.first { it.label == FieldsNames.INITPAGECOUNTER.displayableName }.initialValue == 17.toString())
        assert(result.first { it.label == FieldsNames.LASTPAGECOUNTER.displayableName }.initialValue == 18.toString())
        assert(result.first { it.label == FieldsNames.TYPE.displayableName }.initialValue == 10.toString())
        assert(result.first { it.label == FieldsNames.MODEL.displayableName }.initialValue == 11.toString())
        assert(result.first { it.label == FieldsNames.MANUFACTURER.displayableName }.initialValue == 12.toString())
        assert(result.first { it.label == FieldsNames.COMMENT.displayableName }.initialValue == "comment test")
        assert(result.first { it.label == FieldsNames.HAVESERIAL.displayableName }.initialValue == 14.toString())
        assert(result.first { it.label == FieldsNames.PARALLEL.displayableName }.initialValue == 13.toString())
        assert(result.first { it.label == FieldsNames.USB.displayableName }.initialValue == 15.toString())
        assert(result.first { it.label == FieldsNames.WIFI.displayableName }.initialValue == 16.toString())
        assert(result.first { it.label == FieldsNames.ETHERNET.displayableName }.initialValue == 12.toString())
    }

    @Test
    fun test_if_getUpdatedDeviceWithField() {
        val newName = "new name"
        val newSerial = "new serial"
        val newPhoneNumber = "new phone number"
        val newLocationId = 100
        val newUserId = 101
        val newUserTechId = 102
        val newGroupId = 115
        val newGroupTechId = 116
        val newMemorySize = "8 To"
        val newInitPageCounter = 789
        val newLastPageCounter = 888
        val newStateId = 103
        val newTypeId = 104
        val newModelId = 105
        val newManufacturerId = 106
        val newHaveSerial = 0
        val newHaveParallel = 0
        val newHaveUsb = 0
        val newHaveWifi = 0
        val newHaveEthernet = 0
        val newComment = "new comment"

        val result = helper.getUpdatedDeviceWithFields(
            context = context,
            originalItem = itemToTest,
            fields = mapOf(
                FieldsNames.NAME.displayableName to newName,
                FieldsNames.SERIAL_NUMBER.displayableName to newSerial,
                FieldsNames.PHONE_NUMBER.displayableName to newPhoneNumber,
                FieldsNames.LOCATION.displayableName to newLocationId.toString(),
                FieldsNames.USER.displayableName to newUserId.toString(),
                FieldsNames.USER_TECH.displayableName to newUserTechId.toString(),
                FieldsNames.GROUP.displayableName to newGroupId.toString(),
                FieldsNames.GROUPID.displayableName to newGroupTechId.toString(),
                FieldsNames.MEMORY.displayableName to newMemorySize,
                FieldsNames.INITPAGECOUNTER.displayableName to newInitPageCounter.toString(),
                FieldsNames.LASTPAGECOUNTER.displayableName to newLastPageCounter.toString(),
                FieldsNames.STATE.displayableName to newStateId.toString(),
                FieldsNames.TYPE.displayableName to newTypeId.toString(),
                FieldsNames.MODEL.displayableName to newModelId.toString(),
                FieldsNames.MANUFACTURER.displayableName to newManufacturerId.toString(),
                FieldsNames.HAVESERIAL.displayableName to newHaveSerial.toString(),
                FieldsNames.PARALLEL.displayableName to newHaveParallel.toString(),
                FieldsNames.USB.displayableName to newHaveUsb.toString(),
                FieldsNames.WIFI.displayableName to newHaveWifi.toString(),
                FieldsNames.ETHERNET.displayableName to newHaveEthernet.toString(),
                FieldsNames.COMMENT.displayableName to newComment,
            )
        )

        val expectedItem = itemToTest.copy(
            name = newName,
            serial = newSerial,
            userNumber = newPhoneNumber,
            locationId = newLocationId,
            userId = newUserId,
            userTechId = newUserTechId,
            groupId = newGroupId,
            groupIdTech = newGroupTechId,
            stateId = newStateId,
            printerTypeId = newTypeId,
            printerModelId = newModelId,
            manufacturerId = newManufacturerId,
            memorySize = newMemorySize,
            initPagesCounter = newInitPageCounter,
            lastPagesCounter = newLastPageCounter,
            haveSerial = newHaveSerial,
            haveParallel = newHaveParallel,
            haveUsb = newHaveUsb,
            haveWifi = newHaveWifi,
            haveEthernet = newHaveEthernet,
            comment = newComment
        )

        assert(result.hashCode() == expectedItem.hashCode())
    }

    @Test
    fun test_if_fetchModels_works() {
        runBlocking {
            val result = helper.fetchModels(itemsUseCase = itemsUseCase)

            assert(result.isNotEmpty())
            assert(result.filterIsInstance(PrinterModelApi::class.java).size == result.size)
        }
    }

    @Test
    fun test_if_fetchTypes_works() {
        runBlocking {
            val result = helper.fetchTypes(itemsUseCase = itemsUseCase)

            assert(result.isNotEmpty())
            assert(result.filterIsInstance(PrinterTypeApi::class.java).size == result.size)
        }
    }

    @Test
    fun test_if_getEmptyItem_works() {
        assert(helper.getEmptyItem() == ItemViewModelEmptiesItems.emptyPrinterApi)
    }

    @Test
    fun test_if_fetchDevice_works() {
        runBlocking {
            val result = helper.fetchDevice(itemsUseCase = itemsUseCase, deviceId = 1)

            assert(result is PrinterApi)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_fetchPossibleStates_works() {
        runBlocking {
            val result = helper.fetchPossiblesStates(itemsUseCase = itemsUseCase)

            assert(result.map { it.id }.sorted() == listOf(1, 2, 3, 4))
        }
    }
}