package com.lanpet.auth

enum class SocialAuthType {
    GOOGLE,
    APPLE
}

data class SocialAuthToken(
    val socialAuthType: SocialAuthType,
    val accessToken: String?,
    val refreshToken: String?,
    val email: String?
)