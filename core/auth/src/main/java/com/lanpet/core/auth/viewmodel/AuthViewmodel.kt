package com.lanpet.core.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AuthState
import com.example.model.SocialAuthToken
import com.example.model.account.Account
import com.example.usecase.GetAccountInformationUseCase
import com.example.usecase.GetCognitoSocialAuthTokenUseCase
import com.example.usecase.RegisterAccountUseCase
import com.lanpet.core.manager.AuthStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
            getCognitoSocialAuthTokenUseCase(code).timeout(5.seconds)
                .onEach {
                    authStateHolder.updateState(AuthState.Loading(it))
                }
                .onEach { token ->
                    authStateHolder.updateState(AuthState.Loading(token))
                }.flatMapLatest { socialAuthToken ->
                    getAccountInformationUseCase().timeout(5.seconds)
                        .map { account ->
                            Pair(socialAuthToken, account)
                        }.catch {
                            registerAccountUseCase().timeout(5.seconds).map {
                                socialAuthToken
                            }.flatMapLatest {
                                getAccountInformationUseCase().timeout(5.seconds).map { account ->
                                    Pair<SocialAuthToken, Account?>(socialAuthToken, account)
                                }
                            }.catch {
                                emit(Pair(socialAuthToken, null))
                            }
                        }
                }.catch {
                    authStateHolder.updateState(AuthState.Fail)
                }.collect {
                    println("collect called :: $it")
                    if (it.second == null) {
                        authStateHolder.updateState(AuthState.Fail)
                        return@collect
                    }

                    authStateHolder.updateState(AuthState.Success(it.first, it.second))
                }
        }
    }

    fun logout() {
        authStateHolder.updateState(
            AuthState.Initial
        )
    }
}