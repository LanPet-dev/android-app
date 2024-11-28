package com.example.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AuthState
import com.example.usecase.GetCognitoSocialAuthTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(
        AuthState(
            isSignedIn = false,
            socialAuthToken = null
        )
    )
    val authState = _authState.asStateFlow()

    fun handleAuthCode(code: String) {
        viewModelScope.launch {
            try {
                val result = getCognitoSocialAuthTokenUseCase(code)

                println(result)
                _authState.value = when {
                    result.isSuccess -> _authState.value.copy(
                        isSignedIn = true,
                        socialAuthToken = result.getOrNull()
                    )

                    result.isFailure -> _authState.value.copy(
                        isSignedIn = false,
                        socialAuthToken = null,
                    )

                    else -> _authState.value
                }
            } catch (e: Exception) {
                Log.e(this@AuthViewModel.toString(), "Failed to get social auth token", e)
                _authState.value = _authState.value.copy(
                    isSignedIn = false,
                    socialAuthToken = null
                )
            }
        }
    }
}

