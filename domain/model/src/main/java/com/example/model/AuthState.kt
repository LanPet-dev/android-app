package com.example.model

import androidx.annotation.Keep

@Keep
sealed class AuthState {
    @Keep
    data class Success(
        val socialAuthToken: SocialAuthToken?,
        val userProfile: UserProfile?
    ) : AuthState()

    @Keep
    data object Fail : AuthState()
    @Keep
    data object Initial : AuthState()
    @Keep
    data object Loading : AuthState()
}


