package com.lanpet.free

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.WhiteColor

@Composable
fun FreeBoardScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: (() -> Unit)? = null,
    onNavigateToFreeBoardWrite: () -> Unit = {},
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToFreeBoardWrite()
                },
                containerColor = PrimaryColor.PRIMARY,
                contentColor = WhiteColor.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 80.dp),
            ) {
                Icon(
                    painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_edit_outline),
                    "Floating action button.",
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it),
        ) {
            Text("FreeBoardScreen")
        }
    }
}

@PreviewLightDark
@Composable
private fun FreeBoardScreenPreview() {
    LanPetAppTheme {
        FreeBoardScreen()
    }
}
