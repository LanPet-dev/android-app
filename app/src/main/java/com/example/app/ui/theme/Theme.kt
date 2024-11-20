package com.example.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.example.designsystem.theme.*

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

