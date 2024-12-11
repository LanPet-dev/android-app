package com.example.model

import com.example.model.account.Account
import com.example.model.account.AccountToken

sealed class AuthState {
    data class Success(
        val socialAuthToken: SocialAuthToken?,
        val account: Account?
    ) : AuthState()

    data object Fail : AuthState()
    data object Initial : AuthState()
    data class Loading(
        val socialAuthToken: SocialAuthToken?
    ) : AuthState()
}