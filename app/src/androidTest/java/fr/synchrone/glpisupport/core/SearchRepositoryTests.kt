package fr.synchrone.glpisupport.core

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.synchrone.glpisupport.data.repository.SearchRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class SearchRepositoryTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var searchRepository: SearchRepository

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_searchBy_with_computer_serial() {
        runBlocking {
            val result = searchRepository.searchBy(serial = "4", user = "4")

            assert(result.searchItems.size == 4)
            assert(result.searchItems[0].id == 2)
            assert(result.searchItems[0].itemtype.equals("computer", ignoreCase = true))
            assert(result.searchItems[1].id == 2)
            assert(result.searchItems[1].itemtype.equals("monitor", ignoreCase = true))
            assert(result.searchItems[2].id == 1)
            assert(result.searchItems[2].itemtype.equals("phone", ignoreCase = true))
            assert(result.searchItems[3].id == 1)
            assert(result.searchItems[3].itemtype.equals("printer", ignoreCase = true))
        }
    }
}