package com.lanpet.core.auth

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
import com.lanpet.domain.usecase.GetAccountInformationUseCase
import com.lanpet.domain.usecase.GetCognitoSocialAuthTokenUseCase
import com.lanpet.domain.usecase.RegisterAccountUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class AuthManager @Inject constructor(
    private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase,
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val getAccountInformationUseCase: GetAccountInformationUseCase,
    private val authStateHolder: AuthStateHolder
) {

    val authState = authStateHolder.authState

    @OptIn(FlowPreview::class)
    fun handleAuthCode(code: String) {
        CoroutineScope(Dispatchers.Main).launch {
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