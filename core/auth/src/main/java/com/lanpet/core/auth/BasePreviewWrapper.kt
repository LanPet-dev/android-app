package com.lanpet.core.auth

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.manager.AuthStateHolder

/**
 * Preview Composable 을 만들때 사용되는 Wrapper Composable 입니다.
 * LocalAuthManager 가 기본으로 제공되어 Preview render 오류를 방지합니다.
 * 또한 LanPetAppTheme 와 Surface 를 제공하여 기본적인 Theme 을 제공합니다.
 */
@Composable
fun BasePreviewWrapper(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalAuthManager provides AuthManager(
            authStateHolder = AuthStateHolder(),
        ),
    ) {
        LanPetAppTheme {
            Surface {
                content()
            }
        }
    }
}
