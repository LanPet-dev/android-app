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
    onNavigateToPreferPet: () -> Unit,
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
                AgeChipSection(manProfileCreateViewModel)
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                    onNavigateToPreferPet()
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun AgeChipSection(viewModel: ManProfileCreateViewModel) {
    val manProfileCreate = viewModel.manProfileCreate.collectAsState()

    FlowRow {
        SelectableChip(
            title = "10대",
            isSelected = manProfileCreate.value.age?.value == "10대",
        ) {
            viewModel.setAge(Age.TENS)
        }
        SelectableChip(
            title = "20대",
            isSelected = manProfileCreate.value.age?.value == "20대",
        ) {
            viewModel.setAge(Age.TWENTIES)
        }
        SelectableChip(
            title = "30대",
            isSelected = manProfileCreate.value.age?.value == "30대",
        ) {
            viewModel.setAge(Age.THIRTIES)
        }
        SelectableChip(
            title = "40대",
            isSelected = manProfileCreate.value.age?.value == "40대",
        ) {
            viewModel.setAge(Age.FORTIES)
        }
        SelectableChip(
            title = "50대 이상",
            isSelected = manProfileCreate.value.age?.value == "50대 이상",
        ) {
            viewModel.setAge(Age.FIFTIES)
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewProfileCreateHumanAgeScreen() {
    LanPetAppTheme {
        ProfileCreateHumanAgeScreen(
            manProfileCreateViewModel = hiltViewModel(),
        ) {}
    }
}
