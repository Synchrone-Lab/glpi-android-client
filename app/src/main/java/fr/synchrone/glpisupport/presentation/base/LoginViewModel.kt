package fr.synchrone.glpisupport.presentation.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.synchrone.glpisupport.core.ViewState
import fr.synchrone.glpisupport.data.model.Token
import fr.synchrone.glpisupport.domain.LoginUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * View model used by MainActivityComposable.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    ViewModel() {

    suspend fun isLoggedIn(): Boolean = loginUseCase.isLoggedIn()

    /**
     * Initiate GLPI session in API.
     */
    fun login(credentials: String): Flow<ViewState<Token?>> {
        return flow {
            emit(ViewState.Loading())
            try {
                val token = loginUseCase.loginAndStoreToken(credentials)
                emit(ViewState.Success(token))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ViewState.Exception(e))
            }
        }
    }
}