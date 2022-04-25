package fr.synchrone.glpisupport.core

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.repository.ItemsRepository
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@Suppress("IllegalIdentifier")
@HiltAndroidTest
class ItemsRepositoryTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var itemsRepository: ItemsRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_if_getComputerById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getComputerById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getComputerById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getComputerById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getPhoneById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getPhoneById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getPhoneById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getPhoneById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getSoftwareById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getSoftwareById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getSoftwareById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getSoftwareById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getMonitorById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getMonitorById(id = 4)
            assert(result.id == 4)
        }
    }

    @Test
    fun test_if_getMonitorById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getMonitorById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getPrinterById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getPrinterById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getPrinterById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getPrinterById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getNetworkEquipmentById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getNetworkEquipmentById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getNetworkEquipmentById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getNetworkEquipmentById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getSoftwareLicenceById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getSoftwareLicenceById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getSoftwareLicenceById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getSoftwareLicenceById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getRackById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getRackById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getRackById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getRackById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getLocationById_works_with_good_id() {
        runBlocking {
            val result = itemsRepository.getLocationById(id = 1)
            assert(result.id == 1)
        }
    }

    @Test
    fun test_if_getLocationById_not_work_with_wrong_id() {
        runBlocking {
            try {
                itemsRepository.getLocationById(id = 999)
                assert(false)
            } catch (e: Exception) {
                assert(true)
            }
        }
    }

    @Test
    fun test_if_getAllLocations_works() {
        runBlocking {
            val result = itemsRepository.getAllLocations()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSuppliers_works() {
        runBlocking {
            val result = itemsRepository.getAllSuppliers()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllEntities_works() {
        runBlocking {
            val result = itemsRepository.getAllEntities()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllProfiles_works() {
        runBlocking {
            val result = itemsRepository.getAllProfiles()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllUsers_works() {
        runBlocking {
            val result = itemsRepository.getAllUsers()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllStates_works() {
        runBlocking {
            val result = itemsRepository.getAllStates()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllComputersTypes_works() {
        runBlocking {
            val result = itemsRepository.getAllComputersTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllPhonesTypes_works() {
        runBlocking {
            val result = itemsRepository.getAllPhonesTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllMonitorsTypes_works() {
        runBlocking {
            val result = itemsRepository.getAllMonitorsTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllPrintersTypes_works() {
        runBlocking {
            val result = itemsRepository.getAllPrintersTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllNetworkEquipmentTypes_works() {
        runBlocking {
            val result = itemsRepository.getAllNetworkEquipmentTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllRacksTypes_works() {
        runBlocking {
            val result = itemsRepository.getAllRacksTypes()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSoftwareLicensesType_works() {
        runBlocking {
            val result = itemsRepository.getAllSoftwareLicensesTypesApi()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllManufacturers_works() {
        runBlocking {
            val result = itemsRepository.getAllManufacturers()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllComputersModels_works() {
        runBlocking {
            val result = itemsRepository.getAllComputersModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllPhonesModels_works() {
        runBlocking {
            val result = itemsRepository.getAllPhonesModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllMonitorsModels_works() {
        runBlocking {
            val result = itemsRepository.getAllMonitorsModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllNetworkEquipmentModels_works() {
        runBlocking {
            val result = itemsRepository.getAllNetworkEquipmentModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllPrintersModels_works() {
        runBlocking {
            val result = itemsRepository.getAllPrintersModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllRacksModels_works() {
        runBlocking {
            val result = itemsRepository.getAllRacksModels()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllGroups_works() {
        runBlocking {
            val result = itemsRepository.getAllGroups()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSoftwareCategories_works() {
        runBlocking {
            val result = itemsRepository.getAllSoftwareCategories()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSoftwares_works() {
        runBlocking {
            val result = itemsRepository.getAllSoftwares()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllSoftwaresLicenses_works() {
        runBlocking {
            val result = itemsRepository.getAllSoftwaresLicenses()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllBusinessCriticities_works() {
        runBlocking {
            val result = itemsRepository.getAllBusinessCriticities()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_if_getAllBudgets_works() {
        runBlocking {
            val result = itemsRepository.getAllBudgets()
            assert(result.isNotEmpty())
        }
    }

    @Test
    fun test_addItem_with_ComputerApi() {
        runBlocking {
            val itemApi = ItemViewModelEmptiesItems
                .emptyComputerApi
                .copy(name = "computer test")
            val result = itemsRepository.addItem(item = itemApi)
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
            itemsRepository.updateItem(item = itemApi)
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
            itemsRepository.updateItem(item = itemApi)
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
            itemsRepository.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_updateItem_with_MonitorApi() {
        runBlocking {
            val itemApi = (ItemViewModelEmptiesItems.emptyMonitorApi)
                .copy(
                    id = 1,
                    name = "monitor edited"
                )
            itemsRepository.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_updateItem_with_PrinterApi() {
        runBlocking {
            val itemApi = (ItemViewModelEmptiesItems.emptyPrinterApi)
                .copy(
                    id = 1,
                    name = "printer edited"
                )
            itemsRepository.updateItem(item = itemApi)
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
            itemsRepository.updateItem(item = itemApi)
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
            itemsRepository.updateItem(item = itemApi)
            assert(true)
        }
    }

    @Test
    fun test_getProfilesUserByProfileIdAndEntityName_with_correct_names() {
        runBlocking {
            val result = itemsRepository.getProfilesUserByProfileIdAndEntityName(
                entityName = "Support",
                profileName = "Super"
            )

            assert(result.isEmpty()) //todo
        }
    }

    @Test
    fun test_getComputersTemplates() {
        runBlocking {
            val result = itemsRepository.getComputersTemplates()

            assert(result.size == 0)
            assert(result.map { it.id }.sorted() == listOf<Int>())
        }
    }

    @Test
    fun test_getPhonesTemplates() {
        runBlocking {
            val result = itemsRepository.getPhonesTemplates()

            assert(result.size == 0)
            assert(result.map { it.id }.sorted() == listOf<Int>())
        }
    }

}