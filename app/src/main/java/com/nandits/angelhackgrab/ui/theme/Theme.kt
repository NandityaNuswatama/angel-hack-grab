package com.nandits.angelhackgrab.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColorScheme(
    primary = GrabPrimary,
    inversePrimary = GrabPrimaryVariant,
    secondary = GrabSecondary,
    background = GrabBackground,
    surface = GrabSurface,
    error = GrabError,
    onPrimary = GrabOnPrimary,
    onSecondary = GrabOnSecondary,
    onBackground = GrabOnBackground,
    onSurface = GrabOnSurface,
    onError = GrabOnError
)


@Composable
fun AngelHackGrabTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = Typography,
        content = content
    )
}