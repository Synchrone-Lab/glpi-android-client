package fr.synchrone.glpisupport.data.repository


import fr.synchrone.glpisupport.data.model.remote.search.SearchApi
import fr.synchrone.glpisupport.data.model.remote.search.SearchItemApi
import fr.synchrone.glpisupport.data.service.SearchService
import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchService: SearchService) {
    suspend fun searchBy(serial : String, user: String) : SearchApi {
        val searchRack = searchService.searchSpecificItemBy(serial = serial,itemType =  "rack")
        val searchBy = searchService.searchBy(serial = serial,user =  user)
        return SearchApi(
            searchBy.searchItems + searchRack.searchItems.map {
                                                              SearchItemApi(it.id,"rack")
            },
            searchBy.searchItems.size + searchRack.searchItems.size )
    }
}