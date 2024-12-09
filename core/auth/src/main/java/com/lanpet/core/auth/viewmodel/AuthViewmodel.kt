package com.lanpet.core.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AuthState
import com.example.usecase.GetAccountInformationUseCase
import com.example.usecase.GetCognitoSocialAuthTokenUseCase
import com.example.usecase.RegisterAccountUseCase
import com.lanpet.core.manager.AuthStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase,
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val getAccountInformationUseCase: GetAccountInformationUseCase,
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
                    AuthState.Loading(
                        socialAuthToken = socialAuthToken,
                    )
                )

                getAccountInformationUseCase().onSuccess {
                    print("getAccountInformationUseCase success:: ${it.toString()}")
                    authStateHolder.updateState(
                        AuthState.Success(
                            socialAuthToken = socialAuthToken,
                            account = it
                        )
                    )
                }.onFailure {
                    println("getAccountInformationUseCase failed:: ${it.stackTraceToString()}")
                    registerAccountUseCase().onSuccess {
                        getAccountInformationUseCase().onSuccess { account ->
                            authStateHolder.updateState(
                                AuthState.Success(
                                    socialAuthToken = socialAuthToken,
                                    account = account
                                )
                            )
                        }.onFailure {
                            authStateHolder.updateState(
                                AuthState.Fail
                            )
                        }
                    }.onFailure {
                        authStateHolder.updateState(
                            AuthState.Fail
                        )
                    }
                    authStateHolder.updateState(
                        AuthState.Fail
                    )
                }
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

