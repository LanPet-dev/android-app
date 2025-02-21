package com.lanpet.myprofile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lanpet.core.auth.BasePreviewWrapper
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.crop
import com.lanpet.core.common.myiconpack.ArrowRight
import com.lanpet.core.common.myiconpack.Bookmark
import com.lanpet.core.common.myiconpack.File
import com.lanpet.core.common.myiconpack.Message
import com.lanpet.core.common.myiconpack.Setting
import com.lanpet.core.common.toast
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.ProfileImage
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.UserProfile
import com.lanpet.myprofile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateToProfileCreate: () -> Unit = { },
    onNavigateToProfileManage: (String, ProfileType) -> Unit = { profileId: String, profileType: ProfileType -> },
    onNavigateToSettings: () -> Unit = { },
    onNavigateToMyPosts: (String) -> Unit = { },
) {
    val context = LocalContext.current
    val authManager = LocalAuthManager.current
    val defaultUserProfile = authManager.defaultUserProfile.collectAsStateWithLifecycle()

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
                    .padding(it)
                    .verticalScroll(
                        state = rememberScrollState(),
                    ),
        ) {
            Column {
                MyProfileCard(
                    defaultUserProfile.value,
                    onNavigateToProfileCreate = {},
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxSmall))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(LanPetDimensions.Margin.Layout.horizontal),
                ) {
                    ProfileBaseButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.title_manage_profile_button),
                        onClick = {
                            onNavigateToProfileManage(
                                defaultUserProfile.value.id,
                                defaultUserProfile.value.type,
                            )
                        },
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                    ProfileBaseButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.title_profile_list_button),
                        onClick = {
                            onNavigateToProfileCreate()
                        },
                    )
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
                Spacer(
                    modifier =
                        Modifier
                            .padding(bottom = LanPetDimensions.Spacing.xLarge)
                            .fillMaxWidth()
                            .size(LanPetDimensions.Spacing.xSmall)
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
                            onNavigateToMyPosts(
                                defaultUserProfile.value.id,
                            )
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
                        onclick = {
                            context.toast("준비중 입니다")
                        },
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
                        onclick = {
                            context.toast("준비중 입니다")
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun MyProfileCard(
    myProfile: UserProfile,
    onNavigateToProfileCreate: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.large,
                ),
    ) {
        ProfileImage(
            size = 90.dp,
            profileType = myProfile.type,
            imageUri = myProfile.profileImageUri,
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
        Column {
            Text(
                style = MaterialTheme.customTypography().title2SemiBoldSingle,
                text = myProfile.nickname,
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Text(
                style = MaterialTheme.customTypography().body1RegularSingle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                text = myProfile.introduction.toString(),
            )
        }
    }
}

@Composable
fun ProfileBaseButton(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = GrayColor.Gray200,
                    shape = RoundedCornerShape(LanPetDimensions.Corner.small),
                ).clip(
                    shape = RoundedCornerShape(LanPetDimensions.Corner.small),
                ).clickable {
                    onClick()
                }.padding(
                    horizontal = LanPetDimensions.Margin.medium,
                    vertical = LanPetDimensions.Margin.medium,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            style = MaterialTheme.customTypography().body2RegularMulti,
            text = title,
        )
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
    BasePreviewWrapper {
        MyProfileCard(
            myProfile =
                UserProfile(
                    id = "id",
                    type = ProfileType.BUTLER,
                    nickname = "I am nickname",
                    profileImageUri = null,
                    introduction = "Hello I am nickname. hahaha hell o",
                ),
        ) {}
    }
}

@Composable
@PreviewLightDark()
private fun ProfileBaseButtonPreview() {
    BasePreviewWrapper {
        ProfileBaseButton(title = "Button") {}
    }
}

@Composable
@PreviewLightDark()
private fun MyProfileScreenPreview() {
    BasePreviewWrapper {
        MyProfileScreen()
    }
}
