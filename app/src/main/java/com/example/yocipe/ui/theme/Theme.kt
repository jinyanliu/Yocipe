package com.example.yocipe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.lightColors

private val LightThemeColors = lightColors(
        primary = Red700,
        primaryVariant = Red900,
        onPrimary = Color.White,
        secondary = Red700,
        secondaryVariant = Red900,
        onSecondary = Color.White,
        error = Red800
)

private val DarkThemeColors = darkColors(
        primary = Red300,
        primaryVariant = Red700,
        onPrimary = Color.Black,
        secondary = Red300,
        onSecondary = Color.White,
        error = Red200
)

@Composable
val Colors.snackbarAction: Color
    get() = if (isLight) Red300 else Red700

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