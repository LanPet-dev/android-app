package com.lanpet.wiki

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.PreparingScreen
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WikiScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(
                        style = MaterialTheme.customTypography().title2SemiBoldSingle,
                        text = stringResource(R.string.tab_title_wiki),
                    )
                },
            )
        },
    ) {
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it),
        ) {
            PreparingScreen(
                titleResId = R.string.preparing_screen_title,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WikiScreenPreview() {
    LanPetAppTheme {
        WikiScreen()
    }
}
