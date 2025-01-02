package com.lanpet.wiki

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun PreparingScreen(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .offset(y = maxHeight * 0.2f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(com.lanpet.core.designsystem.R.drawable.img_preparing),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(200.dp),
                )
                Text(
                    text = stringResource(R.string.preparing_screen_title),
                    style = MaterialTheme.customTypography().body1SemiBoldMulti,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreparingScreenPreview() {
    LanPetAppTheme {
        Scaffold {
            Surface(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(it),
            ) {
                PreparingScreen()
            }
        }
    }
}
