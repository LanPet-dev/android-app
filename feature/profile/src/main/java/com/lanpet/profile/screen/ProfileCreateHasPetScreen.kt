package com.lanpet.profile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.profile.R
import com.lanpet.profile.widget.Heading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateHasPetScreen(
    onNavigateToYesPetScreen: () -> Unit,
    onNavigateToNoPetScreen: () -> Unit,
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {},
            )
        },
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.vertical,
                    )
            ) {
                Spacer(Modifier.weight(0.1f))
                Heading(title = stringResource(R.string.heading_profile_create_has_pet))
                Spacer(Modifier.weight(0.3f))
                Content(onNavigateToYesPetScreen, onNavigateToNoPetScreen)
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun Content(
    onNavigateToYesPetScreen: () -> Unit,
    onNavigateToNoPetScreen: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.Companion
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        HasPetSelectButton(
            onNavigateToYesPetScreen,
            title = stringResource(R.string.yes_pet_profile_create_has_pet)
        )
        Spacer(Modifier.padding(horizontal = LanPetDimensions.Margin.small))
        HasPetSelectButton(
            onNavigateToNoPetScreen,
            title = stringResource(R.string.no_pet_profile_create_has_pet)
        )
    }
}

@Composable
fun HasPetSelectButton(onClick: () -> Unit, title: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                onClick = onClick,
            )
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = MaterialTheme.customColorScheme.spacerLine,
                shape = RoundedCornerShape(LanPetDimensions.Corner.small)
            )
            .padding(
                horizontal = LanPetDimensions.Margin.xLarge,
                vertical = LanPetDimensions.Margin.xxLarge
            )
    ) {
        Image(
            painter = painterResource(com.lanpet.core.designsystem.R.drawable.img_dummy),
            contentDescription = null,
            modifier = Modifier
                .size(
                    110.dp
                )
                .clip(
                    RoundedCornerShape(LanPetDimensions.Corner.small)
                ),

            )
        Spacer(Modifier.padding(vertical = LanPetDimensions.Margin.small))
        Text(title, style = MaterialTheme.typography.labelLarge)
    }
}

@PreviewLightDark
@Composable
fun PreviewProfileCreateHasPetScreen() {
    LanPetAppTheme {
        ProfileCreateHasPetScreen(
            {}, {}
        )
    }
}