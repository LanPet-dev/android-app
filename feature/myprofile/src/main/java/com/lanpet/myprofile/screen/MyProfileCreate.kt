package com.lanpet.myprofile.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.commonBorder
import com.lanpet.core.common.crop
import com.lanpet.core.common.myiconpack.ArrowLeft
import com.lanpet.core.common.myiconpack.Plus
import com.lanpet.core.common.widget.CommonHeading
import com.lanpet.core.common.widget.CommonHeadingHint
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.myprofile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileCreate(
    onNavigateUp: (() -> Unit)? = null
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                navigationIcon = {
                    if (onNavigateUp != null)
                        Box(
                            modifier = Modifier.crop(48.dp) { },
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                imageVector = MyIconPack.ArrowLeft,
                                contentDescription = "ic_arrow_left",
                                modifier = Modifier.size(28.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.customColorScheme.defaultIconColor),
                            )
                        }
                },
                title = {
                    Text(stringResource(R.string.title_appbar_my_profile_create))
                },
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.Layout.vertical
                )
        ) {
            Column {
                CommonHeading(
                    title = stringResource(R.string.heading_my_profile_create),
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                CommonHeadingHint(
                    title = stringResource(R.string.heading_hint_my_profile_create),
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.large))
                ProfileListCard()
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
                AddProfileCard()
            }
        }
    }
}

@Composable
fun AddProfileCard(
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .commonBorder(
                shape = RoundedCornerShape(
                    LanPetDimensions.Corner.small
                )
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(
                horizontal = LanPetDimensions.Margin.medium,
                vertical = LanPetDimensions.Margin.medium,
            )
        ) {
            Image(
                imageVector = MyIconPack.Plus,
                contentDescription = "ic_plus",
                colorFilter = ColorFilter.tint(GrayColor.Gray500),
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Text(
                style = MaterialTheme.customTypography().body2MediumSingle,
                text = stringResource(R.string.body_add_profile_profile_create),
            )
        }
    }
}

@Composable
fun ProfileListCard(
    onModifyClicked: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .commonBorder(
                border = BorderStroke(1.dp, PrimaryColor.PRIMARY),
                shape = RoundedCornerShape(
                    LanPetDimensions.Corner.small
                )
            )
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        horizontal = LanPetDimensions.Margin.medium,
                        vertical = LanPetDimensions.Margin.medium,
                    )
            ) {
                Image(
                    modifier = Modifier.crop(
                        size = 64.dp
                    ),
                    painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_animals),
                    contentDescription = "Profile Picture"
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                Column {
                    Text(
                        style = MaterialTheme.customTypography().body1SemiBoldSingle,
                        text = "John Doe",
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                    Text(
                        style = MaterialTheme.customTypography().body2MediumSingle,
                        text = "Hello I am John Doe",
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .crop(
                            size = 36.dp,
                            shape = RoundedCornerShape(
                                8
                            )
                        ) {
                            onModifyClicked()
                        }
                        .commonBorder(
                            shape = RoundedCornerShape(
                                LanPetDimensions.Corner.xSmall,
                            )
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.modify),
                        style = MaterialTheme.customTypography().body2MediumSingle.copy(
                            color = GrayColor.Gray400
                        ),
                    )
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
fun PreviewAddProfilecard() {
    LanPetAppTheme {
        AddProfileCard()
    }
}

@PreviewLightDark
@Composable
fun PreviewMyProfileCreate() {
    LanPetAppTheme {
        ProfileListCard()
    }
}

@PreviewLightDark
@Composable
fun MyProfileCreatePreview() {
    LanPetAppTheme {
        MyProfileCreate {}
    }
}