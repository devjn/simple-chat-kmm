package com.jfayz.testchat.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue20 = Color(0xFF001E92)
val Blue80 = Color(0xFFB8C3FF)
val Teal200 = Color(0xFF03DAC5)
val Teal700 = Color(0xFF018786)

val AccentColor = Color(0xFFff1378)

private val LightColorScheme = lightColorScheme(
    primary = AccentColor,
    onPrimary = Color.White,
    primaryContainer = Teal700,
    onPrimaryContainer = Teal200
)
private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue20
)

@Composable
fun AppTheme(isDarkTheme: Boolean = false, content: @Composable () -> Unit) {
    val myColorScheme = when {
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = myColorScheme,
        content = content
    )
}
