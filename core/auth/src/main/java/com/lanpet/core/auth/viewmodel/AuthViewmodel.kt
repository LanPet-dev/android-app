package com.lanpet.core.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AuthState
import com.example.usecase.GetAccountInformationUseCase
import com.example.usecase.GetCognitoSocialAuthTokenUseCase
import com.example.usecase.RegisterAccountUseCase
import com.lanpet.core.manager.AuthStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase,
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val getAccountInformationUseCase: GetAccountInformationUseCase,
    private val authStateHolder: AuthStateHolder
) : ViewModel() {

    val authState = authStateHolder.authState

    //TODO("Satoshi"): refactor to flow
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun handleAuthCode(code: String) {
        viewModelScope.launch {
            try {
                val token = getCognitoSocialAuthTokenUseCase(code).timeout(5.seconds).onEach {
                    authStateHolder.updateState(
                        AuthState.Loading(socialAuthToken = it)
                    )
                }.first()

                try {
                    val account = getAccountInformationUseCase().single()
                    authStateHolder.updateState(
                        AuthState.Success(
                            socialAuthToken = token,
                            account = account
                        )
                    )
                } catch (e: Exception) {
                    val accountToken = registerAccountUseCase().single()
                    val account = getAccountInformationUseCase().single()
                    authStateHolder.updateState(
                        AuthState.Success(
                            socialAuthToken = token,
                            account = account
                        )
                    )
                }
            } catch (e: Exception) {
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