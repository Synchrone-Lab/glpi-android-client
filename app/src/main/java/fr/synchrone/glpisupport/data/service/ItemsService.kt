package fr.synchrone.glpisupport.data.service

import fr.synchrone.glpisupport.data.model.remote.items.*
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import fr.synchrone.glpisupport.data.model.remote.items.ProfileApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.data.model.remote.items.responses.PostItemResponseApi
import okhttp3.RequestBody
import retrofit2.http.*

interface ItemsService {

    @GET("Computer/{id}?with_infocoms=1")
    suspend fun getComputerById(@Path("id") id: Int): ComputerApi

    @GET("Phone/{id}?with_infocoms=1")
    suspend fun getPhoneById(@Path("id") id: Int): PhoneApi

    @GET("Software/{id}?with_infocoms=1")
    suspend fun getSoftwareById(@Path("id") id: Int): SoftwareApi

    @GET("Monitor/{id}?with_infocoms=1")
    suspend fun getMonitorById(@Path("id") id: Int): MonitorApi

    @GET("Printer/{id}?with_infocoms=1")
    suspend fun getPrinterById(@Path("id") id: Int): PrinterApi

    @GET("NetworkEquipment/{id}?with_infocoms=1")
    suspend fun getNetworkEquipmentById(@Path("id") id: Int): NetworkEquipmentApi

    @GET("SoftwareLicense/{id}?with_infocoms=1")
    suspend fun getSoftwareLicenceById(@Path("id") id: Int): SoftwareLicenseApi

    @GET("Rack/{id}?with_infocoms=1")
    suspend fun getRackById(@Path("id") id: Int): RackApi

    @GET("profile_user/{id}")
    suspend fun getProfileUserById(@Path("id") id: Int): ProfileUserApi

    @GET("Location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): LocationApi

    @GET("Location")
    suspend fun getAllLocations(): List<LocationApi>

    @GET("Entity?range=0-2000")
    suspend fun getAllEntities(): List<EntityApi>

    @GET("Profile")
    suspend fun getAllProfiles(): List<ProfileApi>

    @GET("Supplier")
    suspend fun getAllSuppliers(): List<SupplierApi>

    @GET("User?range=0-20000")
    suspend fun getAllUsers(): List<UserApi>

    @GET("State")
    suspend fun getAllStates(): List<StateApi>

    @GET("ComputerType")
    suspend fun getAllComputersTypes(): List<ComputerTypeApi>

    @GET("PhoneType")
    suspend fun getAllPhonesTypes(): List<PhoneTypeApi>

    @GET("MonitorType")
    suspend fun getAllMonitorsTypes(): List<MonitorTypeApi>

    @GET("PrinterType")
    suspend fun getAllPrintersTypes(): List<PrinterTypeApi>

    @GET("NetworkEquipmentType")
    suspend fun getAllNetworkEquipmentTypes(): List<NetworkEquipmentTypeApi>

    @GET("SoftwareLicenseType")
    suspend fun getAllSoftwareLicensesTypes(): List<SoftwareLicenseTypeApi>

    @GET("RackType")
    suspend fun getAllRacksTypes(): List<RackTypeApi>

    @GET("Manufacturer")
    suspend fun getAllManufacturers(): List<ManufacturerApi>

    @GET("ComputerModel")
    suspend fun getAllComputersModels(): List<ComputerModelApi>

    @GET("PhoneModel")
    suspend fun getAllPhonesModels(): List<PhoneModelApi>

    @GET("MonitorModel")
    suspend fun getAllMonitorsModels(): List<MonitorModelApi>

    @GET("PrinterModel")
    suspend fun getAllPrintersModels(): List<PrinterModelApi>

    @GET("NetworkEquipmentModel")
    suspend fun getAllNetworkEquipmentModels(): List<NetworkEquipmentModelApi>

    @GET("RackModel")
    suspend fun getAllRacksModels(): List<RackModelApi>

    @GET("Group")
    suspend fun getAllGroups(): List<GroupApi>

    @GET("SoftwareCategory")
    suspend fun getAllSoftwareCategories(): List<SoftwareCategoryApi>

    @GET("Software")
    suspend fun getAllSoftwares(): List<SoftwareApi>

    @GET("SoftwareLicense")
    suspend fun getAllSoftwaresLicenses(): List<SoftwareLicenseApi>

    @GET("BusinessCriticity")
    suspend fun getAllBusinessCriticities(): List<BusinessCriticityApi>

    @GET("Budget")
    suspend fun getAllBudgets(): List<BudgetApi>

    @GET("DcRoom")
    suspend fun getAllDCRooms(): List<DCRoomApi>

    @GET("Computer?sort=is_template&order=DESC&range=0-200")
    suspend fun getComputersTemplates(): List<ComputerApi>

    @GET("Phone?sort=is_template&order=DESC&range=0-200")
    suspend fun getPhonesTemplates(): List<PhoneApi>

    @GET("Monitor?sort=is_template&order=DESC&range=0-200")
    suspend fun getMonitorsTemplates(): List<MonitorApi>

    @GET("Networkequipment?sort=is_template&order=DESC&range=0-200")
    suspend fun getNetworkEquipmentsTemplates(): List<NetworkEquipmentApi>

    @GET("Printer?sort=is_template&order=DESC&range=0-200")
    suspend fun getPrintersTemplates(): List<PrinterApi>

    @GET("Software?sort=is_template&order=DESC&range=0-200")
    suspend fun getSoftwaresTemplates(): List<SoftwareApi>

    @GET("SoftwareLicense?sort=is_template&order=DESC&range=0-200")
    suspend fun getSoftwaresLicensesTemplates(): List<SoftwareLicenseApi>

    @PATCH("Computer")
    suspend fun updateComputer(@Body body: RequestBody)

    @PATCH("Phone")
    suspend fun updatePhone(@Body body: RequestBody)

    @PATCH("Printer")
    suspend fun updatePrinter(@Body body: RequestBody)

    @PATCH("Software")
    suspend fun updateSoftware(@Body body: RequestBody)

    @PATCH("Monitor")
    suspend fun updateMonitor(@Body body: RequestBody)

    @PATCH("NetworkEquipment")
    suspend fun updateNetworkEquipment(@Body body: RequestBody)

    @PATCH("SoftwareLicense")
    suspend fun updateSoftwareLicense(@Body body: RequestBody)

    @PATCH("Rack")
    suspend fun updateRack(@Body body: RequestBody)

    @PATCH("InfoCom")
    suspend fun updateInfoCom(@Body body: RequestBody)

    @POST("Computer")
    suspend fun addComputer(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("Phone")
    suspend fun addPhone(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("Printer")
    suspend fun addPrinter(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("NetworkEquipment")
    suspend fun addNetworkEquipment(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("Software")
    suspend fun addSoftware(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("Monitor")
    suspend fun addMonitor(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("SoftwareLicense")
    suspend fun addSoftwareLicense(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("Rack")
    suspend fun addRack(@Body body: RequestBody): List<PostItemResponseApi>

}