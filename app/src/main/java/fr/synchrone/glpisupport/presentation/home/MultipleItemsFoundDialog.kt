package fr.synchrone.glpisupport.presentation.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.theme.AppFonts
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerElement
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerRow

/**
 * Dialog shown in home screen when user's search give multiple results.
 *
 * @see HomeComposable
 */
@Composable
fun MultipleItemsFoundDialog(screenHeight: Dp, pickerRowHeight: Dp, elementsFound: List<PickerElement>, onTapConfirm: (PickerElement) -> Unit, onTapCancel: () -> Unit, isVisible: Boolean) {

    val interactionSource = remember { MutableInteractionSource() }

    val backgroundAlpha by animateFloatAsState(targetValue = if (isVisible) 0.5f else 0f)
    val mainContentAlpha by animateFloatAsState(targetValue = if (isVisible) 1f else 0f, tween(delayMillis = 0, easing = FastOutSlowInEasing))
    val mainContentYOffset by animateDpAsState(targetValue = if (isVisible) 0.dp else 15.dp, tween(delayMillis = 0, easing = FastOutSlowInEasing))

    val textfieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = MaterialTheme.colors.onSecondary,
        cursorColor = MaterialTheme.colors.onSecondary,
        focusedLabelColor = MaterialTheme.colors.onSecondary,
        backgroundColor = MaterialTheme.colors.secondary,
        textColor = MaterialTheme.colors.onSecondary,
        unfocusedLabelColor = MaterialTheme.colors.onSecondary
    )

    Box(modifier = Modifier
        .alpha(backgroundAlpha)
        .fillMaxSize()
        .offset(y = if (isVisible) 0.dp else screenHeight)
        .background(Color.Black)
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) { }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = if (isVisible) 0.dp else screenHeight),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(5.dp))
                .alpha(mainContentAlpha)
                .offset(y = mainContentYOffset)
                .background(MaterialTheme.colors.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Plusieurs résultats trouvés",
                fontFamily = AppFonts.openSans,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Plusieurs appareils ont été trouvés, lequel choisir ?",
                fontFamily = AppFonts.openSans,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn(
                modifier = Modifier.height(220.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(0.dp, 16.dp)
            ) {
                items(elementsFound) { element ->
                    PickerRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(pickerRowHeight)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                onTapConfirm(element)
                            },

                        element = element,
                        background = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { onTapCancel() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                enabled = isVisible
            ) {
                Text("Annuler", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }

}