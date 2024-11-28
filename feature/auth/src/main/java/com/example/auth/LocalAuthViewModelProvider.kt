package com.example.auth

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.auth.viewmodel.AuthViewModel


val LocalAuthViewModel =
    staticCompositionLocalOf<AuthViewModel> { error("No AuthViewModel provided") }