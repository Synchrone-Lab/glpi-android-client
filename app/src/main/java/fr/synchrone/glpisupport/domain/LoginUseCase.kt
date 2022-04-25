package fr.synchrone.glpisupport.domain

import fr.synchrone.glpisupport.data.model.Token
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import fr.synchrone.glpisupport.data.model.remote.user.myprofiles.MyProfilesApi
import fr.synchrone.glpisupport.data.repository.ItemsRepository
import fr.synchrone.glpisupport.data.repository.LoginRepository
import fr.synchrone.glpisupport.data.repository.TokenRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val tokenRepository: TokenRepository,
    private val itemsRepository: ItemsRepository
) {

    data class ActiveProfileAndEntity(
        val activeProfile: MyProfilesApi,
        val activeEntity: EntityApi
    )

    suspend fun isLoggedIn(): Boolean = tokenRepository.isLoggedIn()

    suspend fun loginAndStoreToken(credentials: String): Token {
        val token: Token = getLoginResponse(credentials)
        storeToken(token)
        itemsRepository.deleteAllDevicesHistoriesFromDb()
        return token
    }

    suspend fun logout() {
        tokenRepository.removeToken()
        itemsRepository.deleteAllDevicesHistoriesFromDb()
    }

    suspend fun getMyProfiles(): List<MyProfilesApi> = loginRepository.getMyProfiles().myProfiles

    suspend fun getMyEntities() = loginRepository.getMyEntities().myEntities.map { parseEntityName(entityApi = it) }

    suspend fun getActivesProfilesAndEntities(): ActiveProfileAndEntity {
        val profiles = loginRepository.getMyProfiles().myProfiles
        val entities = loginRepository.getMyEntities().myEntities.map { parseEntityName(entityApi = it) }
        val activeProfileId = loginRepository.getActiveProfile().id
        val activeEntityId = loginRepository.getActiveEntities().id

        val activeProfile = profiles.first { it.id == activeProfileId }
        val activeEntity = entities.first { it.entityId == activeEntityId }

        return ActiveProfileAndEntity(
            activeProfile = activeProfile,
            activeEntity = activeEntity
        )
    }

    suspend fun changeActiveProfile(profile: MyProfilesApi){
        loginRepository.changeActiveProfile(profile = profile)
        itemsRepository.deleteAllDevicesHistoriesFromDb()
    }

    suspend fun changeActiveEntity(entity: EntityApi){
        loginRepository.changeActiveEntity(entity = entity)
        itemsRepository.deleteAllDevicesHistoriesFromDb()
    }

    private suspend fun getLoginResponse(credentials: String): Token =
        loginRepository.login(credentials)

    private suspend fun storeToken(token: Token) = tokenRepository.storeToken(token)

    private fun parseEntityName(entityApi: EntityApi): EntityApi {
        val removeParentEntitiesReges = Regex(".*&gt; ")
        var newEntityApi = entityApi
        while (removeParentEntitiesReges.containsMatchIn(newEntityApi.name)) {
            newEntityApi = newEntityApi.copy(name = removeParentEntitiesReges.replace(entityApi.name, ""))
        }

        return newEntityApi
    }

}