package fr.synchrone.glpisupport.presentation.utilities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.DatePicker
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
import androidx.compose.runtime.*
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
import androidx.compose.ui.viewinterop.AndroidView
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.presentation.theme.AppFonts
import fr.synchrone.glpisupport.presentation.utilities.functions.DateUtilities
import java.util.*

@SuppressLint("InflateParams")
@Composable
fun DatePicker(screenHeight: Dp,
               title: String,
               initialDate: Date?,
               onTapCancel: () -> Unit,
               onTapConfirm: (Date) -> Unit,
               isVisible: Boolean) {

    var selectedDate by remember { mutableStateOf(initialDate ?: Calendar.getInstance().time) }

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
            AndroidView(
                modifier = Modifier
                    .height(200.dp)
                    .width(300.dp),
                factory = { context ->
                    (LayoutInflater.from(context).inflate(R.layout.date_picker, null) as DatePicker).apply {
                        this.tag = "DatePicker" //for ui tests
                        this.calendarViewShown = false
                        this.init(
                            DateUtilities.getCalendarComponentFromDate(date = selectedDate, component = Calendar.YEAR),
                            DateUtilities.getCalendarComponentFromDate(date = selectedDate, component = Calendar.MONTH),
                            DateUtilities.getCalendarComponentFromDate(date = selectedDate, component = Calendar.DAY_OF_MONTH)
                        ) { _, year, monthOfYear, dayOfMonth ->
                            selectedDate = DateUtilities.getDateFromCalendarComponents(
                                year = year,
                                month = monthOfYear,
                                day = dayOfMonth
                            )
                        }
                    }
                },
                update = {
                    val initialDateOrNow = initialDate ?: Calendar.getInstance().time
                    it.updateDate(
                        DateUtilities.getCalendarComponentFromDate(date = initialDateOrNow, component = Calendar.YEAR),
                        DateUtilities.getCalendarComponentFromDate(date = initialDateOrNow, component = Calendar.MONTH),
                        DateUtilities.getCalendarComponentFromDate(date = initialDateOrNow, component = Calendar.DAY_OF_MONTH)
                    )
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                      onTapCancel()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                    enabled = isVisible
                ) {
                    Text("Annuler", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                      onTapConfirm(selectedDate)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text("Confirmer", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }
    }
}
