package com.lanpet.profile.screen.yespet

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
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.widgets.CommonButton
import com.lanpet.core.designsystem.theme.widgets.LanPetTopAppBar
import com.example.model.PetCategory
import com.lanpet.profile.R
import com.lanpet.profile.viewmodel.PetProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.profile.widget.SelectableChip
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileCreatePetCategoryScreen(
    petProfileCreateViewModel: PetProfileCreateViewModel,
    onNavigateToPetSpecies: () -> Unit,
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
                Heading(title = stringResource(R.string.heading_profile_create_category_yes_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_category_yes_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                CategoryChipSection(petProfileCreateViewModel)
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(DS_R.string.next_button_string)) {
                    onNavigateToPetSpecies()
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun CategoryChipSection(viewModel: PetProfileCreateViewModel) {
    val petCategory = viewModel.petProfileCreate.collectAsState()

    FlowRow {
        SelectableChip(
            title = "고양이",
            isSelected = petCategory.value.petCategory?.value == "고양이",
        ) {
            viewModel.setPetCategory(PetCategory.CAT)
        }
        SelectableChip(
            title = "강아지",
            isSelected = petCategory.value.petCategory?.value == "강아지",
        ) {
            viewModel.setPetCategory(PetCategory.DOG)
        }
        SelectableChip(
            title = "거미",
            isSelected = petCategory.value.petCategory?.value == "거미",
        ) {
            viewModel.setPetCategory(PetCategory.SPIDER)
        }
        SelectableChip(
            title = "뱀",
            isSelected = petCategory.value.petCategory?.value == "뱀",
        ) {
            viewModel.setPetCategory(PetCategory.SNAKE)
        }
        SelectableChip(
            title = "물고기",
            isSelected = petCategory.value.petCategory?.value == "물고기",
        ) {
            viewModel.setPetCategory(PetCategory.FISH)
        }
        SelectableChip(
            title = "앵무새",
            isSelected = petCategory.value.petCategory?.value == "앵무새",
        ) {
            viewModel.setPetCategory(PetCategory.PARROT)
        }
        SelectableChip(
            title = "햄스터",
            isSelected = petCategory.value.petCategory?.value == "햄스터",
        ) {
            viewModel.setPetCategory(PetCategory.HAMSTER)
        }
        SelectableChip(
            title = "도마뱀",
            isSelected = petCategory.value.petCategory?.value == "도마뱀",
        ) {
            viewModel.setPetCategory(PetCategory.LIZARD)
        }
        SelectableChip(
            title = "양서류",
            isSelected = petCategory.value.petCategory?.value == "양서류",
        ) {
            viewModel.setPetCategory(PetCategory.AMPHIBIAN)
        }
        SelectableChip(
            title = "파충류",
            isSelected = petCategory.value.petCategory?.value == "파충류",
        ) {
            viewModel.setPetCategory(PetCategory.REPTILE)
        }
        SelectableChip(
            title = "거북이",
            isSelected = petCategory.value.petCategory?.value == "거북이",
        ) {
            viewModel.setPetCategory(PetCategory.TURTLE)
        }
        SelectableChip(
            title = "기타",
            isSelected = petCategory.value.petCategory?.value == "기타",
        ) {
            viewModel.setPetCategory(PetCategory.ETC)
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewProfileCreatePetCategoryScreen() {
    LanPetAppTheme {
        ProfileCreatePetCategoryScreen(
            petProfileCreateViewModel = hiltViewModel(),
        ) {}
    }
}
