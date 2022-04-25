package fr.synchrone.glpisupport.presentation.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.AndroidEntryPoint
import fr.synchrone.glpisupport.presentation.base.composables.MainActivityNavController
import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModelFactory
import fr.synchrone.glpisupport.presentation.theme.AppThemes
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import javax.inject.Inject

/**
 * The only activity in the app.
 */
@androidx.camera.core.ExperimentalGetImage
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var itemViewModelFactory: ItemViewModelFactory

    private val isKeyboardOpenLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val theme = when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> AppThemes.lightTheme // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> AppThemes.darkTheme // Night mode is active, we're using dark theme
            else -> AppThemes.lightTheme
        }

        setContent {
            val isKeyboardOpen by (this@MainActivity).isKeyboardOpenLiveData.observeAsState(initial = false)
            val yOffset by animateDpAsState(targetValue = if (isKeyboardOpen) -100.dp else 0.dp)

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(
                        y = yOffset
                    )
                    .background(theme.background)
            ) {

                println("Max height : ${this.maxHeight}")

                MaterialTheme(
                    colors = theme
                ) {
                    MainActivityNavController(
                        screenHeight = maxHeight,
                        itemViewModelFactory = itemViewModelFactory
                    )
                }
            }


        }

        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        KeyboardVisibilityEvent.setEventListener(
            this
        ) { isOpen ->
            this.isKeyboardOpenLiveData.value = isOpen
        }
    }
}