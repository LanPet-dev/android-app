package com.lanpet.data.service

import com.google.gson.GsonBuilder
import com.lanpet.data.dto.typeadapter.AuthorityTypeTypeAdapter
import com.lanpet.data.dto.typeadapter.FreeBoardCategoryTypeTypeAdapter
import com.lanpet.data.service.interceptors.AccessTokenHeaderInterceptor
import com.lanpet.data.service.interceptors.RefreshTokenInterceptor
import com.lanpet.domain.model.AuthorityType
import com.lanpet.domain.model.free.FreeBoardCategoryType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FreeBoardApiClient
    @Inject
    constructor(
        @Named("BaseApiUrl") baseUrl: String,
        private val refreshTokenInterceptor: RefreshTokenInterceptor,
        private val accessTokenHeaderInterceptor: AccessTokenHeaderInterceptor,
    ) {
        private val gson =
            GsonBuilder()
                .registerTypeAdapter(
                    AuthorityType::class.java,
                    AuthorityTypeTypeAdapter(),
                ).registerTypeAdapter(
                    FreeBoardCategoryType::class.java,
                    FreeBoardCategoryTypeTypeAdapter(),
                ).create()

        private val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(refreshTokenInterceptor)
                .addInterceptor(accessTokenHeaderInterceptor)
                .build()

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
