package fr.synchrone.glpisupport.presentation.utilities

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.theme.AppFonts

@Composable
fun BooleanCheckField(modifier: Modifier,
                    isChecked: Boolean,
                    label: String,
                    enabled: Boolean = true,
                    onCheckedChanged: (Boolean) -> Unit
) {

    val imageAlpha by animateFloatAsState(targetValue = if (enabled) 1f else 0.5f)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Text(
                label,
                fontFamily = AppFonts.openSans,
                color = if (enabled) MaterialTheme.colors.onPrimary else Color.Gray,
                fontSize = 16.sp,
                maxLines = 2,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .width(40.dp)
                .alpha(imageAlpha),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChanged,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.onPrimary,
                    uncheckedColor = MaterialTheme.colors.onPrimary,
                    checkmarkColor = MaterialTheme.colors.primary
                ),
                modifier = Modifier.semantics { contentDescription = "$label checkbox" }
            )
        }

    }
}