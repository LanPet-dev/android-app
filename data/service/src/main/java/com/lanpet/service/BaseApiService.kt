package com.lanpet.service

import retrofit2.http.GET
import retrofit2.http.POST

interface BaseApiService {
    @POST("profiles")
    suspend fun createProfile()

    @GET("profiles")
    suspend fun getProfileList()

    @GET("profiles/{id}")
    suspend fun getProfileDetail(id: String)
}