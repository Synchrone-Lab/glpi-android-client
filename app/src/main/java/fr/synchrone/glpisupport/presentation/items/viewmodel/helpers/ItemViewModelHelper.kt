package fr.synchrone.glpisupport.presentation.items.viewmodel.helpers

import android.content.Context
import fr.synchrone.glpisupport.data.model.remote.items.EntitledApi
import fr.synchrone.glpisupport.data.model.remote.items.StateApi
import fr.synchrone.glpisupport.data.model.remote.items.devices.DeviceApi
import fr.synchrone.glpisupport.domain.ItemsUseCase
import fr.synchrone.glpisupport.presentation.items.ItemField
import fr.synchrone.glpisupport.presentation.items.viewmodel.PickersElementsGrouped

/**
 * Abstract class that tell to ItemViewModel how to manage item type.
 * 
 * @see ItemViewModelComputerHelper
 * @see ItemViewModelPhoneHelper
 * @see ItemViewModelMonitorHelper
 * @see ItemViewModelPrinterHelper
 * @see ItemViewModelNetworkEquipmentHelper
 * @see ItemViewModelRackHelper
 * @see ItemViewModelSoftwareHelper
 * @see ItemViewModelSoftwareLicenseHelper
 */
abstract class ItemViewModelHelper (
    val infos: ItemViewModelInfos
) {
    abstract fun getFieldsForDevice(context: Context, item: DeviceApi): List<ItemField>
    abstract fun getUpdatedDeviceWithFields(context: Context, originalItem: DeviceApi, fields: Map<String, String>): DeviceApi
    abstract fun getEmptyItem(): DeviceApi
    abstract fun getFetchConfig(): ItemViewModelFetchConfig
    open fun areInfosComsEnabled(item: DeviceApi): Boolean = item.infoComs != null
    open fun getStaticsFieldsForLabel(context: Context, label: String): PickersElementsGrouped = mapOf()
    abstract suspend fun fetchModels(itemsUseCase: ItemsUseCase): List<EntitledApi>
    abstract suspend fun fetchTypes(itemsUseCase: ItemsUseCase): List<EntitledApi>
    abstract suspend fun fetchDevice(itemsUseCase: ItemsUseCase, deviceId: Int): DeviceApi
    abstract suspend fun fetchPossiblesStates(itemsUseCase: ItemsUseCase): List<StateApi>
}