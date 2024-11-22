package com.example.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


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
    textFieldText = WhiteColor.MEDIUM,
)


val LocalCustomColorScheme = staticCompositionLocalOf<CustomColorScheme> {
    LightCustomColors
}

val MaterialTheme.customColorScheme: CustomColorScheme
    @Composable
    get() = LocalCustomColorScheme.current


@Composable
fun LanPetAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val customColorScheme = if (darkTheme) DarkCustomColors else LightCustomColors

    val window = (LocalContext.current as? Activity)?.window

    // 테마에 따라 window 배경색 설정
    DisposableEffect(darkTheme) {
        window?.setBackgroundDrawableResource(
            if (darkTheme) android.R.color.black
            else android.R.color.white
        )
        onDispose {}
    }

    CompositionLocalProvider(
        LocalCustomColorScheme provides customColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

}
