package com.avtomatorgovli.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryOrange,
    tertiary = SuccessGreen
)

private val DarkColors = darkColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryOrange,
    tertiary = SuccessGreen
)

@Composable
fun RetailTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = RetailTypography,
        content = content
    )
}
