package fr.synchrone.glpisupport.domain

import androidx.lifecycle.LiveData
import fr.synchrone.glpisupport.data.model.local.devices.DeviceHistoryDb
import fr.synchrone.glpisupport.data.model.remote.items.*
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import fr.synchrone.glpisupport.data.model.remote.items.ProfileApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.data.repository.ItemsRepository
import javax.inject.Inject

class ItemsUseCase @Inject constructor(private val itemsRepository: ItemsRepository) {

    suspend fun getComputerById(id: Int): ComputerApi = itemsRepository.getComputerById(id)

    suspend fun getPhoneById(id: Int): PhoneApi = itemsRepository.getPhoneById(id)

    suspend fun getSoftwareById(id: Int): SoftwareApi = itemsRepository.getSoftwareById(id = id)

    suspend fun getMonitorById(id: Int): MonitorApi = itemsRepository.getMonitorById(id = id)

    suspend fun getPrinterById(id: Int): PrinterApi = itemsRepository.getPrinterById(id = id)

    suspend fun getNetworkEquipmentById(id: Int): NetworkEquipmentApi = itemsRepository.getNetworkEquipmentById(id = id)

    suspend fun getSoftwareLicenceApiById(id: Int): SoftwareLicenseApi = itemsRepository.getSoftwareLicenceById(id = id)

    suspend fun getRackById(id: Int): RackApi = itemsRepository.getRackById(id = id)

    suspend fun getLocationById(id: Int): LocationApi = itemsRepository.getLocationById(id)

    suspend fun getComputersTemplates(): List<ComputerApi> = itemsRepository.getComputersTemplates()

    suspend fun getPhonesTemplates(): List<PhoneApi> = itemsRepository.getPhonesTemplates()

    suspend fun getMonitorsTemplates() = itemsRepository.getMonitorsTemplates()

    suspend fun getPrintersTemplates() = itemsRepository.getPrintersTemplates()

    suspend fun getSoftwaresTemplates() = itemsRepository.getSoftwaresTemplates()

    suspend fun getNetworkEquipmentsTemplates() = itemsRepository.getNetworkEquipmentsTemplates()

    suspend fun getSoftwaresLicensesTemplates() = itemsRepository.getSoftwaresLicensesTemplates()

    suspend fun getAllLocations(): List<LocationApi> = itemsRepository.getAllLocations()

    suspend fun getAllSuppliers(): List<SupplierApi> = itemsRepository.getAllSuppliers()

    suspend fun getAllEntities(): List<EntityApi> = itemsRepository.getAllEntities()

    suspend fun getAllProfiles(): List<ProfileApi> = itemsRepository.getAllProfiles()

    suspend fun getAllUsers(): List<UserApi> = itemsRepository.getAllUsers()

    suspend fun getAllGroups(): List<GroupApi> = itemsRepository.getAllGroups()

    suspend fun getAllSoftwareCategories(): List<SoftwareCategoryApi> = itemsRepository.getAllSoftwareCategories()

    suspend fun getProfilesUserByProfileIdAndEntityName(entityName: String, profileName: String): List<ProfileUserApi> = itemsRepository.getProfilesUserByProfileIdAndEntityName(entityName = entityName, profileName = profileName)

    suspend fun getPossiblesStatesToComputers(): List<StateApi> = itemsRepository.getAllStates().filter { it.isVisibleComputer }
    suspend fun getPossiblesStatesToPhones(): List<StateApi> = itemsRepository.getAllStates().filter { it.isVisiblePhone }
    suspend fun getPossiblesStatesToMonitors(): List<StateApi> = itemsRepository.getAllStates().filter { it.isVisibleMonitor }
    suspend fun getPossiblesStatesToPrinters(): List<StateApi> = itemsRepository.getAllStates().filter { it.isVisiblePrinter }
    suspend fun getPossiblesStatesToSoftwareLicenses(): List<StateApi> = itemsRepository.getAllStates().filter { it.isVisibleSoftwareLicense }
    suspend fun getPossiblesStatesToRacks(): List<StateApi> = itemsRepository.getAllStates().filter { it.isVisibleRack }
    suspend fun getPossiblesStatesToNetworkEquipments(): List<StateApi> = itemsRepository.getAllStates().filter { it.isVisibleNetworkEquipment }

    suspend fun getAllComputersTypes(): List<ComputerTypeApi> = itemsRepository.getAllComputersTypes()

    suspend fun getAllPhonesTypes(): List<PhoneTypeApi> = itemsRepository.getAllPhonesTypes()

    suspend fun getAllMonitorsTypes(): List<MonitorTypeApi> = itemsRepository.getAllMonitorsTypes()

    suspend fun getAllPrintersTypes(): List<PrinterTypeApi> = itemsRepository.getAllPrintersTypes()

    suspend fun getAllNetworkEquipmentsTypes(): List<NetworkEquipmentTypeApi> = itemsRepository.getAllNetworkEquipmentTypes()

    suspend fun getAllRacksTypes(): List<RackTypeApi> = itemsRepository.getAllRacksTypes()

    suspend fun getAllSoftwareLicensesTypesApi(): List<SoftwareLicenseTypeApi> = itemsRepository.getAllSoftwareLicensesTypesApi()

    suspend fun getAllManufacturers(): List<ManufacturerApi> = itemsRepository.getAllManufacturers()

    suspend fun getAllComputersModels(): List<ComputerModelApi> = itemsRepository.getAllComputersModels()

    suspend fun getAllPhonesModels(): List<PhoneModelApi> = itemsRepository.getAllPhonesModels()

    suspend fun getAllNetworkEquipmentsModels(): List<NetworkEquipmentModelApi> = itemsRepository.getAllNetworkEquipmentModels()

    suspend fun getAllMonitorsModels(): List<MonitorModelApi> = itemsRepository.getAllMonitorsModels()

    suspend fun getAllPrintersModels(): List<PrinterModelApi> = itemsRepository.getAllPrintersModels()

    suspend fun getAllRacksModels(): List<RackModelApi> = itemsRepository.getAllRacksModels()

    suspend fun getAllSoftwares() = itemsRepository.getAllSoftwares()

    suspend fun getAllSoftwaresLicenses() = itemsRepository.getAllSoftwaresLicenses()

    suspend fun getAllBusinessCriticities() = itemsRepository.getAllBusinessCriticities()

    suspend fun getAllBudgets() = itemsRepository.getAllBudgets()

    suspend fun getAllDCRooms() = itemsRepository.getAllDCRooms()

    fun getAllDevicesHistories(): LiveData<List<DeviceHistoryDb>> = itemsRepository.getAllDevicesHistories()

    suspend fun insertDeviceHistory(deviceHistory: DeviceHistoryDb) = itemsRepository.insertDeviceHistory(deviceHistory = deviceHistory)

    suspend fun updateItem(item: ItemApi) = itemsRepository.updateItem(item = item)

    suspend fun addItem(item: ItemApi) = itemsRepository.addItem(item = item)
}