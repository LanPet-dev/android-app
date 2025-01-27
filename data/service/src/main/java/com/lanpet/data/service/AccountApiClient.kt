package com.lanpet.data.service

import com.google.gson.GsonBuilder
import com.lanpet.data.dto.typeadapter.AuthorityTypeTypeAdapter
import com.lanpet.data.service.interceptors.AccessTokenHeaderInterceptor
import com.lanpet.domain.model.AuthorityType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AccountApiClient
    @Inject
    constructor(
        @Named("BaseApiUrl") private val baseUrl: String,
        private val accessTokenHeaderInterceptor: AccessTokenHeaderInterceptor,
    ) {
        private val gson =
            GsonBuilder()
                .registerTypeAdapter(
                    AuthorityType::class.java,
                    AuthorityTypeTypeAdapter(),
                ).create()

        private val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(accessTokenHeaderInterceptor)
                .build()

        private val apiService =
            Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(AccountApiService::class.java)

        fun getService(): AccountApiService = apiService
    }
