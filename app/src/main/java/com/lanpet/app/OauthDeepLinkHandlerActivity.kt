package com.lanpet.app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint

// 딥링크를 처리할 액티비티
@AndroidEntryPoint
class OauthDeepLinkHandlerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 딥링크로 전달된 데이터 처리
        intent?.data?.let { uri ->
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
        // TODO("Satoshi"): 인증 코드 처리
        Toast.makeText(this, "인증 성공: $code", Toast.LENGTH_SHORT).show()

        // TODO("Satoshi"): auth viewmodel 에 인증정보 저장
    }

    private fun handleAuthError() {
        Toast.makeText(this, "인증 실패", Toast.LENGTH_SHORT).show()
    }
}