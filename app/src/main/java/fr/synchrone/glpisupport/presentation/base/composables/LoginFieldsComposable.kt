package fr.synchrone.glpisupport.presentation.base.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.theme.AppFonts

/**
 * Username and password fields, inside an elevated box. Used in MainActivityComposable when login is required.
 *
 * @see MainActivityComposable
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginFieldsComposable(
    modifier: Modifier = Modifier,
    usernameValue: String, passwordValue: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onClickConnectButton: () -> Unit,
    isLoading: Boolean,
    error: String?
) {

    val textfieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = MaterialTheme.colors.onSecondary,
        cursorColor = MaterialTheme.colors.onSecondary,
        focusedLabelColor = MaterialTheme.colors.onSecondary,
        backgroundColor = MaterialTheme.colors.secondary,
        textColor = MaterialTheme.colors.onSecondary,
        unfocusedLabelColor = MaterialTheme.colors.onSecondary
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .width(300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Connexion requise",
                fontFamily = AppFonts.openSans,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                color = MaterialTheme.colors.onPrimary
            )
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )
            TextField(
                value = usernameValue,
                label = { Text("Username") },
                enabled = !isLoading,
                singleLine = true,
                onValueChange = onUsernameChange,
                colors = textfieldColors
            )
            Spacer(
                modifier = Modifier
                    .height(50.dp)
            )
            TextField(
                value = passwordValue,
                label = { Text("Password") },
                enabled = !isLoading,
                singleLine = true,
                onValueChange = onPasswordChange,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = textfieldColors
            )
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )
            Button(
                onClick = onClickConnectButton,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier
                    .height(40.dp)
                    .animateContentSize(animationSpec = tween(easing = FastOutSlowInEasing))
            ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = MaterialTheme.colors.secondary, modifier = Modifier.size(20.dp))
                    } else {
                        Text("Connexion", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colors.onSecondary)
                    }
            }
            AnimatedVisibility(visible = error != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        error ?: "",
                        fontFamily = AppFonts.openSans,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.error
                    )
                }

            }

        }
    }


    
}