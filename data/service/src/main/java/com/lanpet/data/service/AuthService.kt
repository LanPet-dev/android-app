package com.lanpet.data.service

import com.lanpet.data.dto.RefreshTokenResponse
import com.lanpet.data.dto.TokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getTokens(
        @Field("grant_type") grantType: String,
        @Field("code") code: String,
        @Field("client_id") clientId: String,
        @Field("redirect_uri") redirectUri: String,
    ): TokenResponse

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun refreshTokens(
        @Field("grant_type") grantType: String,
        @Field("refresh_token") refreshToken: String,
        @Field("client_id") clientId: String,
    ): RefreshTokenResponse
}
