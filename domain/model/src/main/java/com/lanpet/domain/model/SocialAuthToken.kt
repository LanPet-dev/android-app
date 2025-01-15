package com.lanpet.domain.model

import java.util.Date

data class SocialAuthToken(
    val socialAuthType: SocialAuthType,
    val accessToken: String?,
    val refreshToken: String?,
    val expiresIn: Int?,
    private val expireDateTime: Date = Date(System.currentTimeMillis() + (expiresIn ?: 0) * 1000L),
) {
    override fun toString(): String =
        "SocialAuthToken(socialAuthType=$socialAuthType, accessToken=$accessToken, refreshToken=$refreshToken, expiresIn=$expiresIn, expireDateTime=$expireDateTime)"

    fun isExpired(): Boolean = expireDateTime.time - System.currentTimeMillis() <= 0

    fun shouldRefresh(): Boolean = expireDateTime.time - System.currentTimeMillis() + 1000 * 60 * 3 <= 0

    init {
        expiresIn?.let {
            require(expiresIn > 0) { "expiresIn must be greater than 0" }
        }
    }
}
