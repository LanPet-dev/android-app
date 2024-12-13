package com.lanpet.core.auth

import com.lanpet.domain.model.SocialAuthToken

abstract class SocialAuth {
    abstract suspend fun login(): SocialAuthToken?

    abstract fun logout()
}
