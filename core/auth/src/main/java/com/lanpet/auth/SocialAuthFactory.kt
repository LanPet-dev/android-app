package com.lanpet.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialAuthFactory @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun createSocialAuth(type: SocialAuthType): SocialAuth {
        return when (type) {
            SocialAuthType.GOOGLE -> GoogleAuth.newInstance(
                BuildConfig.GOOGLE_OAUTH_CLIENT_KEY,
                context
            )

            SocialAuthType.APPLE -> throw NotImplementedError("Apple auth is not implemented yet")
        }
    }
}
