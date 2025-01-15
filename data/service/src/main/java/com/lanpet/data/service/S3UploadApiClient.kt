package com.lanpet.data.service

import com.google.gson.GsonBuilder
import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class S3UploadApiClient
    @Inject
    constructor(
        private val authStateHolder: AuthStateHolder,
        private val baseUrl: String = "https://lanpet-resource.s3.ap-northeast-2.amazonaws.com/",
    ) {
        private val headerInterceptor =
            Interceptor { chain ->
                if (authStateHolder.authState.value !is AuthState.Loading &&
                    authStateHolder.authState.value !is AuthState.Success) {
                    throw SecurityException("x-access-token is required")
                }

                val request =
                    chain
                        .request()
                        .newBuilder()
                        .addHeader("Content-Type", "image/jpeg")
                        .build()
                chain.proceed(request)
            }

        private val gson =
            GsonBuilder()
                .create()

        private val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(headerInterceptor)
                .build()

        private fun apiService() =
            Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(S3UploadApiService::class.java)

        fun getService(): S3UploadApiService = apiService()
    }