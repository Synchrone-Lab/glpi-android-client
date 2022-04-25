package fr.synchrone.glpisupport.data.model.remote.items.devices

abstract class DeviceWithSerialApi: DeviceApi() {
    abstract val serial: String?

    abstract fun newInstanceWithSerial(serial: String): DeviceWithSerialApi
}