package com.lanpet.myprofile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import com.example.designsystem.theme.LanPetAppTheme
import com.lanpet.auth.AuthStateHolder
import com.lanpet.auth.LocalAuthViewModel
import javax.inject.Inject

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