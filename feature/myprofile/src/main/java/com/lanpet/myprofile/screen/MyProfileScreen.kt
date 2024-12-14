package com.lanpet.myprofile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
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
import com.lanpet.core.common.crop
import com.lanpet.core.common.myiconpack.ArrowRight
import com.lanpet.core.common.myiconpack.Bookmark
import com.lanpet.core.common.myiconpack.File
import com.lanpet.core.common.myiconpack.Message
import com.lanpet.core.common.myiconpack.Setting
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.myprofile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateToProfileCreate: () -> Unit = { },
    onNavigateToSettings: () -> Unit = { },
    onNavigateToMyPosts: () -> Unit = { },
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(
                        style = MaterialTheme.customTypography().title2SemiBoldSingle,
                        text = stringResource(R.string.title_appbar_my_profile),
                    )
                },
                actions = {
                    Box(
                        modifier =
                            Modifier
                                .crop(
                                    48.dp,
                                ) {
                                    onNavigateToSettings()
                                },
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            imageVector = MyIconPack.Setting,
                            contentDescription = "Setting",
                            colorFilter =
                                ColorFilter.tint(
                                    MaterialTheme.customColorScheme.defaultIconColor,
                                ),
                        )
                    }
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
            Column {
                MyProfileCard(
                    onNavigateToProfileCreate = onNavigateToProfileCreate,
                )
                Spacer(
                    modifier =
                        Modifier
                            .padding(bottom = LanPetDimensions.Spacing.xLarge)
                            .fillMaxWidth()
                            .size(LanPetDimensions.Spacing.xxxSmall)
                            .background(
                                color = MaterialTheme.customColorScheme.spacerLine,
                            ),
                )
                Text(
                    style = MaterialTheme.customTypography().title2SemiBoldSingle,
                    text = stringResource(R.string.sub_title_activity_list_my_profile),
                    modifier =
                        Modifier.padding(
                            horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        ),
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
                Column {
                    ActivityListItem(
                        leadingContent = {
                            Image(
                                imageVector = MyIconPack.File,
                                contentDescription = "ic_file",
                                colorFilter =
                                    ColorFilter.tint(
                                        MaterialTheme.customColorScheme.defaultIconColor,
                                    ),
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        headlineContentText = stringResource(R.string.my_profile_label_my_post),
                        onclick = {
                            onNavigateToMyPosts()
                        },
                    )
                    ActivityListItem(
                        leadingContent = {
                            Image(
                                imageVector = MyIconPack.Message,
                                contentDescription = "ic_message",
                                colorFilter =
                                    ColorFilter.tint(
                                        MaterialTheme.customColorScheme.defaultIconColor,
                                    ),
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        headlineContentText = stringResource(R.string.my_profile_label_my_comment),
                        onclick = { },
                    )
                    ActivityListItem(
                        leadingContent = {
                            Image(
                                imageVector = MyIconPack.Bookmark,
                                contentDescription = "ic_bookmark",
                                colorFilter =
                                    ColorFilter.tint(
                                        MaterialTheme.customColorScheme.defaultIconColor,
                                    ),
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        headlineContentText = stringResource(R.string.my_profile_label_bookmark),
                        onclick = { },
                    )
                }
            }
        }
    }
}

@Composable
private fun MyProfileCard(onNavigateToProfileCreate: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.xxLarge,
                ),
    ) {
        Image(
            modifier =
                Modifier.crop(
                    size = 88.dp,
                ),
            painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_animals),
            contentDescription = "Profile Picture",
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
        Column {
            Text(
                style = MaterialTheme.customTypography().title2SemiBoldSingle,
                text = "John Doe",
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Text(
                style = MaterialTheme.customTypography().body1RegularSingle,
                text = "Hello I am John Doe",
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier =
                Modifier
                    .crop(
                        size = 36.dp,
                    ) {
                        onNavigateToProfileCreate()
                    },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                imageVector = MyIconPack.ArrowRight,
                contentDescription = "ic_arrow_right",
            )
        }
    }
}

@Composable
fun ActivityListItem(
    headlineContentText: String,
    modifier: Modifier = Modifier,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable () -> Unit =
        {
            Image(
                imageVector = MyIconPack.ArrowRight,
                contentDescription = "ic_arrow_right",
            )
        },
    onclick: () -> Unit = {},
) {
    ListItem(
        leadingContent = { leadingContent?.invoke() },
        headlineContent = {
            Text(
                style = MaterialTheme.customTypography().body2MediumSingle,
                text = headlineContentText,
            )
        },
        trailingContent = {
            trailingContent()
        },
        modifier = Modifier.clickable(onClick = onclick),
    )
}

@Composable
@PreviewLightDark()
private fun MyProfileCardPreview() {
    LanPetAppTheme {
        Surface {
            MyProfileCard {}
        }
    }
}

@Composable
@PreviewLightDark()
private fun MyProfileScreenPreview() {
    LanPetAppTheme {
        MyProfileScreen()
    }
}
