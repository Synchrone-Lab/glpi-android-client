package fr.synchrone.glpisupport.data.service

import fr.synchrone.glpisupport.data.model.Token
import fr.synchrone.glpisupport.data.model.remote.user.myentities.ActiveEntitiesResponseApi
import fr.synchrone.glpisupport.data.model.remote.user.myentities.MyEntitiesResponseApi
import fr.synchrone.glpisupport.data.model.remote.user.myprofiles.ActiveProfileResponseApi
import fr.synchrone.glpisupport.data.model.remote.user.myprofiles.MyProfilesResponseApi
import okhttp3.RequestBody
import retrofit2.http.*

interface LoginService {

    @GET("initSession")
    suspend fun login(@Header("Authorization") baseAuth: String): Token

    @GET("getMyProfiles")
    suspend fun getMyProfiles(): MyProfilesResponseApi

    @GET("getMyEntities")
    suspend fun getMyEntities(@Query("is_recursive") isRecursive: Boolean): MyEntitiesResponseApi

    @GET("getActiveProfile")
    suspend fun getActiveProfile(): ActiveProfileResponseApi

    @GET("getActiveEntities")
    suspend fun getActiveEntities(): ActiveEntitiesResponseApi

    @POST("changeActiveProfile")
    suspend fun changeActiveProfile(@Body body: RequestBody)

    @POST("changeActiveEntities")
    suspend fun changeActiveEntity(@Body body: RequestBody): Boolean
}