package com.lanpet.myprofile.screen

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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.auth.BasePreviewWrapper
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.ArrowLeft
import com.lanpet.core.common.widget.CommonHeading
import com.lanpet.core.common.widget.CommonIconButtonBox
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.myprofile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileAddProfileEntryScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    onNavigateToAddPetProfile: () -> Unit = {},
    onNavigateToAddManProfile: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {},
                navigationIcon = {
                    CommonIconButtonBox(
                        content = {
                            Image(
                                imageVector = MyIconPack.ArrowLeft,
                                contentDescription = "ic_arrow_left",
                                modifier = Modifier.size(28.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.customColorScheme.defaultIconColor),
                            )
                        },
                        onClick = { onNavigateUp() },
                    )
                },
            )
        },
    ) {
        Surface {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(
                            horizontal = LanPetDimensions.Margin.Layout.horizontal,
                            vertical = LanPetDimensions.Margin.Layout.vertical,
                        ),
            ) {
                Spacer(Modifier.weight(0.1f))
                CommonHeading(title = stringResource(R.string.heading_add_profile_entry))
                Spacer(Modifier.weight(0.3f))
                Content(onNavigateToAddPetProfile, onNavigateToAddManProfile)
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun Content(
    onNavigateToYesPetScreen: () -> Unit,
    onNavigateToNoPetScreen: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier.Companion
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        HasPetSelectButton(
            onClick = onNavigateToYesPetScreen,
            painterResource = painterResource(com.lanpet.core.common.R.drawable.img_has_pet),
            title = stringResource(R.string.title_add_pet_profile),
        )
        Spacer(Modifier.padding(horizontal = LanPetDimensions.Margin.small))
        HasPetSelectButton(
            painterResource = painterResource(com.lanpet.core.common.R.drawable.img_no_pet),
            onClick = onNavigateToNoPetScreen,
            title = stringResource(R.string.title_add_man_profile),
        )
    }
}

@Composable
fun HasPetSelectButton(
    title: String,
    painterResource: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(LanPetDimensions.Corner.small))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.customColorScheme.spacerLine,
                    shape = RoundedCornerShape(LanPetDimensions.Corner.small),
                ).clickable(
                    onClick = onClick,
                ).padding(
                    horizontal = LanPetDimensions.Margin.xLarge,
                    vertical = LanPetDimensions.Margin.xxLarge,
                ),
    ) {
        Image(
            painter = painterResource,
            contentDescription = null,
            modifier =
                Modifier
                    .size(
                        110.dp,
                    ).clip(
                        RoundedCornerShape(LanPetDimensions.Corner.small),
                    ),
        )
        Spacer(Modifier.padding(vertical = LanPetDimensions.Margin.small))
        Text(title, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
@PreviewLightDark
private fun MyProfileAddProfileEntryScreenPreview() {
    BasePreviewWrapper {
        MyProfileAddProfileEntryScreen()
    }
}
