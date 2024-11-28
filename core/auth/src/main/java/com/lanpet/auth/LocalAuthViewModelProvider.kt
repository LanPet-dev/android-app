package com.lanpet.auth

import androidx.compose.runtime.staticCompositionLocalOf
import com.lanpet.auth.viewmodel.AuthViewModel


val LocalAuthViewModel =
    staticCompositionLocalOf<AuthViewModel> { error("No AuthViewModel provided") }