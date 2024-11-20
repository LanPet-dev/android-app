package com.example.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color



val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = WhiteColor.LIGHT,
)

data class CustomColorScheme(
    val buttonBackground: Color,
    val buttonText: Color,
    val textFieldBackground: Color,
    val textFieldText: Color,
    //TODO("Satoshi"): define another colors
)


val LightCustomColors = CustomColorScheme(
    buttonBackground = BlackColor.MEDIUM,
    buttonText = WhiteColor.LIGHT,
    textFieldBackground = WhiteColor.LIGHT,
    textFieldText = BlackColor.MEDIUM,
)

val DarkCustomColors = CustomColorScheme(
    buttonBackground = WhiteColor.LIGHT,
    buttonText = BlackColor.MEDIUM,
    textFieldBackground = BlackColor.MEDIUM,
    textFieldText = WhiteColor.LIGHT,
)


val LocalCustomColorScheme = staticCompositionLocalOf<CustomColorScheme> {
    throw NotImplementedError()
}

// extension fun
val MaterialTheme.customColorScheme: CustomColorScheme
    @Composable
    get() = LocalCustomColorScheme.current

