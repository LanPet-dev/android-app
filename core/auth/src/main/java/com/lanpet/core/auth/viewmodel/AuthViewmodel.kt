package com.lanpet.core.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.AuthState
import com.lanpet.domain.usecase.GetAccountInformationUseCase
import com.lanpet.domain.usecase.GetCognitoSocialAuthTokenUseCase
import com.lanpet.domain.usecase.RegisterAccountUseCase
import com.lanpet.core.manager.AuthStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
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

    @OptIn(FlowPreview::class)
    fun handleAuthCode(code: String) {
        viewModelScope.launch(SupervisorJob()) {
            try {
                val socialAuthToken =
                    getCognitoSocialAuthTokenUseCase(code).timeout(5.seconds).first()
                authStateHolder.updateState(
                    AuthState.Loading(socialAuthToken = socialAuthToken)
                )

                try {
                    val account = getAccountInformationUseCase().timeout(5.seconds).first()
                    authStateHolder.updateState(
                        AuthState.Success(
                            socialAuthToken = socialAuthToken,
                            account = account
                        )
                    )
                } catch (e: Exception) {
                    val accountToken = registerAccountUseCase().timeout(5.seconds).first()
                    delay(500)
                    val account = getAccountInformationUseCase().timeout(5.seconds).first()
                    authStateHolder.updateState(
                        AuthState.Success(
                            socialAuthToken = socialAuthToken,
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