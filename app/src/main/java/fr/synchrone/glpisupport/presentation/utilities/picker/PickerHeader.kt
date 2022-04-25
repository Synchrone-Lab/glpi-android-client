package fr.synchrone.glpisupport.presentation.utilities.picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.theme.AppFonts

@Composable
fun PickerHeader(
    pickerElement: PickerElement,
    background: Color,
    color: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth().background(background).padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            pickerElement.name,
            fontFamily = AppFonts.openSans,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = color
        )
    }

}