package fr.synchrone.glpisupport.presentation.utilities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.presentation.theme.AppFonts

@Composable
fun SearchBarComposable(
    background: Color,
    contentColor: Color,
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {

    var isPlaceholderVisible by remember { mutableStateOf(true) }

    val placeholder = "Rechercher"
    val fontFamily = AppFonts.openSans
    val fontWeight = FontWeight.Normal
    val fontSize = 14.sp

    Row(
        modifier = modifier.background(background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painterResource(id = R.drawable.search),
            "search icon",
            colorFilter = ColorFilter.tint(contentColor),
            modifier = Modifier
                .fillMaxHeight(0.4f),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(modifier = Modifier.weight(1f)) {
            BasicTextField(
                value = value,
                onValueChange = {
                    isPlaceholderVisible = it == ""
                    onValueChange(it)
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = contentColor,
                    fontFamily = fontFamily,
                    fontWeight = fontWeight,
                    fontSize = fontSize
                )
            )
            if(isPlaceholderVisible) {
                Text(
                    placeholder,
                    color = Color.Gray,
                    fontFamily = fontFamily,
                    fontWeight = fontWeight,
                    fontSize = fontSize
                )
            }
        }
    }

}