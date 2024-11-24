package com.lanpet.profile.screen.nopet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.theme.LanPetAppTheme
import com.example.designsystem.theme.LanPetDimensions
import com.example.designsystem.theme.widgets.CommonButton
import com.example.designsystem.theme.widgets.LanPetTopAppBar
import com.example.designsystem.theme.widgets.TextFieldWithDeleteButton
import com.example.profile.R
import com.lanpet.profile.viewmodel.ManProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.profile.widget.ImagePickerView
import com.example.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreateNoPetNameScreen(
    manProfileCreateViewModel: ManProfileCreateViewModel,
    onNavigateToHumanAge: () -> Unit,
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
            ImagePickSection(manProfileCreateViewModel)
            Spacer(Modifier.padding(LanPetDimensions.Spacing.xLarge))
            PetNameInputSection(manProfileCreateViewModel)
            Spacer(Modifier.weight(1f))
            CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                onNavigateToHumanAge()
            }
            Spacer(Modifier.padding(LanPetDimensions.Spacing.xxSmall))
        }
    }
}

@Composable
fun ImagePickSection(viewModel: ManProfileCreateViewModel) {
    var imageUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            uri?.let {
                imageUri = it
                viewModel.setProfileImageUri(it.toString())
            }
        }

    ImagePickerView(
        imageUri,
    ) {
        launcher.launch("image/*")
    }
}

@Composable
fun PetNameInputSection(viewModel: ManProfileCreateViewModel) {
    var nameInput by rememberSaveable {
        mutableStateOf("")
    }

    Text(
        stringResource(R.string.name_input_label_profile_create_no_pet_name),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
    )
    Spacer(modifier = Modifier.padding(bottom = LanPetDimensions.Spacing.small))
    TextFieldWithDeleteButton(
        value = nameInput,
        placeholder = stringResource(R.string.name_input_placeholder_profile_create_no_pet_name),
    ) {
        nameInput = it
        viewModel.setNickName(it)
    }
}

@PreviewLightDark
@Composable
fun PreviewProfileCreateNesPetNameScreen() {
    LanPetAppTheme {
        ProfileCreateNoPetNameScreen(
            manProfileCreateViewModel = hiltViewModel(),
        ) {}

    }
}
