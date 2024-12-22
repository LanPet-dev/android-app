package com.lanpet.core.auth

import androidx.compose.runtime.staticCompositionLocalOf

@Suppress("ComposeCompositionLocalUsage")
val LocalAuthManager =
    staticCompositionLocalOf<AuthManager> { error("No AuthManager provided") }
