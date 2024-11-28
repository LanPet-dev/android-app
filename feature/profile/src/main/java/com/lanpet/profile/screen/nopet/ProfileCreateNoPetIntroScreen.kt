package com.lanpet.profile.screen.nopet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.crop
import com.lanpet.core.designsystem.theme.widgets.CommonButton
import com.lanpet.core.designsystem.theme.widgets.LanPetTopAppBar
import com.lanpet.profile.R
import com.lanpet.profile.widget.Heading
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateNoPetIntroScreen(
    onNavigateToNoPetNameScreen: () -> Unit,
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {},
                actions = {
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.Layout.vertical
                )
        ) {
            val desc1 = stringResource(R.string.desc1_profile_create_intro_no_pet)
            val desc2 = stringResource(R.string.desc2_profile_create_intro_no_pet)

            Column {
                Spacer(Modifier.padding(LanPetDimensions.Spacing.medium))
                Heading(title = stringResource(R.string.heading_profile_create_intro_no_pet))
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xxxLarge))
                ImageSection()
                Spacer(Modifier.padding(LanPetDimensions.Spacing.large))
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = GrayColor.LIGHT_MEDIUM,
                            ).toSpanStyle(),
                        ) {
                            append(
                                desc1 + "\n"
                            )
                        }
                        withStyle(
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            ).toSpanStyle()
                        ) {
                            append(desc2)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = TextStyle(textAlign = TextAlign.Center, lineHeight = 24.sp)
                )
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(R.string.appbar_title_profile_create)) {
                    onNavigateToNoPetNameScreen()
                }
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            }
        }
    }
}

@Composable
fun ImageSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // 나(집사) 이미지
        Image(
            painter = painterResource(DS_R.drawable.img_dummy),
            contentDescription = null,
            modifier = Modifier
                .crop(100.dp)
        )
        Text(
            " > ",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        // 반려동물 이미지
        Image(
            painter = painterResource(DS_R.drawable.img_dummy),
            contentDescription = null,
            modifier = Modifier
                .crop(100.dp)
        )
    }
}


@PreviewLightDark
@Composable
fun PreviewProfileCreateNoPetIntroScreen() {
    LanPetAppTheme {
        ProfileCreateNoPetIntroScreen({})
    }
}