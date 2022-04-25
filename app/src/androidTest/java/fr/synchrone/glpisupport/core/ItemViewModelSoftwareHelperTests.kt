package fr.synchrone.glpisupport.core

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.model.remote.items.devices.SoftwareApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelInfos
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelSoftwareHelper
import fr.synchrone.glpisupport.resources.BaseItemsContent
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ItemViewModelSoftwareHelperTests {

    private enum class FieldsNames(val displayableName: String) {
        NAME(displayableName = "Nom"),
        LOCATION(displayableName = "Lieu"),
        USER(displayableName = "Utilisateur"),
        SOFTWARE_USER_TECH(displayableName = "Technicien chargé du logiciel"),
        SOFTWARE_GROUP_TECH(displayableName = "Groupe chargé du logiciel"),
        EDITOR(displayableName = "Éditeur"),
        CATEGORY(displayableName = "Catégorie"),
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

    private val helper = ItemViewModelSoftwareHelper(infos = ItemViewModelInfos(isCreation = false))

    private val itemToTest = BaseItemsContent.softwareApi

    @Test
    fun test_if_getFieldsForDevice_works() {
        val result = helper.getFieldsForDevice(context = context, item = itemToTest)

        assert(result.first { it.label == FieldsNames.NAME.displayableName }.initialValue == "computer test")
        assert(result.first { it.label == FieldsNames.LOCATION.displayableName }.initialValue == 3.toString())
        assert(result.first { it.label == FieldsNames.USER.displayableName }.initialValue == 4.toString())
        assert(result.first { it.label == FieldsNames.SOFTWARE_USER_TECH.displayableName }.initialValue == 5.toString())
        assert(result.first { it.label == FieldsNames.CATEGORY.displayableName }.initialValue == 7.toString())
        assert(result.first { it.label == FieldsNames.EDITOR.displayableName }.initialValue == 9.toString())
        assert(result.first { it.label == FieldsNames.SOFTWARE_GROUP_TECH.displayableName }.initialValue == 10.toString())
        assert(result.first { it.label == FieldsNames.COMMENT.displayableName }.initialValue == "comment test")
    }

    @Test
    fun test_if_getUpdatedDeviceWithField() {
        val newName = "new name"
        val newLocationId = 100
        val newUserId = 101
        val newUserTechId = 102
        val newGroupTechId = 107
        val newEditorId = 108
        val newCategoryId = 109
        val newComment = "new comment"

        val result = helper.getUpdatedDeviceWithFields(
            context = context,
            originalItem = itemToTest,
            fields = mapOf(
                FieldsNames.NAME.displayableName to newName,
                FieldsNames.LOCATION.displayableName to newLocationId.toString(),
                FieldsNames.USER.displayableName to newUserId.toString(),
                FieldsNames.SOFTWARE_USER_TECH.displayableName to newUserTechId.toString(),
                FieldsNames.EDITOR.displayableName to newEditorId.toString(),
                FieldsNames.SOFTWARE_GROUP_TECH.displayableName to newGroupTechId.toString(),
                FieldsNames.CATEGORY.displayableName to newCategoryId.toString(),
                FieldsNames.COMMENT.displayableName to newComment,
            )
        )

        val expectedItem = itemToTest.copy(
            name = newName,
            locationId = newLocationId,
            userId = newUserId,
            userTechId = newUserTechId,
            softwareCategoryId = newCategoryId,
            manufacturerId = newEditorId,
            groupTechId = newGroupTechId,
            comment = newComment,
            _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
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

            assert(result.isEmpty())
        }
    }

    @Test
    fun test_if_getEmptyItem_works() {
        assert(helper.getEmptyItem() == ItemViewModelEmptiesItems.emptySoftwareApi)
    }

    @Test
    fun test_if_fetchDevice_works() {
        runBlocking {
            val result = helper.fetchDevice(itemsUseCase = itemsUseCase, deviceId = 1)

            assert(result is SoftwareApi)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_fetchPossibleStates_works() {
        runBlocking {
            val result = helper.fetchPossiblesStates(itemsUseCase = itemsUseCase)

            assert(result.map { it.id }.sorted().isEmpty())
        }
    }
}