package com.lanpet.profile.screen.yespet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.CommonSubHeading1
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.TextFieldWithDeleteButton
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.profile.R
import com.lanpet.profile.viewmodel.PetProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.profile.widget.ImagePickerView
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateYesPetNameScreen(
    petProfileCreateViewModel: PetProfileCreateViewModel,
    modifier: Modifier = Modifier,
    onNavigateToPetCategory: () -> Unit = { },
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
            Column {
                Spacer(Modifier.padding(LanPetDimensions.Spacing.medium))
                Heading(title = stringResource(R.string.heading_profile_create_yes_pet_name))
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_yes_pet_name))
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xLarge))
                ImagePickSection { uri ->
                    petProfileCreateViewModel.setProfileImageUri(uri.toString())
                }
                Spacer(Modifier.padding(LanPetDimensions.Spacing.xLarge))
                PetNameInputSection { name ->
                    petProfileCreateViewModel.setNickName(name)
                }
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(DS_R.string.next_button_string)) {
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
fun PetNameInputSection(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
) {
    var nameInput by rememberSaveable {
        mutableStateOf("")
    }

    CommonSubHeading1(
        title = stringResource(R.string.name_input_label_profile_create_yes_pet_name),
    )
    Spacer(modifier = Modifier.padding(bottom = LanPetDimensions.Spacing.small))
    TextFieldWithDeleteButton(
        value = nameInput,
        placeholder = stringResource(R.string.name_input_placeholder_profile_create_yes_pet_name),
    ) {
        nameInput = it
        onTextChange(it)
    }
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
