package com.lanpet.profile.screen.nopet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lanpet.core.common.widget.CommonButton
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
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateNoPetNameScreen(
    manProfileCreateViewModel: ManProfileCreateViewModel,
    modifier: Modifier = Modifier,
    onNavigateToHumanAge: () -> Unit = {},
) {
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
            ImagePickSection { uri ->
                manProfileCreateViewModel.setProfileImageUri(uri.toString())
            }
            Spacer(Modifier.padding(LanPetDimensions.Spacing.xLarge))
            PetNameInputSection { nickName ->
                manProfileCreateViewModel.setNickName(nickName)
            }
            Spacer(Modifier.weight(1f))
            CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                onNavigateToHumanAge()
            }
            Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
        }
    }
}

@Composable
fun ImagePickSection(onImageSelect: (Uri) -> Unit) {
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
fun PetNameInputSection(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
) {
    var nameInput by rememberSaveable {
        mutableStateOf("")
    }

    var nicknameDuplicateCheckStatus by rememberSaveable {
        mutableStateOf<Boolean?>(true)
    }

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
                value = nameInput,
                placeholder = stringResource(R.string.name_input_placeholder_profile_create_no_pet_name),
            ) {
                nameInput = it
                onTextChange(it)
            }
            Spacer(modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xxSmall))
            CommonButton(
                modifier = Modifier.width(100.dp),
                title = stringResource(R.string.check_duplicated_nickname_button_string),
                onClick = {
                },
            )
        }
        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
        nicknameDuplicateCheckStatus?.let {
            if (it) {
                DuplicatedNicknameOkText()
            } else {
                DuplicatedNicknameErrorText()
            }
        }
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
            DuplicatedNicknameErrorText()
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewPetNameInputSection() {
    LanPetAppTheme {
        PetNameInputSection(hiltViewModel())
    }
}

@PreviewLightDark
@Composable
private fun PreviewProfileCreateNesPetNameScreen() {
    LanPetAppTheme {
        ProfileCreateNoPetNameScreen(
            manProfileCreateViewModel = hiltViewModel(),
        ) {}
    }
}
