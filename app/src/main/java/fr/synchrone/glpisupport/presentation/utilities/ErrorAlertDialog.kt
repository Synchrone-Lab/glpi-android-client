package fr.synchrone.glpisupport.presentation.utilities

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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

@Composable
fun ErrorAlertDialog(screenHeight: Dp, title: String, description: String, onTapConfirm: () -> Unit, isVisible: Boolean) {

    val interactionSource = remember { MutableInteractionSource() }

    val backgroundAlpha by animateFloatAsState(targetValue = if (isVisible) 0.5f else 0f)
    val mainContentAlpha by animateFloatAsState(targetValue = if (isVisible) 1f else 0f, tween(delayMillis = 0, easing = FastOutSlowInEasing))
    val mainContentYOffset by animateDpAsState(targetValue = if (isVisible) 0.dp else 15.dp, tween(delayMillis = 0, easing = FastOutSlowInEasing))

    Box(modifier = Modifier
        .alpha(backgroundAlpha)
        .fillMaxSize()
        .offset(y = if(isVisible) 0.dp else screenHeight)
        .background(Color.Black)
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) { }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = if(isVisible) 0.dp else screenHeight),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .clip(RoundedCornerShape(5.dp))
                .alpha(mainContentAlpha)
                .offset(y = mainContentYOffset)
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontFamily = AppFonts.openSans, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(32.dp))
            Text(description, fontFamily = AppFonts.openSans, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onTapConfirm,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
                    modifier = Modifier.height(40.dp).animateContentSize(animationSpec = tween(easing = FastOutSlowInEasing))
                ) {
                    Text("OK", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }
    }

}