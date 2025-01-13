package com.lanpet.domain.model

import java.util.Date

data class SocialAuthToken(
    val socialAuthType: SocialAuthType,
    val accessToken: String?,
    val refreshToken: String?,
    val expiresIn: Int?,
    val expireDateTime: Date = Date(System.currentTimeMillis() + (expiresIn ?: 0) * 1000L),
) {
    override fun toString(): String =
        "SocialAuthToken(socialAuthType=$socialAuthType, accessToken=$accessToken, refreshToken=$refreshToken)"
}
