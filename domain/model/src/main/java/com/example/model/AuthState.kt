package com.example.model

sealed class AuthState {
    data class Success(
        val socialAuthToken: SocialAuthToken?,
        val userProfile: UserProfile?
    ) : AuthState()

    data object Fail : AuthState()
    data object Initial : AuthState()
    data object Loading : AuthState()
}


