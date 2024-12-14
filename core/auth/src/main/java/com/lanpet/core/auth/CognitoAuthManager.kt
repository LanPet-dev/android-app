package com.lanpet.core.auth

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class CognitoAuthManager(
    private val context: Context,
) {
    companion object {
        // 아래 정보들은 노출되어도 상관없는 데이터.
        private const val COGNITO_DOMAIN = "https://lanpet.auth.ap-northeast-2.amazoncognito.com"
        private const val CLIENT_ID = "1me4f6pjgepjiur33u3j5dj3r7"
        private const val REDIRECT_URI = "auth://lanpet.com"
    }

    fun startGoogleSignIn() {
        val authUri = buildAuthUri()
        launchCustomTab(authUri)
    }

    private fun buildAuthUri(): Uri {
        val encodedRedirectUri = URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.toString())

        val authUrl =
            buildString {
                append(COGNITO_DOMAIN)
                append("/oauth2/authorize")
                append("?response_type=code")
                append("&client_id=").append(CLIENT_ID)
                append("&redirect_uri=").append(encodedRedirectUri)
                append("&identity_provider=Google")
                append("&scope=email+openid")
            }

        return Uri.parse(authUrl)
    }

    private fun launchCustomTab(uri: Uri) {
        CustomTabsIntent
            .Builder()
            .setShowTitle(true)
            .setUrlBarHidingEnabled(true)
            .build()
            .apply {
                launchUrl(context, uri)
            }
    }
}
