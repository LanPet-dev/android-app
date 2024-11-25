package com.lanpet.myprofile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.theme.LanPetAppTheme

@Composable
fun MyProfileScreen() {
    Text(text = "MyProfileScreen")
}

@Composable
@PreviewLightDark()
fun MyProfileScreenPreview() {
    LanPetAppTheme {
        MyProfileScreen()
    }
}