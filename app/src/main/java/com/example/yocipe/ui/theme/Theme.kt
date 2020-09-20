package com.example.yocipe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Orange700,
    primaryVariant = Orange900,
    onPrimary = Color.White,
    secondary = Orange700,
    secondaryVariant = Orange900,
    onSecondary = Color.White,
    error = Orange800
)

private val DarkThemeColors = darkColors(
    primary = Orange300,
    primaryVariant = Orange700,
    onPrimary = Color.Black,
    secondary = Orange300,
    onSecondary = Color.White,
    error = Orange200
)

@Composable
val Colors.snackbarAction: Color
    get() = if (isLight) Orange300 else Orange700

@Composable
fun YocipeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = YocipeTypography,
        shapes = YocipeShapes,
        content = content
    )
}