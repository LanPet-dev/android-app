package com.lanpet.data.service

import com.google.gson.GsonBuilder
import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.data.dto.typeadapter.AuthorityTypeTypeAdapter
import com.lanpet.data.dto.typeadapter.FreeBoardCategoryTypeTypeAdapter
import com.lanpet.domain.model.AuthState
import com.lanpet.domain.model.AuthorityType
import com.lanpet.domain.model.FreeBoardCategoryType
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class FreeBoardApiClient
    @Inject
    constructor(
        private val baseUrl: String,
        private val authStateHolder: AuthStateHolder,
    ) {
        private val headerInterceptor =
            Interceptor { chain ->
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
                chain.proceed(request)
            }

        private val gson =
            GsonBuilder()
                .registerTypeAdapter(
                    AuthorityType::class.java,
                    AuthorityTypeTypeAdapter(),
                ).registerTypeAdapter(
                    FreeBoardCategoryType::class.java,
                    FreeBoardCategoryTypeTypeAdapter(),
                ).create()

        private val okHttpClient = OkHttpClient.Builder().addInterceptor(headerInterceptor).build()

        private val apiService =
            Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(FreeBoardApiService::class.java)

        fun getService(): FreeBoardApiService = apiService
    }
