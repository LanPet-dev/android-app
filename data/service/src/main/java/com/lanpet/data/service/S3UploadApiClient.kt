package com.lanpet.data.service

import com.google.gson.GsonBuilder
import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * S3UploadApiClient는 S3 업로드를 위한 API 클라이언트입니다.
 * 현재 페이지에서 사용하는 `baseUrl`은 S3UploadApiClient 빌드에만 사용되며,
 * 실제 API 통신에는 사용되지 않습니다.
 */
class S3UploadApiClient
    @Inject
    constructor(
        private val authStateHolder: AuthStateHolder,
        private val baseUrl: String = "https://lanpet-resource.s3.ap-northeast-2.amazonaws.com/",
    ) {
        private val headerInterceptor =
            Interceptor { chain ->
                if (authStateHolder.authState.value !is AuthState.Loading &&
                    authStateHolder.authState.value !is AuthState.Success
                ) {
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
