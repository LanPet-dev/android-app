package com.lanpet.data.service

import com.lanpet.data.dto.FindProfileResponse
import com.lanpet.data.dto.RegisterManProfileRequest
import com.lanpet.data.dto.RegisterPetProfileRequest
import com.lanpet.data.dto.RegisterProfileResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileApiService {
    @POST("profiles")
    suspend fun createManProfile(registerManProfileRequest: RegisterManProfileRequest): RegisterProfileResponse

    @POST("profiles")
    suspend fun createPetProfile(registerPetProfileRequest: RegisterPetProfileRequest): RegisterProfileResponse

    @GET("profiles")
    suspend fun getProfileList(): FindProfileResponse

    @GET("profiles/{id}")
    suspend fun getProfileDetail(id: String): FindProfileDetailResponse
}
