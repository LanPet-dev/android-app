package com.example.model

import androidx.annotation.Keep

@Keep
enum class SocialAuthType {
    GOOGLE,
    APPLE
}

@Keep
fun String.toSocialAuthType(): SocialAuthType {
    return when (this) {
        "GOOGLE" -> SocialAuthType.GOOGLE
        "APPLE" -> SocialAuthType.APPLE
        else -> throw IllegalArgumentException("Unknown SocialAuthType")
    }
}

