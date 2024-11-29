package com.lanpet.core.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AuthState
import com.example.usecase.GetCognitoSocialAuthTokenUseCase
import com.lanpet.core.auth.AuthStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase,
    private val authStateHolder: AuthStateHolder
) : ViewModel() {

    val authState = authStateHolder.authState

    fun handleAuthCode(code: String) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getCognitoSocialAuthTokenUseCase(code)
            }

            result.onSuccess { socialAuthToken ->
                authStateHolder.updateState(
                    AuthState.Success(
                        socialAuthToken = socialAuthToken
                    )
                )
            }.onFailure {
                authStateHolder.updateState(
                    AuthState.Fail
                )
            }
        }
    }

    fun logout() {
        authStateHolder.updateState(
            AuthState.Initial
        )
    }
}

