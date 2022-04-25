package fr.synchrone.glpisupport.data.repository

import androidx.lifecycle.LiveData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import fr.synchrone.glpisupport.data.model.remote.items.*
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import fr.synchrone.glpisupport.data.service.ItemsService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import com.squareup.moshi.Types.newParameterizedType
import fr.synchrone.glpisupport.data.dao.DeviceHistoryDao
import fr.synchrone.glpisupport.data.model.local.devices.DeviceHistoryDb
import fr.synchrone.glpisupport.data.model.remote.items.ProfileApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.data.model.remote.items.responses.PostItemResponseApi
import fr.synchrone.glpisupport.data.service.SearchService
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

class ItemsRepository @Inject constructor(
    private val moshi: Moshi,
    private val itemsService: ItemsService,
    private val searchService: SearchService,
    private val deviceHistoryDao: DeviceHistoryDao
) {
    suspend fun getComputerById(id: Int): ComputerApi = itemsService.getComputerById(id = id)

    suspend fun getPhoneById(id: Int): PhoneApi = itemsService.getPhoneById(id = id)

    suspend fun getSoftwareById(id: Int): SoftwareApi = itemsService.getSoftwareById(id = id)

    suspend fun getMonitorById(id: Int): MonitorApi = itemsService.getMonitorById(id = id)

    suspend fun getPrinterById(id: Int): PrinterApi = itemsService.getPrinterById(id = id)

    suspend fun getNetworkEquipmentById(id: Int): NetworkEquipmentApi = itemsService.getNetworkEquipmentById(id = id)

    suspend fun getSoftwareLicenceById(id: Int): SoftwareLicenseApi = itemsService.getSoftwareLicenceById(id = id)

    suspend fun getRackById(id: Int): RackApi = itemsService.getRackById(id = id)

    suspend fun getLocationById(id: Int): LocationApi = itemsService.getLocationById(id)

    private suspend fun getProfileUserById(id: Int): ProfileUserApi = itemsService.getProfileUserById(id)

    suspend fun getAllLocations(): List<LocationApi> = itemsService.getAllLocations()

    suspend fun getAllSuppliers(): List<SupplierApi> = itemsService.getAllSuppliers()

    suspend fun getAllEntities(): List<EntityApi> = itemsService.getAllEntities()

    suspend fun getAllProfiles(): List<ProfileApi> = itemsService.getAllProfiles()

    suspend fun getProfilesUserByProfileIdAndEntityName(entityName: String, profileName: String): List<ProfileUserApi> {
        val profilesUsersApi: MutableList<ProfileUserApi> = mutableListOf()

        try {
            val searchResults = searchService.searchProfileUserByProfileIdAndEntityName(entityName = entityName, profileName = profileName)

            val searchItems = searchResults.searchItems ?: return listOf()

            searchItems.forEach {
                val profileUserId = it[SearchService.ID_SEARCH_OPTIONS] as Double
                print(profileUserId)
                val profile = getProfileUserById(id = profileUserId.toInt())

                profilesUsersApi.add(profile)
            }

            return profilesUsersApi
        } catch(e: Exception) {
            return profilesUsersApi
        }

    }

    suspend fun getAllUsers(): List<UserApi> = itemsService.getAllUsers()

    suspend fun getAllStates(): List<StateApi> = itemsService.getAllStates()

    suspend fun getAllComputersTypes(): List<ComputerTypeApi> = itemsService.getAllComputersTypes()

    suspend fun getAllPhonesTypes(): List<PhoneTypeApi> = itemsService.getAllPhonesTypes()

    suspend fun getAllMonitorsTypes(): List<MonitorTypeApi> = itemsService.getAllMonitorsTypes()

    suspend fun getAllPrintersTypes(): List<PrinterTypeApi> = itemsService.getAllPrintersTypes()

    suspend fun getAllNetworkEquipmentTypes(): List<NetworkEquipmentTypeApi> =
        itemsService.getAllNetworkEquipmentTypes()

    suspend fun getAllRacksTypes(): List<RackTypeApi> = itemsService.getAllRacksTypes()

    suspend fun getAllSoftwareLicensesTypesApi(): List<SoftwareLicenseTypeApi> = itemsService.getAllSoftwareLicensesTypes()

    suspend fun getAllManufacturers(): List<ManufacturerApi> = itemsService.getAllManufacturers()

    suspend fun getAllComputersModels(): List<ComputerModelApi> = itemsService.getAllComputersModels()

    suspend fun getAllPhonesModels(): List<PhoneModelApi> = itemsService.getAllPhonesModels()

    suspend fun getAllMonitorsModels(): List<MonitorModelApi> = itemsService.getAllMonitorsModels()

    suspend fun getAllNetworkEquipmentModels(): List<NetworkEquipmentModelApi> =
        itemsService.getAllNetworkEquipmentModels()

    suspend fun getAllPrintersModels(): List<PrinterModelApi> = itemsService.getAllPrintersModels()

    suspend fun getAllRacksModels(): List<RackModelApi> = itemsService.getAllRacksModels()

    suspend fun getAllGroups() = itemsService.getAllGroups()

    suspend fun getAllSoftwareCategories() = itemsService.getAllSoftwareCategories()

    suspend fun getAllSoftwares() = itemsService.getAllSoftwares()

    suspend fun getAllSoftwaresLicenses() = itemsService.getAllSoftwaresLicenses()

    suspend fun getAllBusinessCriticities() = itemsService.getAllBusinessCriticities()

    suspend fun getAllBudgets() = itemsService.getAllBudgets()

    suspend fun getAllDCRooms() = itemsService.getAllDCRooms()

    suspend fun getComputersTemplates() = itemsService.getComputersTemplates().filter { it.isTemplate && it.name.trim() !== "" }

    suspend fun getPhonesTemplates() = itemsService.getPhonesTemplates().filter { it.isTemplate && it.name.trim() !== "" }

    suspend fun getMonitorsTemplates() = itemsService.getMonitorsTemplates().filter { it.isTemplate && it.name.trim() !== "" }

    suspend fun getPrintersTemplates() = itemsService.getPrintersTemplates().filter { it.isTemplate && it.name.trim() !== "" }

    suspend fun getSoftwaresTemplates() = itemsService.getSoftwaresTemplates().filter { it.isTemplate && it.name.trim() !== "" }

    suspend fun getNetworkEquipmentsTemplates() = itemsService.getNetworkEquipmentsTemplates().filter { it.isTemplate && it.name.trim() !== "" }

    suspend fun getSoftwaresLicensesTemplates() = itemsService.getSoftwaresLicensesTemplates().filter { it.isTemplate && it.name.trim() !== "" }

    fun getAllDevicesHistories(): LiveData<List<DeviceHistoryDb>> = deviceHistoryDao.getAllDevicesHistories()

    suspend fun deleteAllDevicesHistoriesFromDb() = deviceHistoryDao.deleteAll()

    suspend fun insertDeviceHistory(deviceHistory: DeviceHistoryDb) = deviceHistoryDao.insertDeviceHistory(deviceHistory = deviceHistory)

    suspend fun <T: ItemApi> addItem(item: T): PostItemResponseApi {
        val type = getParameterizedTypeForItem(item = item)
        val jsonAdapter: JsonAdapter<List<T>> = moshi.adapter(type)

        val jsonObject = JSONObject()
        val itemsJsonArrayString = jsonAdapter.toJson(listOf(item))
        val itemsJsonArray = JSONArray(itemsJsonArrayString)
        itemsJsonArray.getJSONObject(0).remove("id")
        itemsJsonArray.getJSONObject(0).remove("_infocoms")
        jsonObject.put("input", itemsJsonArray)

        val postResult =  sendPostItemRequest(item = item, body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()))

        if (item is DeviceApi && item.infoComs != null) {
            val addedItem = when(item) {
                is ComputerApi -> getComputerById(postResult.id)
                is PhoneApi -> getPhoneById(postResult.id)
                is SoftwareApi -> getSoftwareById(postResult.id)
                is MonitorApi -> getMonitorById(postResult.id)
                is PrinterApi -> getPrinterById(postResult.id)
                is NetworkEquipmentApi -> getNetworkEquipmentById(postResult.id)
                is SoftwareLicenseApi -> getSoftwareLicenceById(postResult.id)
                else -> throw (Exception("Unknown type."))
            }
            val addedItemInfoComs = addedItem.infoComs
            if (addedItemInfoComs != null) {
                val newInfoComs = item.infoComs!!.copy(id = addedItemInfoComs.id)
                updateItem(item = newInfoComs)
            }
        }

        return postResult
    }

    suspend fun <T: ItemApi> updateItem(item: T) {
        val type = getParameterizedTypeForItem(item = item)
        var jsonAdapter: JsonAdapter<List<T>> = moshi.adapter(type)
        jsonAdapter = jsonAdapter.serializeNulls()

        val jsonObject = JSONObject()
        val itemsJsonArrayString = jsonAdapter.toJson(listOf(item))
        val itemsJsonArray = JSONArray(itemsJsonArrayString)
        itemsJsonArray.getJSONObject(0).remove("_infocoms")
        jsonObject.put("input", itemsJsonArray)

        sendPatchItemRequest(item = item, body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()))
        if (item is DeviceApi && item.infoComs != null) {
            updateItem(item = item.infoComs!!)
        }
    }

    private suspend fun sendPatchItemRequest(item: ItemApi, body: RequestBody) {
        when(item) {
            is ComputerApi -> itemsService.updateComputer(body = body)
            is PhoneApi -> itemsService.updatePhone(body = body)
            is SoftwareApi -> itemsService.updateSoftware(body = body)
            is MonitorApi -> itemsService.updateMonitor(body = body)
            is InfoComsApi -> itemsService.updateInfoCom(body = body)
            is PrinterApi -> itemsService.updatePrinter(body = body)
            is NetworkEquipmentApi -> itemsService.updateNetworkEquipment(body = body)
            is SoftwareLicenseApi -> itemsService.updateSoftwareLicense(body = body)
            is RackApi -> itemsService.updateRack(body = body)
            else -> throw (Exception("Unknown type to edit."))
        }
    }

    private suspend fun sendPostItemRequest(item: ItemApi, body: RequestBody): PostItemResponseApi {
        val response = when(item) {
            is ComputerApi -> itemsService.addComputer(body = body)
            is PhoneApi -> itemsService.addPhone(body = body)
            is SoftwareApi -> itemsService.addSoftware(body = body)
            is MonitorApi -> itemsService.addMonitor(body = body)
            is PrinterApi -> itemsService.addPrinter(body = body)
            is NetworkEquipmentApi -> itemsService.addNetworkEquipment(body = body)
            is SoftwareLicenseApi -> itemsService.addSoftwareLicense(body = body)
            is RackApi -> itemsService.addRack(body = body)
            else -> throw (Exception("Unknown type to add."))
        }

        return response.firstOrNull() ?: throw Exception("Unable to parse post item response")
    }

    private fun getParameterizedTypeForItem(item: ItemApi): ParameterizedType {
        return when(item) {
            is ComputerApi -> newParameterizedType(List::class.java, ComputerApi::class.java)
            is PhoneApi -> newParameterizedType(List::class.java, PhoneApi::class.java)
            is SoftwareApi -> newParameterizedType(List::class.java, SoftwareApi::class.java)
            is MonitorApi -> newParameterizedType(List::class.java, MonitorApi::class.java)
            is PrinterApi -> newParameterizedType(List::class.java, PrinterApi::class.java)
            is NetworkEquipmentApi -> newParameterizedType(List::class.java, NetworkEquipmentApi::class.java)
            is SoftwareLicenseApi -> newParameterizedType(List::class.java, SoftwareLicenseApi::class.java)
            is RackApi -> newParameterizedType(List::class.java, RackApi::class.java)
            is InfoComsApi -> newParameterizedType(List::class.java, InfoComsApi::class.java)
            else -> throw(Exception("Unknown type for parametrized type"))
        }
    }
}