package com.example.model

data class SocialAuthToken(
    val socialAuthType: SocialAuthType,
    val accessToken: String?,
    val refreshToken: String?,
)