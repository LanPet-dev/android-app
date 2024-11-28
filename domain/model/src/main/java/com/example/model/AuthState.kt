package com.example.model

sealed class AuthState {
    data class Success(
        val isSignedIn: Boolean,
        val socialAuthToken: SocialAuthToken?
    ) : AuthState()
    object Fail : AuthState()
    object Initial : AuthState()
    object Loading : AuthState()
}


