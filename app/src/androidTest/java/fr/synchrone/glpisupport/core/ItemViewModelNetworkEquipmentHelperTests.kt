package fr.synchrone.glpisupport.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.model.remote.items.NetworkEquipmentModelApi
import fr.synchrone.glpisupport.data.model.remote.items.NetworkEquipmentTypeApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.NetworkEquipmentApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelInfos
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelNetworkEquipmentHelper
import fr.synchrone.glpisupport.resources.BaseItemsContent
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ItemViewModelNetworkEquipmentHelperTests {

    private enum class FieldsNames(val displayableName: String) {
        NAME(displayableName = "Nom"),
        SERIAL_NUMBER(displayableName = "Numéro de série"),
        PHONE_NUMBER(displayableName = "Numéro de téléphone"),
        LOCATION(displayableName = "Lieu"),
        MEMORY(displayableName = "Mémoire (Mio)"),
        USER(displayableName = "Utilisateur"),
        USER_TECH(displayableName = "Responsable technique"),
        STATE(displayableName = "Statut"),
        MANUFACTURER(displayableName = "Fabricant"),
        TYPE(displayableName = "Type"),
        MODEL(displayableName = "Modèle"),
        COMMENT(displayableName = "Commentaire")
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

    private val helper = ItemViewModelNetworkEquipmentHelper(infos = ItemViewModelInfos(isCreation = false))

    private val itemToTest = BaseItemsContent.networkEquipmentApi

    @Test
    fun test_if_getFieldsForDevice_works() {
        val result = helper.getFieldsForDevice(context = context, item = itemToTest)

        assert(result.first { it.label == FieldsNames.NAME.displayableName }.initialValue == "computer test")
        assert(result.first { it.label == FieldsNames.SERIAL_NUMBER.displayableName }.initialValue == "123456789")
        assert(result.first { it.label == FieldsNames.LOCATION.displayableName }.initialValue == 3.toString())
        assert(result.first { it.label == FieldsNames.USER.displayableName }.initialValue == 4.toString())
        assert(result.first { it.label == FieldsNames.USER_TECH.displayableName }.initialValue == 5.toString())
        assert(result.first { it.label == FieldsNames.STATE.displayableName }.initialValue == 6.toString())
        assert(result.first { it.label == FieldsNames.TYPE.displayableName }.initialValue == 7.toString())
        assert(result.first { it.label == FieldsNames.MODEL.displayableName }.initialValue == 8.toString())
        assert(result.first { it.label == FieldsNames.MANUFACTURER.displayableName }.initialValue == 9.toString())
        assert(result.first { it.label == FieldsNames.PHONE_NUMBER.displayableName }.initialValue == "0601020304")
        assert(result.first { it.label == FieldsNames.MEMORY.displayableName }.initialValue == "128 mo")
        assert(result.first { it.label == FieldsNames.COMMENT.displayableName }.initialValue == "comment test")
    }

    @Test
    fun test_if_getUpdatedDeviceWithField() {
        val newName = "new name"
        val newSerial = "new serial"
        val newLocationId = 100
        val newUserId = 101
        val newUserTechId = 102
        val newStateId = 103
        val newTypeId = 104
        val newModelId = 105
        val newManufacturerId = 106
        val newPhoneNumber = "new phone number"
        val newRam = "9999 petaoctets"
        val newComment = "new comment"

        val result = helper.getUpdatedDeviceWithFields(
            context = context,
            originalItem = itemToTest,
            fields = mapOf(
                FieldsNames.NAME.displayableName to newName,
                FieldsNames.SERIAL_NUMBER.displayableName to newSerial,
                FieldsNames.LOCATION.displayableName to newLocationId.toString(),
                FieldsNames.USER.displayableName to newUserId.toString(),
                FieldsNames.USER_TECH.displayableName to newUserTechId.toString(),
                FieldsNames.STATE.displayableName to newStateId.toString(),
                FieldsNames.TYPE.displayableName to newTypeId.toString(),
                FieldsNames.MODEL.displayableName to newModelId.toString(),
                FieldsNames.MANUFACTURER.displayableName to newManufacturerId.toString(),
                FieldsNames.PHONE_NUMBER.displayableName to newPhoneNumber,
                FieldsNames.MEMORY.displayableName to newRam,
                FieldsNames.COMMENT.displayableName to newComment,
            )
        )

        val expectedItem = itemToTest.copy(
            name = newName,
            serial = newSerial,
            locationId = newLocationId,
            userId = newUserId,
            userTechId = newUserTechId,
            stateId = newStateId,
            networkEquipmentTypeId = newTypeId,
            networkEquipmentModelId = newModelId,
            manufacturerId = newManufacturerId,
            userNumber = newPhoneNumber,
            ram = newRam,
            comment = newComment
        )

        assert(result.hashCode() == expectedItem.hashCode())
    }

    @Test
    fun test_if_fetchModels_works() {
        runBlocking {
            val result = helper.fetchModels(itemsUseCase = itemsUseCase)

            assert(result.isNotEmpty())
            assert(result.filterIsInstance(NetworkEquipmentModelApi::class.java).size == result.size)
        }
    }

    @Test
    fun test_if_fetchTypes_works() {
        runBlocking {
            val result = helper.fetchTypes(itemsUseCase = itemsUseCase)

            assert(result.isNotEmpty())
            assert(result.filterIsInstance(NetworkEquipmentTypeApi::class.java).size == result.size)
        }
    }

    @Test
    fun test_if_getEmptyItem_works() {
        assert(helper.getEmptyItem() == ItemViewModelEmptiesItems.emptyNetworkEquipmentApi)
    }

    @Test
    fun test_if_fetchDevice_works() {
        runBlocking {
            val result = helper.fetchDevice(itemsUseCase = itemsUseCase, deviceId = 1)

            assert(result is NetworkEquipmentApi)
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