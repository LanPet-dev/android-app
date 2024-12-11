package com.lanpet.service


import com.example.dto.GetAccountResponse
import com.example.dto.RegisterAccountResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountApiService {
    @POST("accounts")
    suspend fun registerAccount(): RegisterAccountResponse

    @GET("accounts")
    suspend fun getAccount(): GetAccountResponse

}