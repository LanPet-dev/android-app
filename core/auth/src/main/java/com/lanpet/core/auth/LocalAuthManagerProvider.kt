package com.lanpet.core.auth

import androidx.compose.runtime.staticCompositionLocalOf


val LocalAuthManager =
    staticCompositionLocalOf<AuthManager> { error("No AuthViewModel provided") }