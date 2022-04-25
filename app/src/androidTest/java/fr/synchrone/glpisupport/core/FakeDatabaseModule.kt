package fr.synchrone.glpisupport.core

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import fr.synchrone.glpisupport.core.di.DatabaseModule
import fr.synchrone.glpisupport.data.dao.DeviceHistoryDao
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object FakeDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GlpiDatabase {
        return Room.inMemoryDatabaseBuilder(
            appContext,
            GlpiDatabase::class.java
        ).build()
    }

    @Provides
    fun provideChannelDao(glpiDatabase: GlpiDatabase): DeviceHistoryDao {
        return glpiDatabase.deviceHistoryDao()
    }

}