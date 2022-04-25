package fr.synchrone.glpisupport.data.model.local.devices

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "DeviceHistory", primaryKeys = ["id", "itemName"])
data class DeviceHistoryDb(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "itemName") val itemType: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "insertionDate") val insertionDate: String
)