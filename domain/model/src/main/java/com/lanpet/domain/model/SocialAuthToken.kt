package com.lanpet.domain.model

data class SocialAuthToken(
    val socialAuthType: SocialAuthType,
    val accessToken: String?,
    val refreshToken: String?,
) {
    override fun toString(): String =
        "SocialAuthToken(socialAuthType=$socialAuthType, accessToken=$accessToken, refreshToken=$refreshToken)"
}
