package com.lanpet.domain.model

import com.lanpet.domain.model.account.Account

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