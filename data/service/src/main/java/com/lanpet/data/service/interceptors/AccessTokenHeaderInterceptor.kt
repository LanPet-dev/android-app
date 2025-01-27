package com.lanpet.data.service.interceptors

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccessTokenHeaderInterceptor
    @Inject
    constructor(
        private val authStateHolder: AuthStateHolder,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val token =
                when (authStateHolder.authState.value) {
                    is AuthState.Loading -> (authStateHolder.authState.value as AuthState.Loading).socialAuthToken?.accessToken
                    is AuthState.Success -> (authStateHolder.authState.value as AuthState.Success).socialAuthToken?.accessToken
                    else -> throw SecurityException("x-access-token is required")
                }

            val request =
                chain
                    .request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader(
                        "x-access-token",
                        token ?: throw SecurityException("x-access-token is required"),
                    ).build()

            return chain.proceed(request)
        }
    }
