package com.lanpet.app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.manager.AuthStateHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OauthDeepLinkHandlerActivity : ComponentActivity() {
    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var authStateHolder: AuthStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 딥링크로 전달된 데이터 처리
        intent?.data?.let { uri ->
            Log.d("OauthDeepLinkHandlerActivity", "uri: $uri")
            val code = uri.getQueryParameter("code")
            if (code != null) {
                handleAuthSuccess(code)
            } else {
                handleAuthError()
            }
        }

        // 이 액티비티는 투명하게 처리하고 빠르게 종료
        finish()
    }

    private fun handleAuthSuccess(code: String) {
        authManager.handleAuthCode(code)
    }

    private fun handleAuthError() {
        Toast.makeText(this, "인증 실패", Toast.LENGTH_SHORT).show()
    }
}
