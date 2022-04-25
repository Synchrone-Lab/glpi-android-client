package fr.synchrone.glpisupport.resources

import fr.synchrone.glpisupport.data.model.remote.items.InfoComsApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.*
import fr.synchrone.glpisupport.presentation.items.viewmodel.helpers.ItemViewModelEmptiesItems

object BaseItemsContent {
    val computerApi = ComputerApi(
        id = 229,
        entityId = 2,
        name = "computer test",
        serial = "123456789",
        locationId = 3,
        _is_template = 0,
        userId = 4,
        userTechId = 5,
        stateId = 6,
        computerTypeId = 7,
        computerModelId = 8,
        manufacturerId = 9,
        comment = "comment test",
        _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
    )

    val phoneApi = PhoneApi(
        id = 4,
        entityId = 2,
        name = "computer test",
        serial = "123456789",
        userNumber = "0601020304",
        locationId = 3,
        _is_template = 0,
        userId = 4,
        userTechId = 5,
        stateId = 6,
        phoneTypeId = 7,
        phoneModelId = 8,
        manufacturerId = 9,
        comment = "comment test",
        _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
    )

    val printerApi = PrinterApi(
        id = 4,
        entityId = 2,
        userNumber = "0601020304",
        name = "printer test",
        serial = "123456789",
        locationId = 3,
        _is_template = 0,
        userId = 5,
        userTechId = 6,
        groupId = 7,
        groupIdTech = 8,
        stateId = 9,
        printerTypeId = 10,
        printerModelId = 11,
        manufacturerId = 12,
        memorySize = "128",
        haveEthernet = 12,
        haveParallel = 13,
        haveSerial = 14,
        haveUsb = 15,
        haveWifi = 16,
        initPagesCounter = 17,
        lastPagesCounter = 18,
        comment = "comment test",
        _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
    )

    val softwareApi = SoftwareApi(
        id = 4,
        entityId = 2,
        name = "computer test",
        locationId = 3,
        _is_template = 0,
        userId = 4,
        userTechId = 5,
        softwareCategoryId = 7,
        manufacturerId = 9,
        _isHelpdeskVisible = 1,
        groupTechId = 10,
        comment = "comment test",
        _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
    )

    val softwareLicenseApi = SoftwareLicenseApi(
        id = 4,
        entityId = 2,
        name = "computer test",
        locationId = 3,
        _is_template = 0,
        userId = 4,
        userTechId = 5,
        stateId = 6,
        softwareLicenceTypeId = 7,
        manufacturerId = 9,
        groupId = 10,
        groupTechId = 11,
        number = 12,
        _allow_overquota = 1,
        expire = "",
        softwareId = 13,
        softwareLicenseId = 14,
        comment = "comment test",
        _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
    )

    val networkEquipmentApi = NetworkEquipmentApi(
        id = 4,
        entityId = 2,
        name = "computer test",
        serial = "123456789",
        locationId = 3,
        _is_template = 0,
        userId = 4,
        userTechId = 5,
        stateId = 6,
        networkEquipmentTypeId = 7,
        networkEquipmentModelId = 8,
        manufacturerId = 9,
        userNumber = "0601020304",
        ram = "128 mo",
        comment = "comment test",
        _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
    )

    val monitorApi = MonitorApi(
        id = 4,
        entityId = 2,
        name = "computer test",
        serial = "123456789",
        locationId = 3,
        _is_template = 0,
        userId = 4,
        userTechId = 5,
        stateId = 6,
        monitorTypeId = 7,
        monitorModelId = 8,
        manufacturerId = 9,
        _have_micro = 1,
        _have_speaker = 1,
        _have_subd = 1,
        _have_dvi = 1,
        _have_hdmi = 1,
        _have_bnc = 1,
        _have_pivot = 1,
        _have_displayport = 1,
        comment = "comment test",
        _infocoms = ItemViewModelEmptiesItems.emptyInfoComs
    )

    val infoComsApi = InfoComsApi(
        id = 1,
        entityId = 2,
        orderDate = "2021-01-01",
        buyDate = "2021-01-02",
        deliveryDate = "2021-01-03",
        useDate = "2021-01-04",
        inventoryDate = null,
        decommissionDate = null,
        supplierId = 3,
        orderNumber = null,
        bill = null,
        value = "123",
        sinkType = 1,
        sinkCoeff = 3f,
        sinkTime = 2,
        businessCriticityId = 4,
        budgetId = 5,
        deliveryNumber = "2021-01-05",
        immoNumber = null,
        warrantyValue = 6f,
        warrantyDate = null,
        warrantyInfo = null,
        alertId = 7
    )
}