package com.lanpet.data.service

import com.lanpet.data.dto.GetAccountResponse
import com.lanpet.data.dto.RegisterAccountResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountApiService {
    @POST("accounts")
    suspend fun registerAccount(): RegisterAccountResponse

    @GET("accounts")
    suspend fun getAccount(): GetAccountResponse
}
