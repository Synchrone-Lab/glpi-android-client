package fr.synchrone.glpisupport.domain

import fr.synchrone.glpisupport.data.model.remote.search.SearchApi
import fr.synchrone.glpisupport.data.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend fun searchBy(serial: String, user: String): SearchApi =  searchRepository.searchBy(serial, user)
}