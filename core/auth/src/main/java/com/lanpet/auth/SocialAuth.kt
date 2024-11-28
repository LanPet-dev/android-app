package com.lanpet.auth

import com.example.model.SocialAuthToken

abstract class SocialAuth {
    abstract suspend fun login(): SocialAuthToken?
    abstract fun logout()
}