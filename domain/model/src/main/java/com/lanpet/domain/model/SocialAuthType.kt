package com.lanpet.domain.model

enum class SocialAuthType {
    GOOGLE,
    APPLE,
}

fun String.toSocialAuthType(): SocialAuthType =
    when (this) {
        "GOOGLE" -> SocialAuthType.GOOGLE
        "APPLE" -> SocialAuthType.APPLE
        else -> throw IllegalArgumentException("Unknown SocialAuthType")
    }
