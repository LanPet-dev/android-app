package com.example.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.theme.LanPetAppTheme
import com.example.designsystem.theme.LanPetDimensions

@Composable
fun LoginScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.Layout.horizontal
                )
        ) {
            Text("LoginScreen")
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewLoginScreen() {
    LanPetAppTheme {
        LoginScreen()
    }
}