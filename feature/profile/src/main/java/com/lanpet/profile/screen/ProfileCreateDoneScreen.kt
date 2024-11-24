package com.lanpet.profile.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.theme.LanPetAppTheme
import com.example.designsystem.theme.LanPetDimensions
import com.example.designsystem.theme.widgets.CommonButton
import com.example.profile.R
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.example.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateDoneScreen() {
    Scaffold(
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.Layout.vertical
                )
        ) {
            Column(
                Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxLarge))
                Heading(title = stringResource(R.string.heading_profile_create_done1))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                Heading(title = stringResource(R.string.heading_profile_create_done2))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.medium))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_done))
                Spacer(Modifier.weight(1f))
                CommonButton(
                    title = stringResource(DS_R.string.start_button_string)
                ) { }
                Spacer(Modifier.padding(bottom = LanPetDimensions.Spacing.xxSmall))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewProfileCreateDoneScreen() {
    LanPetAppTheme {
        ProfileCreateDoneScreen()
    }
}
