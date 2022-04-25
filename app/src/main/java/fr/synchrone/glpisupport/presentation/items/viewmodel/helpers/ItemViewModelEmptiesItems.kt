package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.*
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.presentation.home.HomeViewModel

/**
 * Collection of items with empties values.
 */
object ItemViewModelEmptiesItems {

    val emptyInfoComs = InfoComsApi(
        id = 0,
        entityId = 0,
        orderDate = null,
        buyDate = null,
        deliveryDate = null,
        useDate = null,
        inventoryDate = null,
        decommissionDate = null,
        supplierId = 0,
        orderNumber = null,
        bill = null,
        value = null,
        sinkType = 0,
        sinkCoeff = 0f,
        sinkTime = 0,
        businessCriticityId = 0,
        budgetId = 0,
        deliveryNumber = "",
        immoNumber = null,
        warrantyValue = 0f,
        warrantyDate = null,
        warrantyInfo = null,
        alertId = 0
    )

    val emptyComputerApi = ComputerApi(
        id = HomeViewModel.NO_TEMPLATE_ID,
        entityId = 0,
        name = "",
        _is_template = 0,
        comment = null,
        computerModelId = 0,
        computerTypeId = 0,
        locationId = 0,
        manufacturerId = 0,
        stateId = 0,
        serial = "",
        userTechId = 0,
        userId = 0,
        _infocoms = emptyInfoComs
    )

    val emptyPhoneApi = PhoneApi(
        id = HomeViewModel.NO_TEMPLATE_ID,
        entityId = 0,
        name = "",
        _is_template = 1,
        comment = null,
        phoneModelId = 0,
        phoneTypeId = 0,
        locationId = 0,
        manufacturerId = 0,
        stateId = 0,
        serial = "",
        userTechId = 0,
        userId = 0,
        userNumber = "",
        _infocoms = emptyInfoComs
    )

    val emptyNetworkEquipmentApi = NetworkEquipmentApi(
        id = HomeViewModel.NO_TEMPLATE_ID,
        entityId = 0,
        name = "",
        _is_template = 1,
        comment = null,
        networkEquipmentModelId = 0,
        networkEquipmentTypeId = 0,
        locationId = 0,
        manufacturerId = 0,
        stateId = 0,
        ram = "",
        serial = "",
        userTechId = 0,
        userId = 0,
        userNumber = "",
        _infocoms = emptyInfoComs
    )

    val emptyRackApi = RackApi(
        id = HomeViewModel.NO_TEMPLATE_ID,
        entityId = 0,
        name = "",
        _is_template = 1,
        comment = null,
        rackModelId = 0,
        rackTypeId = 0,
        width = 0,
        height = 0,
        depth = 0,
        numberUnits = 0,
        dcroomsId = 0,
        roomOrientation = 0,
        maxPower = 0,
        mesuredPower = 0,
        maxWeight = 0,
        locationId = 0,
        manufacturerId = 0,
        stateId = 0,
        serial = "",
        userTechId = 0,
        _infocoms = emptyInfoComs
    )

    val emptyMonitorApi = MonitorApi(
        id = 0,
        name = "",
        entityId = 0,
        locationId = 0,
        _is_template = 0,
        userTechId = 0,
        userId = 0,
        stateId = 0,
        manufacturerId = 0,
        monitorModelId = 0,
        monitorTypeId = 0,
        serial = "",
        _have_micro = 0,
        _have_speaker = 0,
        _have_subd = 0,
        _have_bnc = 0,
        _have_dvi = 0,
        _have_hdmi = 0,
        _have_pivot = 0,
        _have_displayport = 0,
        comment = null,
        _infocoms = emptyInfoComs
    )

    val emptyPrinterApi = PrinterApi(
        id = 0,
        name ="",
        entityId = 0,
        locationId = 0,
        userTechId = 0,
        _is_template = 0,
        userId = 0,
        userNumber = "",
        stateId = 0,
        serial = "",
        comment = null,
        printerTypeId = 0,
        printerModelId = 0,
        groupId = 0,
        groupIdTech = 0,
        initPagesCounter = 0,
        lastPagesCounter = 0,
        manufacturerId = 0,
        memorySize = null,
        haveSerial = 0,
        haveParallel = 0,
        haveUsb = 0,
        haveWifi = 0,
        haveEthernet = 0,
        _infocoms = emptyInfoComs
    )

    val emptySoftwareApi = SoftwareApi(
        id = 0,
        name = "----",
        entityId = 0,
        locationId = 0,
        _is_template = 0,
        userTechId = 0,
        groupTechId = 0,
        userId = 0,
        manufacturerId = 0,
        softwareCategoryId = 0,
        _isHelpdeskVisible = 0,
        comment = null,
        _infocoms = emptyInfoComs
    )

    val emptySoftwareLicenseApi = SoftwareLicenseApi(
        id = 0,
        name = "----",
        entityId = 0,
        softwareId = 0,
        softwareLicenseId = 0,
        softwareLicenceTypeId = 0,
        locationId = 0,
        _is_template = 0,
        userTechId = 0,
        groupTechId = 0,
        userId = 0,
        number = 0,
        groupId = 0,
        _allow_overquota = 0,
        stateId = 0,
        manufacturerId = 0,
        expire = null,
        comment = "",
        _infocoms = emptyInfoComs
    )

    val emptyEntityApi = EntityApi(
        entityId = 0,
        isRecursive = null,
        name = "Autre",
        _ancestorsCache = null
    )

    val emptyProfileApi = ProfileApi(
        profileId = 0,
        name = "Autre"
    )

    val emptyUserApi = UserApi(
        id = 0,
        name = "----",
        entityId = 0,
        profileId = 0,
        realname = "----",
        firstname = "----"
    )

    val emptyGroupApi = GroupApi(
        id = 0,
        name = "----",
        entityId = 0
    )

    val emptyLocationApi = LocationApi(
        id = 0,
        name = "----",
        entityId = 0
    )

    val emptySupplierApi = SupplierApi(
        id = 0,
        name = "----",
        entityId = 0
    )

    val emptyStateApi = StateApi(
        id = 0,
        name = "----",
        entityId = 0,
        _is_visible_computer = 0,
        _is_visible_phone = 0,
        _is_visible_monitor = 0,
        _is_visible_printer = 0,
        _is_visible_softwarelicense = 0,
        _is_visible_rack = 0,
        _is_visible_networkequipment = 0
    )

    val emptyManufacturerApi = ManufacturerApi(
        id = 0,
        name = "----"
    )

    val emptyBusinessCriticityApi = BusinessCriticityApi(
        id = 0,
        name = "----"
    )

    val emptyBudgetApi = BudgetApi(
        id = 0,
        name = "----"
    )

    val emptyDcRoomApi = DCRoomApi(
        id = 0,
        name = "----",
        datacenterId = 0,
        entityId = 0,
        locationId = 0,
        visCols = 0,
        visRows = 0
    )

    val emptyModelApi = object : EntitledApi {
        override val id: Int = 0
        override val name: String = "----"
    }

    val emptyTypeApi = object : EntitledApi {
        override val id: Int = 0
        override val name: String = "----"
    }

    fun getUnknownEntitledApi(context: Context) = object : EntitledApi {
        override val id: Int = 0
        override val name: String = context.getString(R.string.no_item_name)
    }

}