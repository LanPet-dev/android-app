package com.lanpet.profile.screen.yespet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.profile.R
import com.lanpet.profile.widget.Heading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateYesPetIntroScreen(
    modifier: Modifier = Modifier,
    onNavigateToYesPetNameScreen: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {},
                actions = {
                },
            )
        },
    ) {
        Surface(
            modifier =
                Modifier
                    .padding(it)
                    .padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.vertical,
                    ),
        ) {
            val desc1 = stringResource(R.string.desc1_profile_create_intro_yes_pet)
            val desc2 = stringResource(R.string.desc2_profile_create_intro_yes_pet)

            Column {
                Spacer(Modifier.padding(LanPetDimensions.Spacing.medium))
                Heading(title = stringResource(R.string.heading_profile_create_intro_yes_pet))
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xxxLarge))
                ImageSection()
                Spacer(Modifier.padding(LanPetDimensions.Spacing.large))
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style =
                                MaterialTheme.typography.labelLarge
                                    .copy(
                                        color = GrayColor.LIGHT_MEDIUM,
                                    ).toSpanStyle(),
                        ) {
                            append(
                                desc1 + "\n",
                            )
                        }
                        withStyle(
                            style =
                                MaterialTheme.typography.labelLarge
                                    .copy(
                                        fontWeight = FontWeight.Bold,
                                    ).toSpanStyle(),
                        ) {
                            append(desc2)
                        }
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth(),
                    style = TextStyle(textAlign = TextAlign.Center, lineHeight = 24.sp),
                )
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(R.string.appbar_title_profile_create)) {
                    onNavigateToYesPetNameScreen()
                }
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            }
        }
    }
}

@Composable
fun ImageSection(modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(com.lanpet.core.common.R.drawable.img_butler),
            contentDescription = null,
            modifier =
                Modifier
                    .size(130.dp),
        )
        Text(
            " = ",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        Image(
            painter = painterResource(com.lanpet.core.common.R.drawable.img_onboarding),
            contentDescription = null,
            modifier =
                Modifier
                    .size(130.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewProfileCreateYesPetIntroScreen() {
    LanPetAppTheme {
        ProfileCreateYesPetIntroScreen()
    }
}
