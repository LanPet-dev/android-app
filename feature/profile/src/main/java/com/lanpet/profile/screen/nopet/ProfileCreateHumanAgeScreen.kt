package com.lanpet.profile.screen.nopet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.domain.model.Age
import com.lanpet.profile.R
import com.lanpet.profile.viewmodel.ManProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileCreateHumanAgeScreen(
    manProfileCreateViewModel: ManProfileCreateViewModel,
    modifier: Modifier = Modifier,
    onNavigateToPreferPet: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(stringResource(R.string.appbar_title_profile_create))
                },
                actions = {
                    Text("2/4 ")
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
                Heading(title = stringResource(R.string.heading_profile_create_human_age_no_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_human_age_no_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                AgeChipSection(
                    selectedAge =
                        manProfileCreateViewModel.manProfileCreate
                            .collectAsState()
                            .value.butler.age,
                ) { age ->
                    manProfileCreateViewModel.setAge(age)
                }
                Spacer(Modifier.weight(1f))
                CommonButton(
                    title = stringResource(DS_R.string.next_button_string),
                    isActive =
                        manProfileCreateViewModel.manProfileCreateValidationResult
                            .collectAsState()
                            .value.age is FormValidationStatus.Valid,
                ) {
                    onNavigateToPreferPet()
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun AgeChipSection(
    modifier: Modifier = Modifier,
    selectedAge: Age? = null,
    onAgeChange: (Age) -> Unit = {},
) {
    FlowRow {
        SelectableChip.Rounded(
            title = "10대",
            isSelected = selectedAge?.value == "10대",
        ) {
            onAgeChange(Age.TENS)
        }
        SelectableChip.Rounded(
            title = "20대",
            isSelected = selectedAge?.value == "20대",
        ) {
            onAgeChange(Age.TWENTIES)
        }
        SelectableChip.Rounded(
            title = "30대",
            isSelected = selectedAge?.value == "30대",
        ) {
            onAgeChange(Age.THIRTIES)
        }
        SelectableChip.Rounded(
            title = "40대",
            isSelected = selectedAge?.value == "40대",
        ) {
            onAgeChange(Age.FORTIES)
        }
        SelectableChip.Rounded(
            title = "50대 이상",
            isSelected = selectedAge?.value == "50대 이상",
        ) {
            onAgeChange(Age.FIFTIES)
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewProfileCreateHumanAgeScreen() {
    LanPetAppTheme {
        ProfileCreateHumanAgeScreen(
            manProfileCreateViewModel = hiltViewModel(),
        ) {}
    }
}
