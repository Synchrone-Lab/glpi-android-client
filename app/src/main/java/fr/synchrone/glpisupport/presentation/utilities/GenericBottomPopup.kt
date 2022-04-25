package fr.synchrone.glpisupport.presentation.utilities.picker

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min

data class GenericBottomPopupSize(
    private val percentHeight: Float = 1f,
    private val minHeight: Dp? = null,
    private val maxHeight: Dp? = null,
    private val height: Dp? = null
) {

    fun getPercentHeight(screenHeight: Dp): Float {
        return (screenHeight - getYOffset(screenHeight)) / screenHeight
    }


    fun getYOffset(screenHeight: Dp): Dp {
        val minHeightOrZero = this.minHeight ?: 0.dp
        val minHeight = if (minHeightOrZero > screenHeight) 0.dp else minHeightOrZero
        val maxHeightOrScreenHeight = this.maxHeight ?: screenHeight
        val maxHeight = min(maxHeightOrScreenHeight, screenHeight)
        val heightRef = this.height

        return if (heightRef != null) {
            screenHeight - heightRef
        } else {
            val computedOffset = screenHeight * (1 - percentHeight)
            val maxOffset = screenHeight - minHeight
            val minOffset = screenHeight - maxHeight
            min(max(minOffset, computedOffset), maxOffset)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GenericBottomPopup(
    screenHeight: Dp,
    isVisible: Boolean,
    size: GenericBottomPopupSize,
    onTapOutside: (() -> Unit)? = null,
    hideDarkBackground: Boolean = false,
    content: @Composable () -> Unit,
    background: Color = MaterialTheme.colors.background
) {

    val roundedCornersRadius = 10.dp
    val topYOffset = size.getYOffset(screenHeight = screenHeight)

    val yOffset by animateDpAsState(targetValue = if (isVisible) topYOffset else screenHeight, tween(durationMillis = 300, easing = FastOutSlowInEasing))
    val backgroundAlpha by animateFloatAsState(targetValue = if (isVisible && !hideDarkBackground) 0.4f else 0f, tween(durationMillis = 300, easing = FastOutSlowInEasing))

    val defaultInputBoxModifier = Modifier.fillMaxSize().alpha(0f)
    var inputBoxModifier by remember { mutableStateOf(defaultInputBoxModifier) }

    LaunchedEffect(isVisible) {
        inputBoxModifier = if (isVisible && onTapOutside != null) {
            defaultInputBoxModifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { onTapOutside() })
                }
        } else {
            defaultInputBoxModifier
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(backgroundAlpha)
            .background(Color.Black)
    )

    Box(
        modifier = inputBoxModifier,
    )

    Surface(
        shape = RoundedCornerShape(topStart = roundedCornersRadius, topEnd = roundedCornersRadius),
        color = background,
        modifier = Modifier
            .fillMaxHeight(size.getPercentHeight(screenHeight))
            .fillMaxWidth()
            .offset(y = yOffset)
    ) {
        Column(
            modifier = Modifier
                .height(screenHeight * size.getPercentHeight(screenHeight))
                .fillMaxWidth()
        ) {
            content()
        }
    }
}
