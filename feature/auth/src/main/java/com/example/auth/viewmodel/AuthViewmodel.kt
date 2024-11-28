package com.example.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AuthState
import com.example.usecase.GetCognitoSocialAuthTokenUseCase
import com.lanpet.auth.AuthStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase,
    private val authStateHolder: AuthStateHolder
) : ViewModel() {

    val authState = authStateHolder.authState

    fun handleAuthCode(code: String) {
        viewModelScope.launch {
            try {
                val result = getCognitoSocialAuthTokenUseCase(code)

                when {
                    result.isSuccess -> authStateHolder.updateState(
                        AuthState.Success(
                            isSignedIn = true,
                            socialAuthToken = result.getOrNull()
                        )
                    )

                    result.isFailure -> authStateHolder.updateState(
                        AuthState.Fail
                    )

                    else -> Unit
                }
            } catch (e: Exception) {
                Log.e(this@AuthViewModel.toString(), "Failed to get social auth token", e)
                authStateHolder.updateState(
                    AuthState.Fail
                )
            }
        }
    }
}

