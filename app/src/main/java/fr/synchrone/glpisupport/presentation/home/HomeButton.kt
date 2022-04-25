package fr.synchrone.glpisupport.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale

/**
 * Button used in home screen to open item scanner or search with serial dialog.
 *
 * @see HomeComposable
 */
@Composable
fun HomeButton(
    title: String,
    drawable: Painter,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    if (enabled)
                        onClick()
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            drawable,
            title + " " + if(enabled) "enabled" else "disabled",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onPrimary),
            modifier = Modifier
                .fillMaxSize(0.75f)
                .alpha(if (enabled) 1f else 0.4f),
            contentScale = ContentScale.Fit
        )
    }
}