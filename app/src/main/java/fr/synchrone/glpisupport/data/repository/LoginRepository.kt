package fr.synchrone.glpisupport.data.repository

import fr.synchrone.glpisupport.data.model.Token
import fr.synchrone.glpisupport.data.model.remote.items.EntityApi
import fr.synchrone.glpisupport.data.model.remote.user.myentities.ActiveEntityApi
import fr.synchrone.glpisupport.data.model.remote.user.myentities.MyEntitiesResponseApi
import fr.synchrone.glpisupport.data.model.remote.user.myprofiles.ActiveProfileApi
import fr.synchrone.glpisupport.data.model.remote.user.myprofiles.MyProfilesApi
import fr.synchrone.glpisupport.data.model.remote.user.myprofiles.MyProfilesResponseApi
import fr.synchrone.glpisupport.data.service.LoginService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginService: LoginService
) {

    suspend fun login(credentials: String): Token = loginService.login(credentials)

    suspend fun getMyProfiles(): MyProfilesResponseApi = loginService.getMyProfiles()

    suspend fun getMyEntities(): MyEntitiesResponseApi = loginService.getMyEntities(isRecursive = true)

    suspend fun getActiveProfile(): ActiveProfileApi = loginService.getActiveProfile().activeProfile

    suspend fun getActiveEntities(): ActiveEntityApi = loginService.getActiveEntities().activeEntity

    suspend fun changeActiveProfile(profile: MyProfilesApi) {
        val jsonObject = JSONObject()
        jsonObject.put("profiles_id", profile.id)

        loginService.changeActiveProfile(
            jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )
    }

    suspend fun changeActiveEntity(entity: EntityApi): Boolean {
        val jsonObject = JSONObject()
        jsonObject.put("entities_id", entity.entityId)

        return loginService.changeActiveEntity(
            jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        )
    }
}