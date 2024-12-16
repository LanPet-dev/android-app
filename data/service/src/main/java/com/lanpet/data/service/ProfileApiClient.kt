package com.lanpet.data.service

import com.google.gson.GsonBuilder
import com.lanpet.data.dto.Butler
import com.lanpet.data.dto.typeadapter.PetCategoryTypeAdapter
import com.lanpet.data.dto.typeadapter.ProfileTypeTypeAdapter
import com.lanpet.domain.model.ProfileType
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ProfileApiClient
    @Inject
    constructor(
        private val baseUrl: String,
    ) {
        private val headerInterceptor =
            Interceptor { chain ->
                val request =
                    chain
                        .request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                chain.proceed(request)
            }

        private val gson =
            GsonBuilder()
                .registerTypeAdapter(ProfileType::class.java, ProfileTypeTypeAdapter())
                .registerTypeAdapter(Butler::class.java, PetCategoryTypeAdapter())
                .create()

        private val okHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(headerInterceptor)
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