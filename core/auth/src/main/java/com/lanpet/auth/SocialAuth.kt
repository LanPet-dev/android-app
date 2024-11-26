package com.lanpet.auth

abstract class SocialAuth {
    abstract suspend fun login(): SocialAuthToken?
    abstract fun logout()
}

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