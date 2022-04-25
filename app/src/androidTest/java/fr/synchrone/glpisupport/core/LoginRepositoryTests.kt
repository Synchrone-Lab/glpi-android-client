package fr.synchrone.glpisupport.core

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.repository.LoginRepository
import fr.synchrone.glpisupport.data.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class LoginRepositoryTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var tokenRepository: TokenRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_login() {
        runBlocking {
            loginRepository.login(credentials = "ffssd")
            assert(true)
        }
    }
}