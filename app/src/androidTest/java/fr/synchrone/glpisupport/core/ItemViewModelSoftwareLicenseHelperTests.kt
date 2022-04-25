package fr.synchrone.glpisupport.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.model.remote.items.SoftwareLicenseTypeApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.SoftwareLicenseApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelInfos
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelSoftwareLicenseHelper
import fr.synchrone.glpisupport.resources.BaseItemsContent
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ItemViewModelSoftwareLicenseHelperTests {

    private enum class FieldsNames(val displayableName: String) {
        NAME(displayableName = "Nom"),
        SOFTWARE_LICENSE(displayableName = "Licence parente"),
        LOCATION(displayableName = "Lieu"),
        USER(displayableName = "Utilisateur"),
        STATE(displayableName = "Statut"),
        USER_TECH(displayableName = "Technicien chargé de la licence"),
        GROUP_TECH(displayableName = "Groupe chargé de la licence"),
        EDITOR(displayableName = "Éditeur"),
        NUMBER(displayableName = "Nombre"),
        GROUP(displayableName = "Group"),
        TYPE(displayableName = "Type"),
        ALLOW_OVER_QUOTA(displayableName = "Autoriser le dépassement de quota"),
        EXPIRE(displayableName = "Expiration"),
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

    private val helper = ItemViewModelSoftwareLicenseHelper(infos = ItemViewModelInfos(isCreation = false))

    private val itemToTest = BaseItemsContent.softwareLicenseApi

    @Test
    fun test_if_getFieldsForDevice_works() {
        val result = helper.getFieldsForDevice(context = context, item = itemToTest)

        assert(result.first { it.label == FieldsNames.NAME.displayableName }.initialValue == "computer test")
        assert(result.first { it.label == FieldsNames.LOCATION.displayableName }.initialValue == 3.toString())
        assert(result.first { it.label == FieldsNames.USER.displayableName }.initialValue == 4.toString())
        assert(result.first { it.label == FieldsNames.USER_TECH.displayableName }.initialValue == 5.toString())
        assert(result.first { it.label == FieldsNames.STATE.displayableName }.initialValue == 6.toString())
        assert(result.first { it.label == FieldsNames.TYPE.displayableName }.initialValue == 7.toString())
        assert(result.first { it.label == FieldsNames.EDITOR.displayableName }.initialValue == 9.toString())
        assert(result.first { it.label == FieldsNames.GROUP.displayableName }.initialValue == 10.toString())
        assert(result.first { it.label == FieldsNames.GROUP_TECH.displayableName }.initialValue == 11.toString())
        assert(result.first { it.label == FieldsNames.NUMBER.displayableName }.initialValue == 12.toString())
        assert(result.first { it.label == FieldsNames.ALLOW_OVER_QUOTA.displayableName }.initialValue == true.toString())
        assert(result.first { it.label == FieldsNames.EXPIRE.displayableName }.initialValue == "")
        assert(result.first { it.label == FieldsNames.SOFTWARE_LICENSE.displayableName }.initialValue == 14.toString())
        assert(result.first { it.label == FieldsNames.COMMENT.displayableName }.initialValue == "comment test")
    }

    @Test
    fun test_if_getUpdatedDeviceWithField() {
        val newName = "new name"
        val newLocationId = 100
        val newUserId = 101
        val newUserTechId = 102
        val newStateId = 103
        val newTypeId = 104
        val newManufacturerId = 106
        val newGroupId = 107
        val newGroupTechId = 108
        val newNumber = 109
        val newAllowOverquota = 0
        val newExpire = "2021-11-12"
        val newSoftwareLicenseId = 111
        val newComment = "new comment"

        val result = helper.getUpdatedDeviceWithFields(
            context = context,
            originalItem = itemToTest,
            fields = mapOf(
                FieldsNames.NAME.displayableName to newName,
                FieldsNames.LOCATION.displayableName to newLocationId.toString(),
                FieldsNames.USER.displayableName to newUserId.toString(),
                FieldsNames.USER_TECH.displayableName to newUserTechId.toString(),
                FieldsNames.STATE.displayableName to newStateId.toString(),
                FieldsNames.TYPE.displayableName to newTypeId.toString(),
                FieldsNames.EDITOR.displayableName to newManufacturerId.toString(),
                FieldsNames.GROUP.displayableName to newGroupId.toString(),
                FieldsNames.GROUP_TECH.displayableName to newGroupTechId.toString(),
                FieldsNames.NUMBER.displayableName to newNumber.toString(),
                FieldsNames.ALLOW_OVER_QUOTA.displayableName to newAllowOverquota.toString(),
                FieldsNames.EXPIRE.displayableName to newExpire,
                FieldsNames.SOFTWARE_LICENSE.displayableName to newSoftwareLicenseId.toString(),
                FieldsNames.COMMENT.displayableName to newComment,
            )
        )

        val expectedItem = itemToTest.copy(
            name = newName,
            locationId = newLocationId,
            userId = newUserId,
            userTechId = newUserTechId,
            stateId = newStateId,
            softwareLicenceTypeId = newTypeId,
            manufacturerId = newManufacturerId,
            groupId = newGroupId,
            groupTechId = newGroupTechId,
            number = newNumber,
            _allow_overquota = newAllowOverquota,
            expire = newExpire,
            softwareLicenseId = newSoftwareLicenseId,
            comment = newComment
        )

        assert(result.hashCode() == expectedItem.hashCode())
    }

    @Test
    fun test_if_fetchModels_works() {
        runBlocking {
            val result = helper.fetchModels(itemsUseCase = itemsUseCase)

            assert(result.isEmpty())
        }
    }

    @Test
    fun test_if_fetchTypes_works() {
        runBlocking {
            val result = helper.fetchTypes(itemsUseCase = itemsUseCase)

            assert(result.isNotEmpty())
            assert(result.filterIsInstance(SoftwareLicenseTypeApi::class.java).size == result.size)
        }
    }

    @Test
    fun test_if_getEmptyItem_works() {
        assert(helper.getEmptyItem() == ItemViewModelEmptiesItems.emptySoftwareLicenseApi)
    }

    @Test
    fun test_if_fetchDevice_works() {
        runBlocking {
            val result = helper.fetchDevice(itemsUseCase = itemsUseCase, deviceId = 1)

            assert(result is SoftwareLicenseApi)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_fetchPossibleStates_works() {
        runBlocking {
            val result = helper.fetchPossiblesStates(itemsUseCase = itemsUseCase)

            assert(result.map { it.id }.sorted() == listOf(1, 3, 4))
        }
    }
}