package com.lanpet.core.auth

import androidx.compose.runtime.staticCompositionLocalOf
import com.lanpet.core.auth.viewmodel.AuthViewModel


val LocalAuthViewModel =
    staticCompositionLocalOf<AuthViewModel> { error("No AuthViewModel provided") }