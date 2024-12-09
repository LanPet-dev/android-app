package com.lanpet.core.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AuthState
import com.example.model.SocialAuthToken
import com.example.usecase.GetAccountInformationUseCase
import com.example.usecase.GetCognitoSocialAuthTokenUseCase
import com.example.usecase.RegisterAccountUseCase
import com.lanpet.core.manager.AuthStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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

    //TODO("Satoshi"): refactor to flow
    @OptIn(ExperimentalCoroutinesApi::class)
    fun handleAuthCode(code: String) {
        viewModelScope.launch {
            flow {
                val socialAuthToken = getCognitoSocialAuthTokenUseCase(code).getOrThrow()

                authStateHolder.updateState(
                    AuthState.Loading(
                        socialAuthToken = socialAuthToken,
                    )
                )

                emit(socialAuthToken)
            }.flatMapLatest { socialAuthToken ->
                flow {
                    val accountInformation = getAccountInformationUseCase().getOrThrow()
                    authStateHolder.updateState(
                        AuthState.Success(
                            socialAuthToken = socialAuthToken,
                            account = accountInformation
                        )
                    )
                    emit(accountInformation)
                }.catch {
                    registerAccountUseCase().getOrThrow()
                    val accountInformation = getAccountInformationUseCase().getOrThrow()

                    authStateHolder.updateState(
                        AuthState.Success(
                            socialAuthToken = socialAuthToken,
                            account = accountInformation
                        )
                    )
                }.catch {
                    authStateHolder.updateState(
                        AuthState.Fail
                    )
                }
            }.collect{
                // do nothing
            }
        }
    }

    fun logout() {
        authStateHolder.updateState(
            AuthState.Initial
        )
    }
}