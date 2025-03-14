package com.lanpet.core.auth

import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.model.SocialAuthType
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.UUID

class GoogleAuth private constructor(
    private val googleOauthClientKey: String,
    private val context: WeakReference<Context>,
) : SocialAuth() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun login(): SocialAuthToken? {
        if (context.get() == null) {
            Timber.e("Context is null")
            return null
        }

        var resultToken: SocialAuthToken? = null

        val googleIdOption =
            GetGoogleIdOption
                .Builder()
                .setServerClientId(googleOauthClientKey)
                .setNonce(generateNonce()) // 보안을 위한 난수 추가
                .build()

        val request =
            GetCredentialRequest
                .Builder()
                .addCredentialOption(googleIdOption)
                .build()

        val credentialManager = CredentialManager.create(context.get()!!)

        try {
            val result =
                credentialManager.getCredential(
                    request = request,
                    context = context.get()!!,
                )

            resultToken = handleSignIn(result)
        } catch (e: GetCredentialException) {
            e.printStackTrace()
        }

        return resultToken
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    private fun generateNonce(): String = UUID.randomUUID().toString()

    fun handleSignIn(result: GetCredentialResponse): SocialAuthToken? {
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential
                                .createFrom(credential.data)

                        val googleAuthToken =
                            SocialAuthToken(
                                accessToken = googleIdTokenCredential.idToken,
                                refreshToken = "",
                                socialAuthType = SocialAuthType.GOOGLE,
                                expiresIn = TODO(),
                            )

                        return googleAuthToken
//                        saveToken(googleAuthToken)
                        // TODO("Satoshi"): auth process to cognito
                    } catch (e: GoogleIdTokenParsingException) {
                        Timber.e("Received an invalid google id token response", e)
                        return null
                    }
                } else {
                    Timber.e("Unexpected type of credential")
                    return null
                }
            }

            else -> {
                Timber.e("Unexpected type of credential")
                return null
            }
        }
    }

    companion object {
        internal fun newInstance(
            googleOauthClientKey: String,
            context: Context,
        ): GoogleAuth = GoogleAuth(googleOauthClientKey, WeakReference(context))
    }
}
