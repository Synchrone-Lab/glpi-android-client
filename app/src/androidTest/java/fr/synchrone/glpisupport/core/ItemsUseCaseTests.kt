package fr.synchrone.glpisupport.core

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@Suppress("IllegalIdentifier")
@HiltAndroidTest
class ItemsUseCaseTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var itemsUseCase: ItemsUseCase

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_if_getComputerById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getComputerById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getComputerById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getComputerById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getPhoneById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getPhoneById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getPhoneById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getPhoneById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getSoftwareById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getSoftwareById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getSoftwareById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getSoftwareById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getMonitorById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getMonitorById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getMonitorById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getMonitorById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getPrinterById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getPrinterById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getPrinterById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getPrinterById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getNetworkEquipmentById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getNetworkEquipmentById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getNetworkEquipmentById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getNetworkEquipmentById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getSoftwareLicenceById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getSoftwareLicenceApiById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getSoftwareLicenceById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getSoftwareLicenceApiById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getRackById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getRackById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getRackById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getRackById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getLocationById_works_with_good_id() {
        runBlocking {
            val result = itemsUseCase.getLocationById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getLocationById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsUseCase.getLocationById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getAllLocations_works() {
        runBlocking {
            val result = itemsUseCase.getAllLocations()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSuppliers_works() {
        runBlocking {
            val result = itemsUseCase.getAllSuppliers()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllEntities_works() {
        runBlocking {
            val result = itemsUseCase.getAllEntities()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllProfiles_works() {
        runBlocking {
            val result = itemsUseCase.getAllProfiles()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllUsers_works() {
        runBlocking {
            val result = itemsUseCase.getAllUsers()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllComputersTypes_works() {
        runBlocking {
            val result = itemsUseCase.getAllComputersTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllPhonesTypes_works() {
        runBlocking {
            val result = itemsUseCase.getAllPhonesTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllMonitorsTypes_works() {
        runBlocking {
            val result = itemsUseCase.getAllMonitorsTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllPrintersTypes_works() {
        runBlocking {
            val result = itemsUseCase.getAllPrintersTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllNetworkEquipmentTypes_works() {
        runBlocking {
            val result = itemsUseCase.getAllNetworkEquipmentsTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllRacksTypes_works() {
        runBlocking {
            val result = itemsUseCase.getAllRacksTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSoftwareLicensesType_works() {
        runBlocking {
            val result = itemsUseCase.getAllSoftwareLicensesTypesApi()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllManufacturers_works() {
        runBlocking {
            val result = itemsUseCase.getAllManufacturers()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllComputersModels_works() {
        runBlocking {
            val result = itemsUseCase.getAllComputersModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllPhonesModels_works() {
        runBlocking {
            val result = itemsUseCase.getAllPhonesModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllMonitorsModels_works() {
        runBlocking {
            val result = itemsUseCase.getAllMonitorsModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllNetworkEquipmentModels_works() {
        runBlocking {
            val result = itemsUseCase.getAllNetworkEquipmentsModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllPrintersModels_works() {
        runBlocking {
            val result = itemsUseCase.getAllPrintersModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllRacksModels_works() {
        runBlocking {
            val result = itemsUseCase.getAllRacksModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllGroups_works() {
        runBlocking {
            val result = itemsUseCase.getAllGroups()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSoftwareCategories_works() {
        runBlocking {
            val result = itemsUseCase.getAllSoftwareCategories()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSoftwares_works() {
        runBlocking {
            val result = itemsUseCase.getAllSoftwares()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSoftwaresLicenses_works() {
        runBlocking {
            val result = itemsUseCase.getAllSoftwaresLicenses()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllBusinessCriticities_works() {
        runBlocking {
            val result = itemsUseCase.getAllBusinessCriticities()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getPossiblesStatesToComputers_works() {
        runBlocking {
            val result = itemsUseCase.getPossiblesStatesToComputers()

            assert(result.map { it.id }.sorted() == listOf(1, 2, 3, 4))
        }
    }

    @Test
    fun test_if_getPossiblesStatesToPhones_works() {
        runBlocking {
            val result = itemsUseCase.getPossiblesStatesToPhones()

            assert(result.map { it.id }.sorted() == listOf(1, 2, 3, 4))
        }
    }

    @Test
    fun test_if_getPossiblesStatesToMonitors_works() {
        runBlocking {
            val result = itemsUseCase.getPossiblesStatesToMonitors()

            assert(result.map { it.id }.sorted() == listOf(1, 2, 3, 4))
        }
    }

    @Test
    fun test_if_getPossiblesStatesToPrinters_works() {
        runBlocking {
            val result = itemsUseCase.getPossiblesStatesToPrinters()

            assert(result.map { it.id }.sorted() == listOf(1, 2, 3, 4))
        }
    }

    @Test
    fun test_if_getPossiblesStatesToSoftwareLicenses_works() {
        runBlocking {
            val result = itemsUseCase.getPossiblesStatesToSoftwareLicenses()

            assert(result.map { it.id }.sorted() == listOf(1, 3, 4))
        }
    }

    @Test
    fun test_if_getPossiblesStatesToRacks_works() {
        runBlocking {
            val result = itemsUseCase.getPossiblesStatesToRacks()

            assert(result.map { it.id }.sorted() == listOf(1, 2, 3, 4))
        }
    }

    @Test
    fun test_if_getPossiblesStatesToNetworkEquipments_works() {
        runBlocking {
            val result = itemsUseCase.getPossiblesStatesToNetworkEquipments()

            assert(result.map { it.id }.sorted() == listOf(1, 2, 3, 4))
        }
    }

    @Test
    fun test_if_getAllBudgets_works() {
        runBlocking {
            val result = itemsUseCase.getAllBudgets()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_addItem_with_ComputerApi() {
        runBlocking {
            val itemApi = ItemViewModelEmptiesItems
                .emptyComputerApi
                .copy(name = "computer added")
            val result = itemsUseCase.addItem(item = itemApi)
            assert(result.id == 4)
        }
    }

    @Test
    fun test_updateItem_with_ComputerApi() {
        runBlocking {
            val itemApi = ItemViewModelEmptiesItems
                .emptyComputerApi
                .copy(
                    id = 1,
                    name = "computer edited"
                )
            itemsUseCase.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_updateItem_with_PhoneApi() {
        runBlocking {
            val itemApi = ItemViewModelEmptiesItems
                .emptyPhoneApi
                .copy(
                    id = 1,
                    name = "phone edited"
                )
            itemsUseCase.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_updateItem_with_SoftwareApi() {
        runBlocking {
            val itemApi = ItemViewModelEmptiesItems
                .emptySoftwareApi
                .copy(
                    id = 1,
                    name = "software edited"
                )
            itemsUseCase.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_updateItem_with_MonitorApi() {
        runBlocking {
            val itemApi = (ItemViewModelEmptiesItems.emptyMonitorApi)
                .copy(
                    id = 4,
                    name = "monitor test"
                )
            itemsUseCase.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_updateItem_with_PrinterApi() {
        runBlocking {
            val itemApi = (ItemViewModelEmptiesItems.emptyPrinterApi)
                .copy(
                    id = 4,
                    name = "printer test"
                )
            itemsUseCase.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_updateItem_with_NetworkEquipmentApi() {
        runBlocking {
            val itemApi = ItemViewModelEmptiesItems
                .emptyNetworkEquipmentApi
                .copy(
                    id = 1,
                    name = "networkequipment edited"
                )
            itemsUseCase.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_updateItem_with_SoftwareLicenseApi() {
        runBlocking {
            val itemApi = ItemViewModelEmptiesItems
                .emptySoftwareLicenseApi
                .copy(
                    id = 1,
                    name = "softwarelicense edited"
                )
            itemsUseCase.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_getProfilesUserByProfileIdAndEntityName_with_correct_names() {
        runBlocking {
            val result = itemsUseCase.getProfilesUserByProfileIdAndEntityName(
                entityName = "Support",
                profileName = "Super"
            )

            assert(result.isEmpty()) //todo
        }
    }

    @Test
    fun test_getComputersTemplates() {
        runBlocking {
            val result = itemsUseCase.getComputersTemplates()

            assert(result.size == 0)
            assert(result.map { it.id }.sorted() == listOf<Int>())
        }
    }

    @Test
    fun test_getPhonesTemplates() {
        runBlocking {
            val result = itemsUseCase.getPhonesTemplates()

            assert(result.size == 0)
            assert(result.map { it.id }.sorted() == listOf<Int>())
        }
    }

}