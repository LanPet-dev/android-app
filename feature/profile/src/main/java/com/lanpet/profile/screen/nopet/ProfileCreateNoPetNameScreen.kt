package com.lanpet.profile.screen.nopet

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.createProfileImageUri
import com.lanpet.core.common.rememberCameraPermissionLauncher
import com.lanpet.core.common.rememberCameraTakePictureLauncher
import com.lanpet.core.common.rememberGalleryLauncher
import com.lanpet.core.common.widget.ActionButton
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.CommonHint
import com.lanpet.core.common.widget.IOSActionSheet
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.TextFieldWithDeleteButton
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.profile.R
import com.lanpet.profile.viewmodel.ManProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.profile.widget.ImagePickerView
import kotlinx.coroutines.launch
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateNoPetNameScreen(
    manProfileCreateViewModel: ManProfileCreateViewModel,
    modifier: Modifier = Modifier,
    onNavigateToHumanAge: () -> Unit = {},
) {
    val duplicateCheckState by manProfileCreateViewModel.isNicknameDuplicated.collectAsState()
    val validationStatus by manProfileCreateViewModel.manProfileCreateValidationResult.collectAsState()
    val manProfileCreate by manProfileCreateViewModel.manProfileCreate.collectAsState()

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(stringResource(R.string.appbar_title_profile_create))
                },
                actions = {
                    Text("1/4 ")
                },
            )
        },
    ) {
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
            Spacer(Modifier.padding(LanPetDimensions.Spacing.medium))
            Heading(title = stringResource(R.string.heading_profile_create_no_pet_name))
            Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            HeadingHint(title = stringResource(R.string.sub_heading_profile_create_no_pet_name))
            Spacer(Modifier.padding(LanPetDimensions.Spacing.xLarge))
            ImagePickSection(
                profileImageUri = manProfileCreate.profileImageUri,
            ) { uri ->
                manProfileCreateViewModel.setProfileImageUri(uri.toString())
            }
            Spacer(Modifier.padding(LanPetDimensions.Spacing.xLarge))
            PetNameInputSection(
                nicknameInput = manProfileCreate.nickName,
                nickNameValidationStatus = validationStatus.nickName,
                duplicateCheckState = duplicateCheckState,
                onTextChange = { nickName ->
                    manProfileCreateViewModel.clearNicknameDuplicated()
                    manProfileCreateViewModel.setNickName(nickName)
                },
                onCheckNicknameDuplicate = {
                    scope.launch {
                        manProfileCreateViewModel.checkNickNameDuplicate()
                    }
                },
            )
            Spacer(Modifier.weight(1f))
            CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                if (validationStatus.nickName !is FormValidationStatus.Valid) return@CommonButton
                if (duplicateCheckState != true) return@CommonButton

                onNavigateToHumanAge()
            }
            Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickSection(
    profileImageUri: Uri?,
    modifier: Modifier = Modifier,
    onImageSelect: (Uri) -> Unit = { },
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var imageUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }

    // 갤러리 launcher
    val galleryLauncher =
        rememberGalleryLauncher(
            onGetUri = { uri ->
                uri?.let {
                    imageUri = it
                    onImageSelect(it)
                }
            },
        )

    // 카메라 launcher
    val cameraLauncher =
        rememberCameraTakePictureLauncher { success ->
            if (success) {
                imageUri?.let { uri ->
                    onImageSelect(uri)
                }
            }
        }

    // 카메라 권한 launcher
    val cameraPermissionLauncher =
        rememberCameraPermissionLauncher(
            onGrant = {
                val uri = context.createProfileImageUri()
                imageUri = uri
                cameraLauncher.launch(uri)
            },
            onDeny = { Toast.makeText(context, "카메라 권한이 필요합니다", Toast.LENGTH_SHORT).show() },
        )

    ImagePickerView(
        imageUri = profileImageUri,
    ) {
        scope.launch {
            sheetState.show()
        }
    }

    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {},
            containerColor = Color.Transparent,
        ) {
            IOSActionSheet(
                cancelButton = {
                    ActionButton(
                        text = stringResource(R.string.title_button_close),
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                            }
                        },
                    )
                },
                content = {
                    Column {
                        ActionButton(
                            text = stringResource(R.string.title_button_camera),
                            onClick = {
                                // TODO open camera
                                scope.launch {
                                    sheetState.hide()
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                        )
                        ActionButton(
                            text = stringResource(R.string.title_button_gallery),
                            onClick = {
                                scope.launch {
                                    sheetState.hide()
                                    galleryLauncher.launch("image/*")
                                }
                            },
                        )
                    }
                },
            )
        }
    }
}

@Composable
fun PetNameInputSection(
    nickNameValidationStatus: FormValidationStatus,
    duplicateCheckState: Boolean?,
    nicknameInput: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
    onCheckNicknameDuplicate: () -> Unit = {},
) {
    Column {
        Text(
            stringResource(R.string.name_input_label_profile_create_no_pet_name),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.padding(bottom = LanPetDimensions.Spacing.small))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextFieldWithDeleteButton(
                modifier = Modifier.weight(3f),
                value = nicknameInput,
                placeholder = stringResource(R.string.name_input_placeholder_profile_create_no_pet_name),
            ) {
                onTextChange(it)
            }
            Spacer(modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xxSmall))
            CommonButton(
                modifier = Modifier.width(100.dp),
                title = stringResource(R.string.check_duplicated_nickname_button_string),
                onClick = {
                    onCheckNicknameDuplicate()
                },
            )
        }
        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))

        // 표시할 텍스트가 있는경우
        if (nickNameValidationStatus is FormValidationStatus.Invalid) {
            if (nickNameValidationStatus.message != null) {
                Spacer(modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xSmall))
                Text(
                    nickNameValidationStatus.message.toString(),
                    style = MaterialTheme.customTypography().body3MediumSingle.copy(color = MaterialTheme.customColorScheme.errorText),
                )
            }
        }

        // 중복체크 결과가 있는경우
        if (duplicateCheckState != null) {
            Spacer(modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xSmall))
            if (duplicateCheckState) {
                com.lanpet.profile.screen.yespet
                    .DuplicatedNicknameOkText()
            } else {
                com.lanpet.profile.screen.yespet
                    .DuplicatedNicknameErrorText()
            }
        }

        Spacer(modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xSmall))
        CommonHint(
            title = stringResource(R.string.hint_nickname_validator_profile_create),
        )
    }
}

@Composable
fun DuplicatedNicknameOkText(modifier: Modifier = Modifier) {
    Text(
        stringResource(R.string.check_duplicated_nickname_ok_string),
        style = MaterialTheme.customTypography().body3MediumSingle.copy(color = MaterialTheme.customColorScheme.confirmText),
    )
}

@Composable
fun DuplicatedNicknameErrorText(modifier: Modifier = Modifier) {
    Text(
        stringResource(R.string.check_duplicated_nickname_error_string),
        style = MaterialTheme.customTypography().body3MediumSingle.copy(color = MaterialTheme.customColorScheme.errorText),
    )
}

@PreviewLightDark
@Composable
private fun PreviewErrorConfirmedText(modifier: Modifier = Modifier) {
    LanPetAppTheme {
        Column {
            DuplicatedNicknameOkText()
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
            DuplicatedNicknameErrorText()
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewPetNameInputSection_DuplicatedCheckStateIsNull() {
    LanPetAppTheme {
        PetNameInputSection(
            nickNameValidationStatus = FormValidationStatus.Valid("valid"),
            duplicateCheckState = null,
            nicknameInput = "input",
            onTextChange = {},
            onCheckNicknameDuplicate = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewPetNameInputSection_DuplicatedCheckStateIsFalse() {
    LanPetAppTheme {
        PetNameInputSection(
            nickNameValidationStatus = FormValidationStatus.Valid("valid"),
            duplicateCheckState = false,
            nicknameInput = "input",
            onTextChange = {},
            onCheckNicknameDuplicate = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewPetNameInputSection_DuplicatedCheckStateIsTrue() {
    LanPetAppTheme {
        PetNameInputSection(
            nickNameValidationStatus = FormValidationStatus.Valid("valid"),
            duplicateCheckState = true,
            nicknameInput = "input",
            onTextChange = {},
            onCheckNicknameDuplicate = {},
        )
    }
}
