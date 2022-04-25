package fr.synchrone.glpisupport.core.di

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.synchrone.glpisupport.data.dao.DeviceHistoryDao
import fr.synchrone.glpisupport.data.repository.*
import fr.synchrone.glpisupport.data.service.ItemsService
import fr.synchrone.glpisupport.data.service.LoginService
import fr.synchrone.glpisupport.data.service.SearchService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTokenRepository(@ApplicationContext context: Context): TokenRepository =
        TokenRepository(context)

    @Provides
    @Singleton
    fun provideLoginRepository(loginService: LoginService): LoginRepository =
        LoginRepository(loginService)

    @Provides
    @Singleton
    fun provideSearchRepository(searchService: SearchService): SearchRepository =
        SearchRepository(searchService)

    @Provides
    @Singleton
    fun provideItemsRepository(itemsService: ItemsService, searchService: SearchService, moshi: Moshi, deviceHistoryDao: DeviceHistoryDao): ItemsRepository =
        ItemsRepository(itemsService = itemsService, searchService = searchService, moshi = moshi, deviceHistoryDao = deviceHistoryDao)
}