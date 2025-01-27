package com.lanpet.data.service

import com.google.gson.GsonBuilder
import com.lanpet.data.dto.typeadapter.PetCategoryTypeAdapter
import com.lanpet.data.dto.typeadapter.ProfileTypeTypeAdapter
import com.lanpet.data.service.interceptors.AccessTokenHeaderInterceptor
import com.lanpet.data.service.interceptors.RefreshTokenInterceptor
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ProfileApiClient
    @Inject
    constructor(
        @Named("BaseApiUrl") baseUrl: String,
        private val refreshTokenInterceptor: RefreshTokenInterceptor,
        private val accessTokenHeaderInterceptor: AccessTokenHeaderInterceptor,
    ) {
        private val gson =
            GsonBuilder()
                .registerTypeAdapter(ProfileType::class.java, ProfileTypeTypeAdapter())
                .registerTypeAdapter(PetCategory::class.java, PetCategoryTypeAdapter())
                .create()

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
                .create(ProfileApiService::class.java)

        fun getService(): ProfileApiService = apiService
    }
