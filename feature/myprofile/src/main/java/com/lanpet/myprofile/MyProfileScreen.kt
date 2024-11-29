package com.lanpet.myprofile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.auth.LocalAuthViewModel
import kotlinx.coroutines.delay

@Composable
fun MyProfileScreen() {
    val authViewModel = LocalAuthViewModel.current

    Text(text = "MyProfileScreen")
    Button(onClick = {
        authViewModel.logout()
    }) {
        Text("logout")
    }
}

@Composable
@PreviewLightDark()
fun MyProfileScreenPreview() {
    LanPetAppTheme {
        MyProfileScreen()
    }
}