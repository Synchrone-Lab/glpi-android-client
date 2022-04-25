package fr.synchrone.glpisupport.presentation.utilities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.theme.AppFonts
import fr.synchrone.glpisupport.presentation.utilities.picker.GenericBottomPopup
import fr.synchrone.glpisupport.presentation.utilities.picker.GenericBottomPopupSize

@ExperimentalAnimationApi
@Composable
fun TextViewAlertDialog(screenHeight: Dp,
                        title: String,
                        description: String,
                        value: String,
                        label: @Composable () -> Unit,
                        onValueChange: (String) -> Unit,
                        onTapCancel: () -> Unit,
                        onTapConfirm: () -> Unit,
                        errorMessage: String?,
                        isVisible: Boolean) {

    val focusManager = LocalFocusManager.current

    val textfieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = MaterialTheme.colors.onSecondary,
        cursorColor = MaterialTheme.colors.onSecondary,
        focusedLabelColor = MaterialTheme.colors.onSecondary,
        backgroundColor = MaterialTheme.colors.secondary,
        textColor = MaterialTheme.colors.onSecondary,
        unfocusedLabelColor = MaterialTheme.colors.onSecondary
    )

    GenericBottomPopup(
        screenHeight = screenHeight,
        size = GenericBottomPopupSize(
            percentHeight = 0.5f,
            minHeight = 450.dp
        ),
        isVisible = isVisible,
        onTapOutside = { onTapCancel() },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colors.background)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        title,
                        fontFamily = AppFonts.openSans,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        description,
                        fontFamily = AppFonts.openSans,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TextField(
                        value = value,
                        label = label,
                        singleLine = true,
                        enabled = isVisible,
                        onValueChange = onValueChange,
                        colors = textfieldColors
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                onTapCancel()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                        ) {
                            Text("Annuler", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = Color.White)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                focusManager.clearFocus()
                                onTapConfirm()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                        ) {
                            Text("Confirmer", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    AnimatedVisibility(visible = errorMessage != null) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(errorMessage ?: "", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colors.error)
                        }
                    }
                }
            }
        }
    )

}