package fr.synchrone.glpisupport.presentation.base.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.synchrone.glpisupport.presentation.base.LoginViewModel
import fr.synchrone.glpisupport.presentation.home.HomeComposable
import fr.synchrone.glpisupport.presentation.home.HomeViewModel
import fr.synchrone.glpisupport.presentation.items.ItemComposable
import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModelFactory

/**
 * Application's navigation controller
 */
@ExperimentalAnimationApi
@androidx.camera.core.ExperimentalGetImage
@Composable
fun MainActivityNavController(
    screenHeight: Dp,
    itemViewModelFactory: ItemViewModelFactory
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            val loginViewModel: LoginViewModel = hiltViewModel(it)
            MainActivityComposable(screenHeight = screenHeight, loginViewModel = loginViewModel, navController = navController)
        }
        composable("home") {
            val homeViewModel: HomeViewModel = hiltViewModel(it)
            HomeComposable(screenHeight = screenHeight, homeViewModel = homeViewModel, navController = navController)
        }
        composable(
            "item/{type}/{id}?isItemCreation={isItemCreation}&overrideSerial={overrideSerial}",
            arguments = listOf(
                navArgument("type") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType },
                navArgument("isItemCreation") { type = NavType.BoolType },
                navArgument("overrideSerial") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            val itemType = it.arguments!!.getString("type")!!
            val itemId = it.arguments!!.getInt("id")
            val isItemCreation = it.arguments!!.getBoolean("isItemCreation")
            val overrideSerial = it.arguments!!.getString("overrideSerial")
            val itemViewModel = itemViewModelFactory.create(itemType = itemType, itemId = itemId, isItemCreation = isItemCreation, overrideSerial = overrideSerial)

            ItemComposable(navController = navController, screenHeight = screenHeight, itemViewModel = itemViewModel)
        }
    }
}