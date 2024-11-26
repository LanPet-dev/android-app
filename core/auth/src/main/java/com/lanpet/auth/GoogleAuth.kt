package com.lanpet.auth

import android.content.Context
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import java.util.UUID

class GoogleAuth private constructor(
    private val googleOauthClientKey: String,
    private val context: Context
) : SocialAuth() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override suspend fun login(): SocialAuthToken? {
        var resultToken: SocialAuthToken? = null

        val googleIdOption =
            GetGoogleIdOption.Builder()
                .setServerClientId(googleOauthClientKey)
                .setNonce(generateNonce())  // 보안을 위한 난수 추가
                .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialManager = CredentialManager.create(context)

        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
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

    private fun generateNonce(): String {
        return UUID.randomUUID().toString()
    }

    fun handleSignIn(result: GetCredentialResponse): SocialAuthToken? {
        val credential = result.credential

        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        val googleAuthToken = SocialAuthToken(
                            SocialAuthType.GOOGLE,
                            googleIdTokenCredential.idToken,
                            null,
                            googleIdTokenCredential.data.get("com.google.android.libraries.identity.googleid.BUNDLE_KEY_ID")
                                .toString()
                        )


                        return googleAuthToken
//                        saveToken(googleAuthToken)
                        //TODO("Satoshi"): auth process to cognito

                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                        return null
                    }
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                    return null
                }
            }

            else -> {
                Log.e(TAG, "Unexpected type of credential")
                return null
            }
        }
    }

    companion object {
        private const val TAG = "GoogleAuth"

        internal fun newInstance(googleOauthClientKey: String, context: Context): GoogleAuth {
            return GoogleAuth(googleOauthClientKey, context)
        }
    }
}