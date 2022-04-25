package fr.synchrone.glpisupport.presentation.utilities

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import fr.synchrone.glpisupport.R

@Composable
fun PickerTextField(modifier: Modifier,
                    value: String,
                    label: String,
                    backgroundColor: Color = MaterialTheme.colors.background,
                    onBackgroundColor: Color = MaterialTheme.colors.onBackground,
                    isDeleteButtonVisible: Boolean = false,
                    enabled: Boolean = true,
                    onClick: () -> Unit,
                    onDelete: () -> Unit = {}) {

    val interactionSource = remember { MutableInteractionSource() }

    val imageAlpha by animateFloatAsState(targetValue = if (enabled) 1f else 0.5f)

    val colors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = onBackgroundColor,
        cursorColor = onBackgroundColor,
        focusedLabelColor = onBackgroundColor,
        backgroundColor = backgroundColor,
        textColor = onBackgroundColor,
        unfocusedLabelColor = onBackgroundColor
    )

    val disabledColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Gray,
        cursorColor = Color.Gray,
        focusedLabelColor = Color.Gray
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        Box(modifier = Modifier.weight(1f)) {
            TextField(
                value = value,
                onValueChange = {},
                label = { Text(label) },
                enabled = enabled,
                singleLine = true,
                colors = if (enabled) colors else disabledColors
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .alpha(0f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = if (enabled) onClick else {
                            {}
                        }
                    ),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .width(30.dp)
                .alpha(imageAlpha),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isDeleteButtonVisible) {
                Image(
                    painterResource(id = R.drawable.times),
                    "delete icon",
                    colorFilter = ColorFilter.tint(if (enabled) backgroundColor else Color.Gray),
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                        interactionSource = interactionSource,
                    indication = null,
                    onClick = if (isDeleteButtonVisible) onDelete else onClick
                        )
                        .semantics { contentDescription = "$label delete icon" },
                contentScale = ContentScale.Fit
                )
            } else {
                Image(
                    painterResource(id = R.drawable.chevron_down),
                    "chevron down icon",
                    colorFilter = ColorFilter.tint(if (enabled) backgroundColor else Color.Gray),
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(imageAlpha),
                    contentScale = ContentScale.Fit
                )
            }
        }

    }
}