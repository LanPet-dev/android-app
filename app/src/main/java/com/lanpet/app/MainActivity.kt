package com.lanpet.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.example.designsystem.theme.LanPetAppTheme
import com.lanpet.auth.CognitoAuthManager
import com.lanpet.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LanPetAppTheme {
                Column {
                    Spacer(modifier = androidx.compose.ui.Modifier.weight(1f))
                    Button(
                        onClick = {
                            CognitoAuthManager(this@MainActivity).startGoogleSignIn()
                        }
                    ) {
                        Text("Google \nLogin")
                    }
                    AppNavigation()
                }
            }
        }
    }
}
