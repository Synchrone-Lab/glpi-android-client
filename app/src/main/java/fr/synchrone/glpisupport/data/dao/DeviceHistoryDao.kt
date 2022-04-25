package fr.synchrone.glpisupport.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.synchrone.glpisupport.data.model.local.devices.DeviceHistoryDb

@Dao
interface DeviceHistoryDao {

    @Transaction
    @Query("SELECT * FROM DeviceHistory ORDER BY insertionDate DESC")
    fun getAllDevicesHistories(): LiveData<List<DeviceHistoryDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceHistory(deviceHistory: DeviceHistoryDb)

    @Query("DELETE FROM DeviceHistory")
    suspend fun deleteAll()
}