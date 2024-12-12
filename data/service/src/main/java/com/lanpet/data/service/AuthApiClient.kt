package com.lanpet.data.service

import com.lanpet.data.dto.typeadapter.ProfileTypeTypeAdapter
import com.google.gson.GsonBuilder
import com.lanpet.domain.model.ProfileType
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthApiClient @Inject constructor(
    private val baseUrl: String,
) {
    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .build()
        chain.proceed(request)
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(ProfileType::class.java, ProfileTypeTypeAdapter())
        .create()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .build()

    private val apiService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(BaseApiService::class.java)

    fun getService(): BaseApiService {
        return apiService
    }
}