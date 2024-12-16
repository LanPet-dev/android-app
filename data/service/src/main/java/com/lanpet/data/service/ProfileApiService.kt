package com.lanpet.data.service

import com.lanpet.data.dto.RegisterManProfileRequest
import com.lanpet.data.dto.RegisterPetProfileRequest
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileApiService {
    @POST("profiles")
    suspend fun createManProfile(registerManProfileRequest: RegisterManProfileRequest)

    @POST("profiles")
    suspend fun createPetProfile(registerPetProfileRequest: RegisterPetProfileRequest)

    @GET("profiles")
    suspend fun getProfileList()

    @GET("profiles/{id}")
    suspend fun getProfileDetail(id: String)
}
