package com.lanpet.core.auth

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
import com.lanpet.domain.usecase.account.GetAccountInformationUseCase
import com.lanpet.domain.usecase.account.RegisterAccountUseCase
import com.lanpet.domain.usecase.cognitoauth.GetCognitoSocialAuthTokenUseCase
import com.lanpet.domain.usecase.profile.GetAllProfileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class AuthManager
    @Inject
    constructor(
        private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase,
        private val registerAccountUseCase: RegisterAccountUseCase,
        private val getAccountInformationUseCase: GetAccountInformationUseCase,
        private val getAllProfileUseCase: GetAllProfileUseCase,
        private val authStateHolder: AuthStateHolder,
    ) {
        val authState = authStateHolder.authState

        /**
         * 대표 프로필
         */
        val defaultUserProfile = authStateHolder.defaultProfile

        /**
         * 유저의 프로필 목록
         */
        val userProfiles = authStateHolder.userProfiles

        @OptIn(FlowPreview::class)
        fun handleAuthCode(code: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val socialAuthToken =
                        getCognitoSocialAuthTokenUseCase(code).timeout(5.seconds).first()
                    authStateHolder.updateState(
                        AuthState.Loading(socialAuthToken = socialAuthToken),
                    )

                    try {
                        val account = getAccountInformationUseCase().timeout(5.seconds).first()
                        authStateHolder.updateState(
                            AuthState.Success(
                                socialAuthToken = socialAuthToken,
                                account = account,
                                navigationHandleFlag = false,
                            ),
                        )
                        val profile = getAllProfileUseCase().timeout(5.seconds).first()
                        authStateHolder.updateState(
                            AuthState.Success(
                                socialAuthToken = socialAuthToken,
                                account = account,
                                profile = profile,
                            ),
                        )
                    } catch (e: Exception) {
                        val accountToken = registerAccountUseCase().timeout(5.seconds).first()
                        delay(500)
                        val account = getAccountInformationUseCase().timeout(5.seconds).first()
                        authStateHolder.updateState(
                            AuthState.Success(
                                socialAuthToken = socialAuthToken,
                                account = account,
                                navigationHandleFlag = false,
                            ),
                        )
                        val profile = getAllProfileUseCase().timeout(5.seconds).first()
                        authStateHolder.updateState(
                            AuthState.Success(
                                socialAuthToken = socialAuthToken,
                                account = account,
                                profile = profile,
                            ),
                        )
                    }
                } catch (e: Exception) {
                    authStateHolder.updateState(
                        AuthState.Fail(),
                    )
                }
            }
        }

        fun logout() {
            authStateHolder.updateState(
                AuthState.Logout(),
            )
        }
    }
