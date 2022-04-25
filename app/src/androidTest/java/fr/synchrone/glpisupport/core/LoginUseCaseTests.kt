package fr.synchrone.glpisupport.core

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.repository.TokenRepository
import fr.synchrone.glpisupport.domain.LoginUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Credentials
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LoginUseCaseTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var loginUseCase: LoginUseCase

    @Inject
    lateinit var tokenRepository: TokenRepository

    private val credentials = Credentials.basic(username = "glpi", password = "glpi")

    @Before
    fun init() {
        hiltRule.inject()
    }

    @InternalCoroutinesApi
    @Test
    fun test_if_loginAndStoreToken_works() {
        runBlocking {
            val result = loginUseCase.loginAndStoreToken(credentials = credentials)
            val expected = "6p9detlpht38knq8oi3eq0kocq"
            assert(result.sessionToken == expected)
            assert(tokenRepository.userToken.first() == expected)
        }
    }

    @InternalCoroutinesApi
    @Test
    fun test_if_isLoggedIn_works_when_not_logged() {
        runBlocking {
            assert(!loginUseCase.isLoggedIn())
        }
    }

    @InternalCoroutinesApi
    @Test
    fun test_if_isLoggedIn_works_when_logged() {
        runBlocking {
            loginUseCase.loginAndStoreToken(credentials = credentials)
            assert(loginUseCase.isLoggedIn())
        }
    }

    @InternalCoroutinesApi
    @Test
    fun test_if_logout_works_when_logged() {
        runBlocking {
            loginUseCase.loginAndStoreToken(credentials = credentials)
            loginUseCase.logout()
            assert(!loginUseCase.isLoggedIn())
        }
    }

    @InternalCoroutinesApi
    @Test
    fun test_if_logout_works_when_not_logged() {
        runBlocking {
            loginUseCase.logout()
            assert(!loginUseCase.isLoggedIn())
        }
    }

}