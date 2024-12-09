package com.lanpet.wiki

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.lanpet.core.common.widget.BottomNavItem
import com.lanpet.core.common.widget.LanPetBottomNavBar
import com.lanpet.core.designsystem.theme.LanPetAppTheme

@Composable
fun WikiScreen() {
    Scaffold() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {

            Text("WikiScreen")
        }
    }
}

@PreviewLightDark
@Composable
fun WikiScreenPreview() {
    LanPetAppTheme {
        WikiScreen()
    }
}