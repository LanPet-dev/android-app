package com.lanpet.free

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.theme.LanPetAppTheme

@Composable
fun FreeBoardScreen() {
    Text("FreeBoardScreen")
}

@PreviewLightDark
@Composable
fun FreeBoardScreenPreview() {
    LanPetAppTheme {
        FreeBoardScreen()
    }
}