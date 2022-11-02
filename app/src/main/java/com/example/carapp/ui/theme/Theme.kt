package com.example.carapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Gray,
    primaryVariant = Gray,
    secondary = Orange,
    background = MediumGray,
    onBackground = TextWhite,
    surface = LightGray,
    onSurface = TextWhite,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = White,
    secondary = Orange,
    background = White,
    onBackground = DarkGray,
    surface = White,
    onSurface = DarkGray,
    onPrimary = DarkGray,
    onSecondary = White,
)

@Composable
fun CarAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    CompositionLocalProvider(
        LocalSpacing provides Dimensions(),
        LocalFontSize provides FontSize()
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            content = content,
            shapes = Shapes,
        )
    }
}