package fr.synchrone.glpisupport.core

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import fr.synchrone.glpisupport.core.di.NetworkModule
import fr.synchrone.glpisupport.core.http.HttpInterceptor
import fr.synchrone.glpisupport.data.repository.TokenRepository
import fr.synchrone.glpisupport.data.service.ItemsService
import fr.synchrone.glpisupport.data.service.LoginService
import fr.synchrone.glpisupport.data.service.SearchService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object FakeNetworkModule {

    private const val BASE_URL = "http://10.0.2.2:3001"

    @Provides
    @Singleton
    fun provideHttpLoggerInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    fun provideHttpInterceptor(tokenRepository: TokenRepository): Interceptor =
        HttpInterceptor(tokenRepository)

    @Provides
    @Singleton
    fun provideHttpClient(
        httpInterceptor: HttpInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(httpInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofitClient(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun provideItemsService(retrofit: Retrofit): ItemsService =
        retrofit.create(ItemsService::class.java)
}