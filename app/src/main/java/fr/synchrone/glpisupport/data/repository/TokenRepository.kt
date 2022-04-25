package fr.synchrone.glpisupport.data.repository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import fr.synchrone.glpisupport.data.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenRepository(context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "token"
    )

    val userToken: Flow<String> = dataStore.data
        .map { pref ->
            pref[USER_TOKEN] ?: ""
        }

    suspend fun isLoggedIn() : Boolean {
        return userToken.first().isNotEmpty()
    }

    suspend fun storeToken(token: Token) = dataStore.edit { pref ->
        pref[USER_TOKEN] = token.sessionToken
    }

    suspend fun removeToken() = dataStore.edit { pref ->
        pref.remove(USER_TOKEN)
    }

    companion object PreferencesKeys {
        val USER_TOKEN = preferencesKey<String>("user_token")
    }
}