package com.lanpet.profile.screen.yespet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.CommonHint
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.TextFieldWithDeleteButton
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.profile.R
import com.lanpet.profile.viewmodel.PetProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.profile.widget.ImagePickerView
import kotlinx.coroutines.launch
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateYesPetNameScreen(
    petProfileCreateViewModel: PetProfileCreateViewModel,
    modifier: Modifier = Modifier,
    onNavigateToPetCategory: () -> Unit = { },
) {
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    val validationResult =
        petProfileCreateViewModel.petProfileCreateValidationResult.collectAsState()

    val petProfileCreate = petProfileCreateViewModel.petProfileCreate.collectAsState()

    val isNicknameDuplicated =
        petProfileCreateViewModel.isNicknameDuplicated.collectAsState()

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
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.vertical,
                    ),
        ) {
            Column(
                modifier =
                    Modifier.scrollable(
                        state = scrollState,
                        orientation = Orientation.Vertical,
                    ),
            ) {
                Spacer(Modifier.padding(LanPetDimensions.Spacing.medium))
                Heading(title = stringResource(R.string.heading_profile_create_yes_pet_name))
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_yes_pet_name))
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xLarge))
                ImagePickSection { uri ->
                    petProfileCreateViewModel.setProfileImageUri(uri.toString())
                }
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xLarge))
                PetNameInputSection(
                    nickNameValidationStatus = validationResult.value.nickName,
                    duplicateCheckState = isNicknameDuplicated.value,
                    onTextChange = { name ->
                        petProfileCreateViewModel.clearNicknameDuplicated()
                        petProfileCreateViewModel.setNickName(name)
                    },
                    nicknameInput = petProfileCreate.value.nickName,
                    onCheckNicknameDuplicate = {
                        scope.launch {
                            petProfileCreateViewModel.checkNickNameDuplicate()
                        }
                    },
                )
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                    if (validationResult.value.nickName !is FormValidationStatus.Valid) return@CommonButton
                    if (isNicknameDuplicated.value == false || isNicknameDuplicated.value == null) return@CommonButton

                    onNavigateToPetCategory()
                }
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            }
        }
    }
}

@Composable
fun ImagePickSection(
    modifier: Modifier = Modifier,
    onImageSelect: (Uri) -> Unit = { },
) {
    var imageUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            uri?.let {
                imageUri = it
                onImageSelect(it)
            }
        }

    ImagePickerView(
        imageUri = imageUri,
    ) {
        launcher.launch("image/*")
    }
}

@Composable
fun CheckDuplicateNicknameButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xxSmall))
        CommonButton(
            title = stringResource(R.string.check_duplicated_nickname_button_string),
            onClick = onClick,
        )
    }
}

@Composable
fun PetNameInputSection(
    nickNameValidationStatus: FormValidationStatus,
    nicknameInput: String,
    modifier: Modifier = Modifier,
    duplicateCheckState: Boolean? = null,
    onTextChange: (String) -> Unit = {},
    onCheckNicknameDuplicate: () -> Unit = {},
) {
    Column {
        Spacer(modifier = Modifier.padding(bottom = LanPetDimensions.Spacing.small))
        Row {
            TextFieldWithDeleteButton(
                modifier = Modifier.weight(3f),
                value = nicknameInput,
                placeholder = stringResource(R.string.name_input_placeholder_profile_create_yes_pet_name),
            ) {
                onTextChange(it)
            }
            Spacer(modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xxSmall))
            CheckDuplicateNicknameButton(
                modifier = Modifier.width(112.dp),
                onClick = {
                    onCheckNicknameDuplicate()
                },
            )
        }
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
                DuplicatedNicknameOkText()
            } else {
                DuplicatedNicknameErrorText()
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
private fun PreviewProfileCreateYesPetNameScreen() {
    LanPetAppTheme {
        ProfileCreateYesPetNameScreen(
            petProfileCreateViewModel = hiltViewModel(),
        )
    }
}
