package fr.synchrone.glpisupport.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.synchrone.glpisupport.core.GlpiDatabase
import fr.synchrone.glpisupport.data.dao.DeviceHistoryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GlpiDatabase {
        return Room.databaseBuilder(
            appContext,
            GlpiDatabase::class.java,
            "GlpiDatabase"
        ).build()
    }

    @Provides
    fun provideChannelDao(glpiDatabase: GlpiDatabase): DeviceHistoryDao {
        return glpiDatabase.deviceHistoryDao()
    }

}