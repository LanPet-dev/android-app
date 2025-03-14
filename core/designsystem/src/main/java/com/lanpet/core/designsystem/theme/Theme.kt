package com.lanpet.core.designsystem.theme

import android.R
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

val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80,
    )

val LightColorScheme =
    lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40,
        background = WhiteColor.LIGHT,
        surface = WhiteColor.White,
    )

data class CustomColorScheme(
    val heading: Color,
    val buttonBackground: Color,
    val buttonBackgroundDisabled: Color,
    val buttonText: Color,
    val buttonTextDisabled: Color,
    val textFieldBackground: Color,
    val textFieldText: Color,
    val selectedText: Color,
    val selectedContainer: Color,
    val unSelectedText: Color,
    val unSelectedContainer: Color,
    val errorText: Color,
    val confirmText: Color,
    val spacerLine: Color,
    val defaultIconColor: Color,
    val topBarTextButtonTextColor: Color,
    val dialogContentColor: Color,
    // TODO("Satoshi"): define another colors
)

val LightCustomColors =
    CustomColorScheme(
        buttonBackground = PrimaryColor.PRIMARY,
        buttonBackgroundDisabled = GrayColor.Gray100,
        buttonTextDisabled = GrayColor.Gray300,
        buttonText = WhiteColor.LIGHT,
        textFieldBackground = WhiteColor.LIGHT,
        textFieldText = BlackColor.MEDIUM,
        selectedText = PrimaryColor.PRIMARY,
        selectedContainer = VioletColor.Violet50,
        unSelectedText = GrayColor.LIGHT_MEDIUM,
        unSelectedContainer = WhiteColor.LIGHT,
        heading = BlackColor.MEDIUM,
        errorText = Color.Red,
        confirmText = Color.Blue,
        spacerLine = GrayColor.Gray50,
        defaultIconColor = BlackColor.MEDIUM,
        topBarTextButtonTextColor = GrayColor.Gray950,
        dialogContentColor = GrayColor.Gray700,
    )

val DarkCustomColors =
    CustomColorScheme(
        buttonBackground = WhiteColor.LIGHT,
        buttonBackgroundDisabled = GrayColor.Gray100,
        buttonText = BlackColor.MEDIUM,
        buttonTextDisabled = GrayColor.Gray300,
        textFieldBackground = BlackColor.MEDIUM,
        textFieldText = WhiteColor.MEDIUM,
        selectedText = PrimaryColor.PRIMARY,
        selectedContainer = VioletColor.Violet50,
        unSelectedText = GrayColor.LIGHT_MEDIUM,
        unSelectedContainer = BlackColor.MEDIUM,
        heading = WhiteColor.MEDIUM,
        errorText = Color.Red,
        confirmText = Color.Blue,
        spacerLine = GrayColor.Gray950,
        defaultIconColor = WhiteColor.White,
        topBarTextButtonTextColor = GrayColor.Gray50,
        dialogContentColor = GrayColor.Gray300,
    )

val LocalCustomColorScheme =
    staticCompositionLocalOf<CustomColorScheme> {
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
            if (darkTheme) {
                R.color.black
            } else {
                R.color.white
            },
        )
        onDispose {}
    }

    CompositionLocalProvider(
        LocalCustomColorScheme provides customColorScheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
