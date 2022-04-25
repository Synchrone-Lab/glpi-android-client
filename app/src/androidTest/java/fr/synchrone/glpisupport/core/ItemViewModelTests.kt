package fr.synchrone.glpisupport.core

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.model.remote.items.ComputerModelApi
import fr.synchrone.glpisupport.data.model.remote.items.ComputerTypeApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.presentation.items.ItemField
import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModel
import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModelFactory
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidTest
class ItemViewModelTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Inject
    lateinit var itemViewModelFactory: ItemViewModelFactory

    val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun test_viewModel_init_with_computer() {
        initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        assert(true)
    }

    @Test
    fun test_viewModel_init_with_phone() {
        initViewModel(
            itemType = PhoneApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        assert(true)
    }

    @Test
    fun test_viewModel_init_with_printer() {
        initViewModel(
            itemType = PrinterApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        assert(true)
    }

    @Test
    fun test_viewModel_init_with_software() {
        initViewModel(
            itemType = SoftwareApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        assert(true)
    }

    @Test
    fun test_viewModel_init_with_software_license() {
        initViewModel(
            itemType = SoftwareLicenseApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        assert(true)
    }

    @Test
    fun test_viewModel_init_with_network_equipment() {
        initViewModel(
            itemType = NetworkEquipmentApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        assert(true)
    }

    @Test
    fun test_viewModel_init_with_monitor() {
        initViewModel(
            itemType = MonitorApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        assert(true)
    }

    @Test
    fun test_getUserNameById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getUserNameById(id = 4)
        assert(result == "tech")
    }

    @Test
    fun test_getUserNameById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getUserNameById(id = 9999)

        assert(result == "----")
    }

    @Test
    fun test_getLocationById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getLocationById(id = 2)

        assert(result.id == 2)
        assert(result.name == "Paris")
    }

    @Test
    fun test_getLocationById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getLocationById(id = 9999)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptyLocationApi.hashCode())
    }

    @Test
    fun test_getGroupById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = SoftwareApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getGroupById(id = 1)

        assert(result.id == 1)
        assert(result.name == "group edited")
    }

    @Test
    fun test_getGroupById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = SoftwareApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getGroupById(id = 3)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptyGroupApi.hashCode())
    }

    @Test
    fun test_getSupplierById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = SoftwareApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getSupplierById(id = 2)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptySupplierApi.hashCode())
    }

    @Test
    fun test_getStateById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getStateById(id = 3)

        assert(result.id == 3)
        assert(result.name == "En stock")
    }

    @Test
    fun test_getStateById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getStateById(id = 5)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptyStateApi.hashCode())
    }

    @Test
    fun test_getTypeById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getTypeById(id = 1)

        assert(result is ComputerTypeApi)
        assert(result.id == 1)
        assert(result.name == "computertype edited")
    }

    @Test
    fun test_getTypeById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getTypeById(id = 3)

        assert(result.name == ItemViewModelEmptiesItems.emptyTypeApi.name
                && result.id == ItemViewModelEmptiesItems.emptyTypeApi.id)
    }

    @Test
    fun test_getManufacturerById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getManufacturerById(id = 1)

        assert(result.id == 1)
        assert(result.name == "manufacturer edited")
    }

    @Test
    fun test_getManufacturerById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getManufacturerById(id = 6)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptyManufacturerApi.hashCode())
    }

    @Test
    fun test_getModelById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getModelById(id = 1)

        assert(result is ComputerModelApi)
        assert(result.id == 1)
        assert(result.name == "computermodel edited")
    }

    @Test
    fun test_getModelById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getModelById(id = 9999)

        assert(result.name == ItemViewModelEmptiesItems.emptyModelApi.name
                && result.id == ItemViewModelEmptiesItems.emptyModelApi.id)
    }

    @Test
    fun test_getSoftwareById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = SoftwareLicenseApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getSoftwareById(id = 1)

        assert(result.id == 1)
        assert(result.name == "software edited")
    }

    @Test
    fun test_getSoftwareById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = SoftwareApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getSoftwareById(id = 3)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptySoftwareApi.hashCode())
    }

    @Test
    fun test_getSoftwareLicenseById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = SoftwareApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getSoftwareLicenseById(id = 1)

        assert(result.id == 1)
        assert(result.name == "softwarelicense edited")
    }

    @Test
    fun test_getSoftwareLicenseById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = SoftwareLicenseApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getSoftwareLicenseById(id = 3)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptySoftwareLicenseApi.hashCode())
    }

    @Test
    fun test_getBusinessCriticityById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getBusinessCriticityById(id = 1)

        assert(result.id == 1)
        assert(result.name == "businesscriticity edited")
    }

    @Test
    fun test_getBusinessCriticityById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getManufacturerById(id = 9999)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptyBusinessCriticityApi.hashCode())
    }

    @Test
    fun test_getBudgetById_with_valid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getBudgetById(id = 1)

        assert(result.id == 1)
        assert(result.name == "budget edited")
    }

    @Test
    fun test_getBudgetById_with_invalid_id() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        val result = viewModel.getBudgetById(id = 2)

        assert(result.hashCode() == ItemViewModelEmptiesItems.emptyBudgetApi.hashCode())
    }

    @Test
    fun test_if_items_are_correctly_grouped_by_entities() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        var isTestOK = false

        try {
            runBlocking {
                viewModel.usersByEntity.observeForever { result ->
                    Log.wtf("test", result.keys.map { it.id }.sorted().toString())
                    isTestOK = result.keys.map { it.id }.sorted() == listOf(0)
                            && result[result.keys.first()]!!.map { it.id }.sorted() == listOf(0, 2, 3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23)
                    this.cancel()
                }
            }
        } catch(e: CancellationException) {
            assert(isTestOK)
        }
    }

    @Test
    fun test_if_items_are_correctly_grouped_by_nothing() {
        val viewModel = initViewModel(
            itemType = ComputerApi.typeName,
            itemId = 1,
            isItemCreation = true, //this avoid database writing on main thread,
            overrideSerial = null
        )

        var isTestOK = false

        try {
            runBlocking {
                viewModel.typesByEntity.observeForever { result ->
                    isTestOK = result.keys.size == 1
                    isTestOK = isTestOK && result.values.first().isNotEmpty()
                    this.cancel()
                }
            }
        } catch(e: CancellationException) {
            assert(isTestOK)
        }
    }

    private fun initViewModel(
        itemType: String,
        itemId: Int,
        isItemCreation: Boolean,
        overrideSerial: String?
    ): ItemViewModel {
        val viewModel = itemViewModelFactory.create(
            itemType = itemType,
            itemId = itemId,
            isItemCreation = isItemCreation,
            overrideSerial = overrideSerial
        )

        var stopJob = false
        val displayedFieldsLiveData = viewModel.displayedFields
        val fetchingError = viewModel.fetchingErrorMessage
        var displayedFields: List<ItemField>
        var isError: Boolean

        while(!stopJob) {

            displayedFields = displayedFieldsLiveData.value ?: listOf()
            isError = fetchingError.value != null

            if (displayedFields.isNotEmpty()) {
                assert(true)
            }
            if(isError) {
                assert(false)
            }

            stopJob = displayedFields.isNotEmpty() || isError
            TimeUnit.MILLISECONDS.sleep(100)
        }

        return viewModel
    }
}