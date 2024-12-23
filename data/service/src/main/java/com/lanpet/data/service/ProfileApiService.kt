package com.lanpet.data.service

import com.lanpet.data.dto.FindProfileDetailResponse
import com.lanpet.data.dto.FindProfileResponse
import com.lanpet.data.dto.RegisterManProfileRequest
import com.lanpet.data.dto.RegisterPetProfileRequest
import com.lanpet.data.dto.RegisterProfileResponse
import com.lanpet.data.dto.UpdateProfileRequest
import com.lanpet.data.dto.UpdateProfileResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ProfileApiService {
    @POST("profiles")
    suspend fun createManProfile(
        @Body
        registerManProfileRequest: RegisterManProfileRequest,
    ): RegisterProfileResponse

    @POST("profiles")
    suspend fun createPetProfile(
        @Body
        registerPetProfileRequest: RegisterPetProfileRequest,
    ): RegisterProfileResponse

    @GET("profiles")
    suspend fun getProfileList(): FindProfileResponse

    @GET("profiles/{id}")
    suspend fun getProfileDetail(id: String): FindProfileDetailResponse

    @PATCH("profiles/{id}")
    suspend fun updateProfile(
        id: String,
        @Body
        updateProfileRequest: UpdateProfileRequest,
    ): UpdateProfileResponse

    @DELETE("profiles/{id}")
    suspend fun deleteProfile(id: String): Unit
}
