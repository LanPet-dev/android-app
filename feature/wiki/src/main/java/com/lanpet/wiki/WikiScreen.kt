package com.lanpet.wiki

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.theme.LanPetAppTheme

@Composable
fun WikiScreen() {
    Text("WikiScreen")
}

@PreviewLightDark
@Composable
fun WikiScreenPreview() {
    LanPetAppTheme {
        WikiScreen()
    }
}