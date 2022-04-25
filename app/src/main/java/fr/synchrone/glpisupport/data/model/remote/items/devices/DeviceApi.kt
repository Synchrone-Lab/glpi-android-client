package fr.synchrone.glpisupport.data.model.remote.items.devices

import fr.synchrone.glpisupport.data.model.remote.items.InfoComsApi
import fr.synchrone.glpisupport.data.model.remote.items.ItemApi

abstract class DeviceApi: ItemApi {
    abstract val _infocoms: Any?

    val infoComs: InfoComsApi? get() = if (_infocoms is InfoComsApi) _infocoms as InfoComsApi else null

    abstract fun newInstanceWithInfoComs(infoComs: InfoComsApi): DeviceApi
}