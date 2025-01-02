package com.lanpet.myprofile.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lanpet.core.auth.BasePreviewWrapper
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.commonBorder
import com.lanpet.core.common.crop
import com.lanpet.core.common.myiconpack.ArrowLeft
import com.lanpet.core.common.myiconpack.Plus
import com.lanpet.core.common.widget.CommonHeading
import com.lanpet.core.common.widget.CommonHeadingHint
import com.lanpet.core.common.widget.CommonIconButtonBox
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.ProfileImage
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.UserProfile
import com.lanpet.myprofile.R
import com.lanpet.myprofile.viewmodel.ProfileListViewModel
import com.lanpet.myprofile.widget.DeleteProfileDialog
import com.lanpet.myprofile.widget.SetDefaultProfileDialog
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileCreateProfileScreen(
    modifier: Modifier = Modifier,
    profileListViewModel: ProfileListViewModel = hiltViewModel(),
    onNavigateUp: (() -> Unit)? = null,
    onNavigateToAddProfileEntry: () -> Unit = {},
    onNavigateToModifyProfile: (String, ProfileType) -> Unit = { profileId, profileType -> },
) {
    val authManager = LocalAuthManager.current
    val defaultUserProfile by authManager.defaultUserProfile.collectAsStateWithLifecycle()
    val userProfiles by authManager.userProfiles.collectAsStateWithLifecycle()
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    var showSetDefaultProfileDialog by remember { mutableStateOf(false) }
    var showDeleteProfileDialog by remember { mutableStateOf(false) }
    var selectedProfileId by remember { mutableStateOf<String>("") }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    if (showDeleteProfileDialog) {
        Dialog(
            onDismissRequest = { showDeleteProfileDialog = false },
            properties =
                DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
        ) {
            DeleteProfileDialog(
                onDismiss = { showDeleteProfileDialog = false },
                onSetDefaultProfile = {
                    Timber.d("selectedProfileId: $selectedProfileId")
                    scope.launch {
                        try {
                            if (selectedProfileId.isEmpty()) {
                                showDeleteProfileDialog = false
                                return@launch
                            }

                            profileListViewModel.deleteProfile(selectedProfileId)
                            showDeleteProfileDialog = false
                        } catch (e: Exception) {
                            Timber.e(e)
                            showDeleteProfileDialog = false
                        }
                    }
                },
            )
        }
    }

    if (showSetDefaultProfileDialog) {
        Dialog(
            onDismissRequest = { showSetDefaultProfileDialog = false },
            properties =
                DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
        ) {
            SetDefaultProfileDialog(
                onDismiss = { showSetDefaultProfileDialog = false },
                onSetDefaultProfile = {
                    Timber.d("selectedProfileId: $selectedProfileId")
                    scope.launch {
                        if (selectedProfileId.isEmpty()) {
                            return@launch
                        }

                        authManager.updateUserProfile(
                            selectedProfileId,
                        )

                        showSetDefaultProfileDialog = false
                    }
                },
            )
        }
    }

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                navigationIcon = {
                    if (onNavigateUp != null) {
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
                    }
                },
                title = {
                    Text(
                        stringResource(R.string.title_appbar_my_profile_create),
                    )
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
                        state = scrollState,
                    ).padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.vertical,
                    ),
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
                if (!isExpanded) {
                    TextButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            isExpanded = true
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.title_button_do_edit_profile),
                            style = MaterialTheme.customTypography().body1SemiBoldSingle,
                        )
                    }
                } else {
                    TextButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            isExpanded = false
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.title_button_end_edit_profile),
                            style = MaterialTheme.customTypography().body1SemiBoldSingle,
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                ProfileListCard(
                    userProfile = defaultUserProfile,
                    isActive = true,
                    isExpanded = isExpanded,
                    onModifyClick = {
                        onNavigateToModifyProfile(defaultUserProfile.id, defaultUserProfile.type)
                    },
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                userProfiles
                    .filterNot { profile ->
                        profile.id == defaultUserProfile.id
                    }.forEach { userProfile ->
                        Timber.d("userProfile: $userProfile")
                        ProfileListCard(
                            isExpanded = isExpanded,
                            userProfile = userProfile,
                            isActive = false,
                            onProfileClick = {
                                showSetDefaultProfileDialog = true
                                selectedProfileId = userProfile.id
                            },
                            onModifyClick = {
                                onNavigateToModifyProfile(userProfile.id, userProfile.type)
                            },
                            onDeleteClick = {
                                selectedProfileId = userProfile.id
                                showDeleteProfileDialog = true
                            },
                        )
                        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                    }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
                if (userProfiles.size < 3) {
                    AddProfileButton { onNavigateToAddProfileEntry() }
                }
            }
        }
    }
}

@Composable
fun AddProfileButton(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    onAddProfileClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    if (isActive) MaterialTheme.customColorScheme.buttonBackground else MaterialTheme.customColorScheme.buttonBackgroundDisabled,
                    shape = RoundedCornerShape(LanPetDimensions.Corner.medium),
                ).clip(RoundedCornerShape(LanPetDimensions.Corner.medium))
                .clickable(
                    enabled = isActive,
                ) {
                    onAddProfileClick()
                }.padding(
                    horizontal = LanPetDimensions.Margin.medium,
                    vertical = LanPetDimensions.Margin.medium,
                ),
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier =
                Modifier.crop(
                    size = 18.dp,
                ),
            imageVector = MyIconPack.Plus,
            colorFilter =
                if (isActive) {
                    ColorFilter.tint(MaterialTheme.customColorScheme.buttonText)
                } else {
                    ColorFilter.tint(
                        MaterialTheme.customColorScheme.buttonTextDisabled,
                    )
                },
            contentDescription = "Add Profile Picture",
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
        Column {
            Text(
                style = MaterialTheme.customTypography().body2SemiBoldSingle,
                text = stringResource(R.string.title_add_profile_button),
                color =
                    if (isActive) {
                        MaterialTheme.customColorScheme.buttonText
                    } else {
                        MaterialTheme.customColorScheme.buttonTextDisabled
                    },
            )
        }
    }
}

@Composable
fun ProfileListCard(
    userProfile: UserProfile,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    onProfileClick: () -> Unit = {},
    onModifyClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .commonBorder(
                    border =
                        if (isActive) {
                            BorderStroke(
                                1.dp,
                                PrimaryColor.PRIMARY,
                            )
                        } else {
                            BorderStroke(0.dp, GrayColor.Gray400)
                        },
                    shape =
                        RoundedCornerShape(
                            LanPetDimensions.Corner.small,
                        ),
                ).clickable {
                    if (isActive) return@clickable
                    onProfileClick()
                },
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .padding(
                            horizontal = LanPetDimensions.Margin.medium,
                            vertical = LanPetDimensions.Margin.medium,
                        ),
            ) {
                ProfileImage(
                    profileType = userProfile.type,
                    imageUri = userProfile.profileImageUri,
                    size = 88.dp,
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                Column {
                    Text(
                        style = MaterialTheme.customTypography().body1SemiBoldSingle,
                        text = userProfile.nickname,
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                    Text(
                        style = MaterialTheme.customTypography().body2MediumSingle,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        text = userProfile.introduction.toString(),
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter =
                    expandVertically(
                        animationSpec =
                            spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessLow,
                            ),
                    ) + fadeIn(),
                exit =
                    shrinkVertically(
                        animationSpec =
                            spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessLow,
                            ),
                    ) + fadeOut(),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(LanPetDimensions.Margin.Layout.horizontal),
                ) {
                    ProfileBaseButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.title_manage_profile_button_2),
                        onClick = {
                            onModifyClick()
                        },
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                    ProfileBaseButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.title_delete_profile_button),
                        onClick = {
                            onDeleteClick()
                        },
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAddProfileCard() {
    BasePreviewWrapper {
        Column {
            AddProfileButton()
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
            AddProfileButton(isActive = false)
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewMyProfileCreate() {
    BasePreviewWrapper {
        Column {
            ProfileListCard(
                userProfile =
                    UserProfile(
                        nickname = "닉네임",
                        introduction = "소개",
                        id = "id",
                        type = ProfileType.PET,
                        isDefault = true,
                        profileImageUri = null,
                    ),
                isActive = true,
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
            ProfileListCard(
                userProfile =
                    UserProfile(
                        nickname = "닉네임",
                        introduction = "소개",
                        id = "id",
                        type = ProfileType.PET,
                        isDefault = true,
                        profileImageUri = null,
                    ),
                isActive = false,
            )

            ProfileListCard(
                userProfile =
                    UserProfile(
                        nickname = "닉네임",
                        introduction = "소개",
                        id = "id",
                        type = ProfileType.PET,
                        isDefault = true,
                        profileImageUri = null,
                    ),
                isExpanded = true,
                isActive = true,
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
            ProfileListCard(
                userProfile =
                    UserProfile(
                        nickname = "닉네임",
                        introduction = "소개",
                        id = "id",
                        type = ProfileType.PET,
                        isDefault = true,
                        profileImageUri = null,
                    ),
                isExpanded = true,
                isActive = false,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun MyProfileCreatePreview() {
    BasePreviewWrapper {
        MyProfileCreateProfileScreen(
            onNavigateUp = { },
        )
    }
}
