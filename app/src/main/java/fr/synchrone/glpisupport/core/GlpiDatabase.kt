package fr.synchrone.glpisupport.core

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.synchrone.glpisupport.data.dao.DeviceHistoryDao
import fr.synchrone.glpisupport.data.model.local.devices.DeviceHistoryDb

@Database(entities = [DeviceHistoryDb::class], version = 1, exportSchema = false)
abstract class GlpiDatabase : RoomDatabase() {
    abstract fun deviceHistoryDao(): DeviceHistoryDao
}