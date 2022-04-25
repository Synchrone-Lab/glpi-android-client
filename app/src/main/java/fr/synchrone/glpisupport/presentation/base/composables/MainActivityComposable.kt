package fr.synchrone.glpisupport.presentation.base.composables

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.core.ViewState
import fr.synchrone.glpisupport.presentation.base.LoginViewModel
import fr.synchrone.glpisupport.presentation.utilities.WavesComposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Credentials

/**
 * Show app logo then login fields (or redirect to home if already logged).
 */
@Composable
fun MainActivityComposable(navController: NavController, screenHeight: Dp, loginViewModel: LoginViewModel) {

    val offsetsDuration = 1000

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    //State

    var isTitleAppeared by remember { mutableStateOf(false) }
    var isLogoAppeared by remember { mutableStateOf(false) }
    var areWavesTranslated by remember { mutableStateOf(false) }
    var areLoginFieldsAppears by remember { mutableStateOf(false) }
    var isConnectLoading by remember { mutableStateOf(false) }

    var loginSuccessful by remember { mutableStateOf(false) }
    var loginFailed by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }

    val loginCoroutineScope = rememberCoroutineScope()

    //Animations

    val logoAlpha by animateFloatAsState(targetValue = if (isLogoAppeared && !loginSuccessful) 1f else 0f)
    val wavesXOffset by animateDpAsState(targetValue = if (areWavesTranslated && !loginSuccessful) (-500).dp else 0.dp, tween(offsetsDuration, easing = FastOutSlowInEasing))
    val wavesYOffset by animateDpAsState(targetValue = if (!areWavesTranslated || loginSuccessful) screenHeight else 50.dp, tween(offsetsDuration, easing = FastOutSlowInEasing))
    val loginFieldsAlpha by animateFloatAsState(targetValue = if (areLoginFieldsAppears && !loginSuccessful) 1f else 0f)

    fun goToHome() {
        navController.navigate("home")
    }


    fun login() {
        isConnectLoading = true
        loginCoroutineScope.launch {
            loginViewModel.login(Credentials.basic(username = username, password = password)).collect {
                when (it) {
                    is ViewState.Success -> {
                        loginSuccessful = true
                        delay(offsetsDuration.toLong())
                        goToHome()
                    }
                    is ViewState.Loading -> {
                        isConnectLoading = true
                        loginFailed = false
                        errorText = null
                    }
                    is ViewState.Exception -> {
                        isConnectLoading = false
                        loginFailed = true
                        errorText = context.getString(R.string.login_failed)
                        delay(3000L)
                        loginFailed = false
                        errorText = null
                    }
                }
            }
        }
    }

    LaunchedEffect("") {
        delay(500L)
        isLogoAppeared = true
        delay(200L)
        isTitleAppeared = true
        val isUserLogged = loginViewModel.isLoggedIn()
        delay(1500L)
        if (isUserLogged) {
            loginSuccessful = true
            delay(300L)
            goToHome()
        } else {
            areWavesTranslated = true
            delay((offsetsDuration * 0.5).toLong())
            areLoginFieldsAppears = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.logo_glpi_white),
            "logo glpi",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            modifier = Modifier
                .width(150.dp)
                .alpha(logoAlpha),
            contentScale = ContentScale.Fit
        )
    }

    WavesComposable(wavesXOffset = wavesXOffset, wavesYOffset = wavesYOffset)
    
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painterResource(id = R.drawable.logo_glpi_white),
            "logo glpi",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            modifier = Modifier
                .width(50.dp)
                .alpha(loginFieldsAlpha),
            contentScale = ContentScale.Fit
        )
    }

    ConstraintLayout(modifier = Modifier
        .alpha(loginFieldsAlpha)
        .fillMaxSize()
    ) {
        val (loginFields) = createRefs()

        LoginFieldsComposable(
            usernameValue = username,
            passwordValue = password,
            onUsernameChange = { username = it },
            onPasswordChange = { password = it },
            onClickConnectButton = {
                focusManager.clearFocus()
                login()
            },
            isLoading = isConnectLoading,
            modifier = Modifier.constrainAs(loginFields) {
                centerTo(parent)
            },
            error = errorText
        )
    }

}