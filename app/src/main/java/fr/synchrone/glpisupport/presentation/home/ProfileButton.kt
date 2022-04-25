package fr.synchrone.glpisupport.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.theme.AppFonts

/**
 * Button used in home screen to open item scanner or search with serial dialog.
 *
 * @see HomeComposable
 */
@Composable
fun ProfileButton(
    profile: String,
    entity: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier
) {

    val roundedCornersRadius = 10.dp

    Surface(
        shape = RoundedCornerShape(roundedCornersRadius),
        color = MaterialTheme.colors.secondary,
        modifier = modifier
            .fillMaxSize()

            .pointerInput(Unit) {
                detectTapGestures {
                    if (enabled)
                        onClick()
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                profile,
                modifier = Modifier
                    .weight(1f),
                fontFamily = AppFonts.openSans,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSecondary
            )

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp)
                    .background(MaterialTheme.colors.background)
            )

            Text(
                entity,
                modifier = Modifier
                    .weight(1f),
                fontFamily = AppFonts.openSans,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSecondary
            )

        }
    }
}