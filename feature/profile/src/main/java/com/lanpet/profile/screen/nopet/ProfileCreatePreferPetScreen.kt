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
import com.example.designsystem.theme.LanPetAppTheme
import com.example.designsystem.theme.LanPetDimensions
import com.example.designsystem.theme.widgets.CommonButton
import com.example.designsystem.theme.widgets.LanPetTopAppBar
import com.example.model.PetCategory
import com.example.profile.R
import com.lanpet.profile.viewmodel.ManProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.profile.widget.SelectableChip
import com.example.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileCreatePreferPetScreen(
    manProfileCreateViewModel: ManProfileCreateViewModel,
    onNavigateToHumanBio: () -> Unit,
) {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(stringResource(R.string.appbar_title_profile_create))
                },
                actions = {
                    Text("3/4 ")
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
                Heading(title = stringResource(R.string.heading_profile_create_prefer_pet_no_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_prefer_pet_no_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                PreferPetChipSection(manProfileCreateViewModel)
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                    onNavigateToHumanBio()
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun PreferPetChipSection(viewModel: ManProfileCreateViewModel) {
    val manProfileCreate = viewModel.manProfileCreate.collectAsState()

    FlowRow {
        SelectableChip(
            title = "고양이",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.CAT),
        ) {
            viewModel.updatePreferPet(PetCategory.CAT)
        }
        SelectableChip(
            title = "강아지",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.DOG),
        ) {
            viewModel.updatePreferPet(PetCategory.DOG)
        }
        SelectableChip(
            title = "햄스터",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.HAMSTER),
        ) {
            viewModel.updatePreferPet(PetCategory.HAMSTER)
        }
        SelectableChip(
            title = "도마뱀",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.LIZARD),
        ) {
            viewModel.updatePreferPet(PetCategory.LIZARD)
        }
        SelectableChip(
            title = "앵무새",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.PARROT),
        ) {
            viewModel.updatePreferPet(PetCategory.PARROT)
        }
        SelectableChip(
            title = "물고기",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.FISH),
        ) {
            viewModel.updatePreferPet(PetCategory.FISH)
        }
        SelectableChip(
            title = "뱀",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.SNAKE),
        ) {
            viewModel.updatePreferPet(PetCategory.SNAKE)
        }
        SelectableChip(
            title = "거미",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.SPIDER),
        ) {
            viewModel.updatePreferPet(PetCategory.SPIDER)
        }
        SelectableChip(
            title = "양서류",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.AMPHIBIAN),
        ) {
            viewModel.updatePreferPet(PetCategory.AMPHIBIAN)
        }
        SelectableChip(
            title = "기타",
            isSelected = manProfileCreate.value.preferPets.contains(PetCategory.ETC),
        ) {
            viewModel.updatePreferPet(PetCategory.ETC)
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewProfileCreatePreferPetScreen() {
    LanPetAppTheme {
        ProfileCreatePreferPetScreen(
            manProfileCreateViewModel = hiltViewModel(),
        ){}
    }
}
