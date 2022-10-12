package com.alxnns1.fiomarket.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val Colours = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun FIOMarketTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = Colours,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}