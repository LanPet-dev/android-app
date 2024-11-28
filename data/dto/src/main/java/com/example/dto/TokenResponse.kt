package com.example.dto

import com.example.model.SocialAuthToken
import com.example.model.SocialAuthType
import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("id_token")
    val idToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("token_type")
    val tokenType: String
)

fun TokenResponse.toSocialAuthToken(socialAuthType: SocialAuthType) = SocialAuthToken(
    accessToken = accessToken,
    refreshToken = refreshToken,
    socialAuthType = socialAuthType
)
