package fr.synchrone.glpisupport.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import fr.synchrone.glpisupport.data.repository.ItemsRepository
import fr.synchrone.glpisupport.data.repository.LoginRepository
import fr.synchrone.glpisupport.data.repository.SearchRepository
import fr.synchrone.glpisupport.data.repository.TokenRepository
import fr.synchrone.glpisupport.domain.LoginUseCase
import fr.synchrone.glpisupport.domain.SearchUseCase

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {
    @Provides
    fun providesLoginUseCase(loginRepository: LoginRepository, tokenRepository: TokenRepository, itemsRepository: ItemsRepository): LoginUseCase =
        LoginUseCase(loginRepository, tokenRepository, itemsRepository)

    @Provides
    fun providesSearchUseCase(searchRepository: SearchRepository): SearchUseCase =
        SearchUseCase(searchRepository)
}