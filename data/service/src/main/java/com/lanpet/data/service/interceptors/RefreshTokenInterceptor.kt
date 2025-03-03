package com.lanpet.data.service.interceptors

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
import com.lanpet.domain.usecase.cognitoauth.RefreshCognitoSocialAuthTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class RefreshTokenInterceptor
    @Inject
    constructor(
        private val refreshTokenUseCase: RefreshCognitoSocialAuthTokenUseCase,
        private val authStateHolder: AuthStateHolder,
    ) : Interceptor {
        @OptIn(FlowPreview::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            Timber.d(
                "RefreshTokenInterceptor\n" +
                    "authState: ${authStateHolder.authState.value}\n",
            )

            if (authStateHolder.authState.value !is AuthState.Success && authStateHolder.authState.value !is AuthState.Loading) {
                return chain.proceed(chain.request())
            }

            val socialAuthToken =
                when (authStateHolder.authState.value) {
                    is AuthState.Loading -> (authStateHolder.authState.value as AuthState.Loading).socialAuthToken
                    is AuthState.Success -> (authStateHolder.authState.value as AuthState.Success).socialAuthToken
                    else -> throw SecurityException("socialAuthToken is required")
                }

            assert(socialAuthToken?.accessToken != null) { "accessToken is required" }

            // 만료시간이 3분 이내로 남았을 경우 refreshToken을 사용하여 accessToken을 갱신한다.
            if (socialAuthToken!!.shouldRefresh()) {
                if (socialAuthToken.refreshToken == null) {
                    throw SecurityException("refreshToken is required")
                }

                runBlocking(Dispatchers.IO) {
                    runCatching {
                        refreshTokenUseCase(socialAuthToken.refreshToken!!)
                            .timeout(5.seconds)
                            .retryWhen { cause, attempt ->
                                cause is Exception && attempt < 3
                            }.collect {
                                when (authStateHolder.authState.value) {
                                    is AuthState.Loading ->
                                        authStateHolder.updateState(
                                            (authStateHolder.authState.value as AuthState.Loading).copy(
                                                socialAuthToken = it,
                                                navigationHandleFlag = false,
                                            ),
                                        )

                                    is AuthState.Success ->
                                        authStateHolder.updateState(
                                            (authStateHolder.authState.value as AuthState.Success).copy(
                                                socialAuthToken = it,
                                                navigationHandleFlag = false,
                                            ),
                                        )

                                    else -> Unit
                                }
                            }
                    }.onFailure {
                        authStateHolder.updateState(AuthState.Fail())
                        throw it
                    }
                }
            }

            return chain.proceed(chain.request())
        }
    }
