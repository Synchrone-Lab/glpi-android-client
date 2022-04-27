package fr.synchrone.glpisupport.presentation.utilities.functions

import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.data.model.remote.items.devices.*

object ItemsUtilities {

    fun getIconDrawableIdForItem(itemType: String): Int {
        return when {
            itemType.equals(ComputerApi.typeName, ignoreCase = true) -> R.drawable.laptop
            itemType.equals(PhoneApi.typeName, ignoreCase = true) -> R.drawable.mobile_icon
            itemType.equals(SoftwareApi.typeName, ignoreCase = true) -> R.drawable.cube
            itemType.equals(MonitorApi.typeName, ignoreCase = true) -> R.drawable.desktop_icon
            itemType.equals(PrinterApi.typeName, ignoreCase = true) -> R.drawable.ic_print
            itemType.equals(NetworkEquipmentApi.typeName, ignoreCase = true) -> R.drawable.ic_network_wired
            itemType.equals(RackApi.typeName, ignoreCase = true) -> R.drawable.ic_server
            itemType.equals(SoftwareLicenseApi.typeName, ignoreCase = true) -> R.drawable.key
            else -> R.drawable.laptop
        }
    }

    fun getDisplayableDeviceTypeNameForItem(itemType: String): String {
        return when {
            itemType.equals(ComputerApi.typeName, ignoreCase = true) -> "Ordinateur"
            itemType.equals(PhoneApi.typeName, ignoreCase = true) -> "Téléphone"
            itemType.equals(SoftwareApi.typeName, ignoreCase = true) -> "Logiciel"
            itemType.equals(MonitorApi.typeName, ignoreCase = true) -> "Moniteur"
            itemType.equals(PrinterApi.typeName, ignoreCase = true) -> "Imprimante"
            itemType.equals(NetworkEquipmentApi.typeName, ignoreCase = true) -> "Matériel réseau"
            itemType.equals(RackApi.typeName, ignoreCase = true) -> "Baie"
            itemType.equals(SoftwareLicenseApi.typeName, ignoreCase = true) -> "Licence"
            else -> ""
        }
    }
}
