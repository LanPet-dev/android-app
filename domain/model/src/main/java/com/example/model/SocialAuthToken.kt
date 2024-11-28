package com.example.model

data class SocialAuthToken(
    val socialAuthType: SocialAuthType,
    val accessToken: String?,
    val refreshToken: String?,
) {
    override fun toString(): String {
        return "SocialAuthToken(socialAuthType=$socialAuthType, accessToken=$accessToken, refreshToken=$refreshToken)"
    }
}

fun SocialAuthToken.fromString(): SocialAuthToken {
    val split = this.toString().split(" ")
    return SocialAuthToken(
        socialAuthType = split[1].toSocialAuthType(),
        accessToken = split[2],
        refreshToken = split[3]
    )
}