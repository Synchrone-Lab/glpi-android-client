package fr.synchrone.glpisupport.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.model.remote.items.MonitorModelApi
import fr.synchrone.glpisupport.data.model.remote.items.MonitorTypeApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.MonitorApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelInfos
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelMonitorHelper
import fr.synchrone.glpisupport.resources.BaseItemsContent
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ItemViewModelMonitorHelperTests {

    private enum class FieldsNames(val displayableName: String) {
        NAME(displayableName = "Nom"),
        SERIAL_NUMBER(displayableName = "Numéro de série"),
        LOCATION(displayableName = "Lieu"),
        USER(displayableName = "Utilisateur"),
        USER_TECH(displayableName = "Responsable technique"),
        STATE(displayableName = "Statut"),
        MANUFACTURER(displayableName = "Fabricant"),
        TYPE(displayableName = "Type"),
        MODEL(displayableName = "Modèle"),
        MICRO(displayableName = "Microphone"),
        SPEAKERS(displayableName = "Enceintes"),
        SUBD(displayableName = "Sub-D"),
        DVI(displayableName = "DVI"),
        HDMI(displayableName = "HDMI"),
        BNC(displayableName = "BNC"),
        PIVOT(displayableName = "PIVOT"),
        DISPLAY_PORT(displayableName = "DisplayPort"),
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

    private val helper = ItemViewModelMonitorHelper(infos = ItemViewModelInfos(isCreation = false))

    private val itemToTest = BaseItemsContent.monitorApi

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
        assert(result.first { it.label == FieldsNames.MICRO.displayableName }.initialValue == true.toString())
        assert(result.first { it.label == FieldsNames.SPEAKERS.displayableName }.initialValue == true.toString())
        assert(result.first { it.label == FieldsNames.SUBD.displayableName }.initialValue == true.toString())
        assert(result.first { it.label == FieldsNames.DVI.displayableName }.initialValue == true.toString())
        assert(result.first { it.label == FieldsNames.HDMI.displayableName }.initialValue == true.toString())
        assert(result.first { it.label == FieldsNames.BNC.displayableName }.initialValue == true.toString())
        assert(result.first { it.label == FieldsNames.PIVOT.displayableName }.initialValue == true.toString())
        assert(result.first { it.label == FieldsNames.DISPLAY_PORT.displayableName }.initialValue == true.toString())
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
        val newHaveMicro = false
        val newHaveSpeaker = false
        val newHaveSubd = false
        val newHaveDvi = false
        val newHaveHdmi = false
        val newHaveBnc = false
        val newHavePivot = false
        val newHaveDisplayport = false
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
                FieldsNames.MICRO.displayableName to newHaveMicro.toString(),
                FieldsNames.SPEAKERS.displayableName to newHaveSpeaker.toString(),
                FieldsNames.SUBD.displayableName to newHaveSubd.toString(),
                FieldsNames.DVI.displayableName to newHaveDvi.toString(),
                FieldsNames.HDMI.displayableName to newHaveHdmi.toString(),
                FieldsNames.BNC.displayableName to newHaveBnc.toString(),
                FieldsNames.PIVOT.displayableName to newHavePivot.toString(),
                FieldsNames.DISPLAY_PORT.displayableName to newHaveDisplayport.toString(),
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
            monitorTypeId = newTypeId,
            monitorModelId = newModelId,
            manufacturerId = newManufacturerId,
            _have_micro = 0,
            _have_speaker = 0,
            _have_subd = 0,
            _have_dvi = 0,
            _have_hdmi = 0,
            _have_bnc = 0,
            _have_pivot = 0,
            _have_displayport = 0,
            comment = newComment
        )

        assert(result.hashCode() == expectedItem.hashCode())
    }

    @Test
    fun test_if_fetchModels_works() {
        runBlocking {
            val result = helper.fetchModels(itemsUseCase = itemsUseCase)

            assert(result.isNotEmpty())
            assert(result.filterIsInstance(MonitorModelApi::class.java).size == result.size)
        }
    }

    @Test
    fun test_if_fetchTypes_works() {
        runBlocking {
            val result = helper.fetchTypes(itemsUseCase = itemsUseCase)

            assert(result.isNotEmpty())
            assert(result.filterIsInstance(MonitorTypeApi::class.java).size == result.size)
        }
    }

    @Test
    fun test_if_getEmptyItem_works() {
        assert(helper.getEmptyItem() == ItemViewModelEmptiesItems.emptyMonitorApi)
    }

    @Test
    fun test_if_fetchDevice_works() {
        runBlocking {
            val result = helper.fetchDevice(itemsUseCase = itemsUseCase, deviceId = 1)

            assert(result is MonitorApi)
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