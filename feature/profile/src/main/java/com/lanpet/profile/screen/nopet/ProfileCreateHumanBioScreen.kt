package com.lanpet.profile.screen.nopet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.profile.R
import com.lanpet.profile.viewmodel.ManProfileCreateViewModel
import com.lanpet.profile.viewmodel.RegisterManProfileResult
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileCreateHumanBioScreen(
    manProfileCreateViewModel: ManProfileCreateViewModel,
    modifier: Modifier = Modifier,
    onNavigateToFinish: () -> Unit = { },
) {
    val registerManProfileResult by
        manProfileCreateViewModel.registerManProfileResult.collectAsState()

    val currentOnNavigateToFinish by rememberUpdatedState {
        onNavigateToFinish()
    }

    LaunchedEffect(registerManProfileResult) {
        when (registerManProfileResult) {
            is RegisterManProfileResult.Success -> {
                currentOnNavigateToFinish()
            }

            is RegisterManProfileResult.Error -> TODO()
            RegisterManProfileResult.Initial -> TODO()
            RegisterManProfileResult.Loading -> TODO()
        }
    }

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(stringResource(R.string.appbar_title_profile_create))
                },
                actions = {
                    Text("4/4 ")
                },
            )
        },
    ) {
        Box(
            modifier =
                Modifier
                    .padding(it)
                    .padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.vertical,
                    ),
        ) {
            Column {
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                Heading(title = stringResource(R.string.heading_profile_create_pet_human_no_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_human_bio_no_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                BioInputSection {
                    manProfileCreateViewModel.setBio(it)
                }
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                    manProfileCreateViewModel.registerManProfile()
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            }
        }
    }
}

@Composable
fun BioInputSection(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
) {
    var input by rememberSaveable {
        mutableStateOf("")
    }

    val maxLength = 50

    Box {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = input,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
            minLines = 7,
            colors =
                OutlinedTextFieldDefaults.colors().copy(
                    unfocusedIndicatorColor = GrayColor.LIGHT,
                    focusedIndicatorColor = GrayColor.LIGHT,
                    disabledIndicatorColor = GrayColor.LIGHT,
                    focusedPlaceholderColor = GrayColor.MEDIUM,
                    unfocusedPlaceholderColor = GrayColor.MEDIUM,
                    disabledPlaceholderColor = GrayColor.MEDIUM,
                    cursorColor = GrayColor.MEDIUM,
                    focusedContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                    unfocusedContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                    disabledContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                    errorContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                ),
            singleLine = false,
            onValueChange = { newText ->
                if (newText.length <= maxLength) {
                    input = newText
                    onTextChange(newText)
                }
            },
            placeholder = {
                Text(
                    stringResource(R.string.bio_input_placeholder_profile_create_human_bio_no_pet),
                    style = MaterialTheme.typography.bodyMedium.copy(color = GrayColor.LIGHT),
                )
            },
        )

        Box(
            modifier =
                Modifier
                    .matchParentSize()
                    .padding(bottom = 16.dp, end = 16.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Text(
                text = "${input.length}/$maxLength",
                style = MaterialTheme.typography.bodySmall.copy(color = GrayColor.LIGHT),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewProfileCreateHumanBioScreen() {
    LanPetAppTheme {
        ProfileCreateHumanBioScreen(
            manProfileCreateViewModel = hiltViewModel(),
        )
    }
}
