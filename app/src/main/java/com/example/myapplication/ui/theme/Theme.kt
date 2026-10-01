package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Mantemos apenas um esquema de cores claro, já que o mockup do app
// (telas de login, principal, programado e novo lembrete) foi todo
// desenhado em tema claro.
private val LightColorScheme = lightColorScheme(
    primary = Terracota,
    onPrimary = Color.White,
    secondary = AzulMarinho,
    onSecondary = Color.White,
    background = CremeFundo,
    onBackground = AzulMarinho,
    surface = BrancoCard,
    onSurface = AzulMarinho,
    error = VermelhoCartao,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
