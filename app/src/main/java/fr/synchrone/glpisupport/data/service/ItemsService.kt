package fr.synchrone.glpisupport.data.service

import fr.synchrone.glpisupport.data.model.remote.items.*
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import fr.synchrone.glpisupport.data.model.remote.items.ProfileApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.data.model.remote.items.responses.PostItemResponseApi
import okhttp3.RequestBody
import retrofit2.http.*

interface ItemsService {

    @GET("computer/{id}?with_infocoms=1")
    suspend fun getComputerById(@Path("id") id: Int): ComputerApi

    @GET("phone/{id}?with_infocoms=1")
    suspend fun getPhoneById(@Path("id") id: Int): PhoneApi

    @GET("software/{id}?with_infocoms=1")
    suspend fun getSoftwareById(@Path("id") id: Int): SoftwareApi

    @GET("monitor/{id}?with_infocoms=1")
    suspend fun getMonitorById(@Path("id") id: Int): MonitorApi

    @GET("printer/{id}?with_infocoms=1")
    suspend fun getPrinterById(@Path("id") id: Int): PrinterApi

    @GET("networkequipment/{id}?with_infocoms=1")
    suspend fun getNetworkEquipmentById(@Path("id") id: Int): NetworkEquipmentApi

    @GET("softwarelicense/{id}?with_infocoms=1")
    suspend fun getSoftwareLicenceById(@Path("id") id: Int): SoftwareLicenseApi

    @GET("rack/{id}?with_infocoms=1")
    suspend fun getRackById(@Path("id") id: Int): RackApi

    @GET("profile_user/{id}")
    suspend fun getProfileUserById(@Path("id") id: Int): ProfileUserApi

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): LocationApi

    @GET("location")
    suspend fun getAllLocations(): List<LocationApi>

    @GET("entity?range=0-2000")
    suspend fun getAllEntities(): List<EntityApi>

    @GET("profile")
    suspend fun getAllProfiles(): List<ProfileApi>

    @GET("supplier")
    suspend fun getAllSuppliers(): List<SupplierApi>

    @GET("user?range=0-20000")
    suspend fun getAllUsers(): List<UserApi>

    @GET("state")
    suspend fun getAllStates(): List<StateApi>

    @GET("computertype")
    suspend fun getAllComputersTypes(): List<ComputerTypeApi>

    @GET("phonetype")
    suspend fun getAllPhonesTypes(): List<PhoneTypeApi>

    @GET("monitortype")
    suspend fun getAllMonitorsTypes(): List<MonitorTypeApi>

    @GET("printertype")
    suspend fun getAllPrintersTypes(): List<PrinterTypeApi>

    @GET("networkequipmenttype")
    suspend fun getAllNetworkEquipmentTypes(): List<NetworkEquipmentTypeApi>

    @GET("softwarelicensetype")
    suspend fun getAllSoftwareLicensesTypes(): List<SoftwareLicenseTypeApi>

    @GET("racktype")
    suspend fun getAllRacksTypes(): List<RackTypeApi>

    @GET("manufacturer")
    suspend fun getAllManufacturers(): List<ManufacturerApi>

    @GET("computermodel")
    suspend fun getAllComputersModels(): List<ComputerModelApi>

    @GET("phonemodel")
    suspend fun getAllPhonesModels(): List<PhoneModelApi>

    @GET("monitormodel")
    suspend fun getAllMonitorsModels(): List<MonitorModelApi>

    @GET("printermodel")
    suspend fun getAllPrintersModels(): List<PrinterModelApi>

    @GET("networkequipmentmodel")
    suspend fun getAllNetworkEquipmentModels(): List<NetworkEquipmentModelApi>

    @GET("rackmodel")
    suspend fun getAllRacksModels(): List<RackModelApi>

    @GET("group")
    suspend fun getAllGroups(): List<GroupApi>

    @GET("softwarecategory")
    suspend fun getAllSoftwareCategories(): List<SoftwareCategoryApi>

    @GET("software")
    suspend fun getAllSoftwares(): List<SoftwareApi>

    @GET("softwarelicense")
    suspend fun getAllSoftwaresLicenses(): List<SoftwareLicenseApi>

    @GET("businesscriticity")
    suspend fun getAllBusinessCriticities(): List<BusinessCriticityApi>

    @GET("budget")
    suspend fun getAllBudgets(): List<BudgetApi>

    @GET("dcroom")
    suspend fun getAllDCRooms(): List<DCRoomApi>

    @GET("computer?sort=is_template&order=DESC&range=0-200")
    suspend fun getComputersTemplates(): List<ComputerApi>

    @GET("phone?sort=is_template&order=DESC&range=0-200")
    suspend fun getPhonesTemplates(): List<PhoneApi>

    @GET("monitor?sort=is_template&order=DESC&range=0-200")
    suspend fun getMonitorsTemplates(): List<MonitorApi>

    @GET("networkequipment?sort=is_template&order=DESC&range=0-200")
    suspend fun getNetworkEquipmentsTemplates(): List<NetworkEquipmentApi>

    @GET("printer?sort=is_template&order=DESC&range=0-200")
    suspend fun getPrintersTemplates(): List<PrinterApi>

    @GET("software?sort=is_template&order=DESC&range=0-200")
    suspend fun getSoftwaresTemplates(): List<SoftwareApi>

    @GET("softwarelicense?sort=is_template&order=DESC&range=0-200")
    suspend fun getSoftwaresLicensesTemplates(): List<SoftwareLicenseApi>

    @PATCH("computer")
    suspend fun updateComputer(@Body body: RequestBody)

    @PATCH("phone")
    suspend fun updatePhone(@Body body: RequestBody)

    @PATCH("printer")
    suspend fun updatePrinter(@Body body: RequestBody)

    @PATCH("software")
    suspend fun updateSoftware(@Body body: RequestBody)

    @PATCH("monitor")
    suspend fun updateMonitor(@Body body: RequestBody)

    @PATCH("networkequipment")
    suspend fun updateNetworkEquipment(@Body body: RequestBody)

    @PATCH("softwarelicense")
    suspend fun updateSoftwareLicense(@Body body: RequestBody)

    @PATCH("rack")
    suspend fun updateRack(@Body body: RequestBody)

    @PATCH("infocom")
    suspend fun updateInfoCom(@Body body: RequestBody)

    @POST("computer")
    suspend fun addComputer(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("phone")
    suspend fun addPhone(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("printer")
    suspend fun addPrinter(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("networkequipment")
    suspend fun addNetworkEquipment(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("software")
    suspend fun addSoftware(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("monitor")
    suspend fun addMonitor(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("softwarelicense")
    suspend fun addSoftwareLicense(@Body body: RequestBody): List<PostItemResponseApi>

    @POST("rack")
    suspend fun addRack(@Body body: RequestBody): List<PostItemResponseApi>

}