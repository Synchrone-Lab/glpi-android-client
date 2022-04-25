package fr.synchrone.glpisupport.presentation.theme

import android.annotation.SuppressLint
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

/**
 * Collection of themes used in the application.
 */
object AppThemes {

    @SuppressLint("ConflictingOnColor")
    val lightTheme = lightColors(
        primary = AppColors.darkerBlueColor,
        secondary = AppColors.blueColor,
        primaryVariant = Color(0xFFF3F3F3),
        error = AppColors.pink,
        background = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.White
    )

    val darkTheme = darkColors(
        primary = AppColors.darkThemeMiddlePurple,
        secondary = AppColors.darkThemeLightPurple,
        primaryVariant = AppColors.darkThemeMiddlePurple,
        background = AppColors.darkThemeDarkPurple,
        error = AppColors.pink,
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onBackground = Color.White
    )

}