package com.example.model

data class AuthState(
    val isSignedIn: Boolean,
    val socialAuthToken: SocialAuthToken?
)

