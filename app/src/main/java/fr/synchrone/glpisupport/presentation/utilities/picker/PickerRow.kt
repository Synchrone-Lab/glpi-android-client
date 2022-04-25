package fr.synchrone.glpisupport.presentation.utilities.picker

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.theme.AppFonts

@Composable
fun PickerRow(
    element: PickerElement,
    contentColor: Color,
    background: Color,
    modifier: Modifier
) {
    Column(
        modifier = Modifier.padding(8.dp, 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = modifier,
            elevation = 3.dp,
            shape = RoundedCornerShape(5.dp),
            color = background
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painterResource(id = element.iconResourceId),
                    "user icon",
                    colorFilter = ColorFilter.tint(contentColor),
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .width(40.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = element.name,
                    fontFamily = AppFonts.openSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}