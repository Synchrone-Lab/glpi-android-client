package fr.synchrone.glpisupport.presentation.utilities.picker

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.presentation.utilities.SearchBarComposable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PickerComposable(
    screenHeight: Dp,
    isVisible: Boolean,
    elements: Map<PickerElement, List<PickerElement>>,
    onElementSelected: (PickerElement) -> Unit,
    onTapCancel: () -> Unit,
    background: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.onBackground,
    cellBackground: Color = MaterialTheme.colors.primary,
    cellContentColor: Color = MaterialTheme.colors.onPrimary
) {

    val roundedCornersRadius = 10.dp
    val topYOffset = 40.dp

    var searchValue by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    val yOffset by animateDpAsState(targetValue = if (isVisible) topYOffset else screenHeight, tween(durationMillis = 300, easing = FastOutSlowInEasing))
    val backgroundAlpha by animateFloatAsState(targetValue = if (isVisible) 0.4f else 0f, tween(durationMillis = 300, easing = FastOutSlowInEasing))

    fun onTapElement(elementId: Int) {
        val elementSelected = elements.values.flatten().first { it.id == elementId }
        onElementSelected(elementSelected)
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            searchValue = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(backgroundAlpha)
            .background(Color.Black),
    )

    Surface(
        shape = RoundedCornerShape(topStart = roundedCornersRadius, topEnd = roundedCornersRadius),
        color = background,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = yOffset)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(0.dp, 16.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Image(
                            painterResource(id = R.drawable.times),
                            "back icon",
                            colorFilter = ColorFilter.tint(contentColor),
                            modifier = Modifier
                                .fillMaxHeight(0.8f)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    onTapCancel()
                                },
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        SearchBarComposable(
                            background = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .weight(1f)
                                .fillMaxHeight(0.8f), value = searchValue
                        ) { searchValue = it }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
                elements.forEach { mapElement ->
                    val group = mapElement.key
                    val elementsOfGroup = mapElement.value.filter { it.name.contains(searchValue, ignoreCase = true) }

                    stickyHeader {
                        PickerHeader(
                            pickerElement = group,
                            background = background,
                            color = contentColor
                        )
                    }

                    items(elementsOfGroup) { element ->
                        PickerRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(75.dp)
                                .clickable(
                                    interactionSource = interactionSource,
                                    indication = null
                                ) {
                                    onTapElement(element.id)
                                },

                            element = element,
                            background = cellBackground,
                            contentColor = cellContentColor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(topYOffset))
        }
    }
}
