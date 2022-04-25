package fr.synchrone.glpisupport.data.service

import fr.synchrone.glpisupport.data.model.remote.search.MultiCriteriaSearchApi
import fr.synchrone.glpisupport.data.model.remote.search.SearchApi
import fr.synchrone.glpisupport.data.model.remote.search.SearchSpecificItemTypeApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {

    companion object {
        const val ID_SEARCH_OPTIONS = "2"
        const val ENTITY_NAME_SEARCH_OPTIONS = "80"
        const val PROFILE_NAME_SEARCH_OPTIONS = "4"
    }


    @GET("search/AllAssets?criteria[0][itemtype]=AllAssets&criteria[0][field]=5&criteria[0][searchtype]=equal&criteria[1][link]=OR&criteria[1][field]=70&criteria[1][searchtype]=contains")
    suspend fun searchBy(@Query("criteria[0][value]") serial: String, @Query("criteria[1][value]") user: String): SearchApi

    @GET("search/profile_user?forcedisplay[0]=2&criteria[0][field]=${ENTITY_NAME_SEARCH_OPTIONS}&criteria[0][searchtype]=equal&criteria[1][field]=${PROFILE_NAME_SEARCH_OPTIONS}&criteria[1][searchtype]=equal")
    suspend fun searchProfileUserByProfileIdAndEntityName(@Query("criteria[0][value]") entityName: String, @Query("criteria[1][value]") profileName: String): MultiCriteriaSearchApi

    @GET("search/{itemType}?forcedisplay[0]=2&criteria[0][field]=5&criteria[0][searchtype]=equal")
    suspend fun searchSpecificItemBy(@Path("itemType") itemType : String, @Query("criteria[0][value]") serial: String): SearchSpecificItemTypeApi
}