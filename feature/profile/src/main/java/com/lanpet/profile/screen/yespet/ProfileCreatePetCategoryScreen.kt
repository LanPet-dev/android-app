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
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.domain.model.PetCategory
import com.lanpet.profile.R
import com.lanpet.profile.viewmodel.PetProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileCreatePetCategoryScreen(
    petProfileCreateViewModel: PetProfileCreateViewModel,
    modifier: Modifier = Modifier,
    onNavigateToPetSpecies: () -> Unit = {},
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
                petProfileCreateViewModel.petProfileCreate
                    .collectAsState()
                    .value.pet.petCategory
                    .let { it1 ->
                        CategoryChipSection(
                            selectedCategory =
                            it1,
                        ) {
                            petProfileCreateViewModel.setPetCategory(it)
                        }
                    }
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
private fun CategoryChipSection(
    selectedCategory: PetCategory,
    modifier: Modifier = Modifier,
    onItemSelect: (PetCategory) -> Unit = {},
) {
    FlowRow {
        SelectableChip.Rounded(
            title = "고양이",
            isSelected = selectedCategory.value == "고양이",
        ) {
            onItemSelect(PetCategory.CAT)
        }
        SelectableChip.Rounded(
            title = "강아지",
            isSelected = selectedCategory.value == "강아지",
        ) {
            onItemSelect(PetCategory.DOG)
        }
        SelectableChip.Rounded(
            title = "거미",
            isSelected = selectedCategory.value == "거미",
        ) {
            onItemSelect(PetCategory.SPIDER)
        }
        SelectableChip.Rounded(
            title = "뱀",
            isSelected = selectedCategory.value == "뱀",
        ) {
            onItemSelect(PetCategory.SNAKE)
        }
        SelectableChip.Rounded(
            title = "물고기",
            isSelected = selectedCategory.value == "물고기",
        ) {
            onItemSelect(PetCategory.FISH)
        }
        SelectableChip.Rounded(
            title = "앵무새",
            isSelected = selectedCategory.value == "앵무새",
        ) {
            onItemSelect(PetCategory.PARROT)
        }
        SelectableChip.Rounded(
            title = "햄스터",
            isSelected = selectedCategory.value == "햄스터",
        ) {
            onItemSelect(PetCategory.HAMSTER)
        }
        SelectableChip.Rounded(
            title = "도마뱀",
            isSelected = selectedCategory.value == "도마뱀",
        ) {
            onItemSelect(PetCategory.LIZARD)
        }
        SelectableChip.Rounded(
            title = "양서류",
            isSelected = selectedCategory.value == "양서류",
        ) {
            onItemSelect(PetCategory.AMPHIBIAN)
        }
        SelectableChip.Rounded(
            title = "파충류",
            isSelected = selectedCategory.value == "파충류",
        ) {
            onItemSelect(PetCategory.REPTILE)
        }
        SelectableChip.Rounded(
            title = "거북이",
            isSelected = selectedCategory.value == "거북이",
        ) {
            onItemSelect(PetCategory.TURTLE)
        }
        SelectableChip.Rounded(
            title = "기타",
            isSelected = selectedCategory.value == "기타",
        ) {
            onItemSelect(PetCategory.ETC)
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewProfileCreatePetCategoryScreen() {
    LanPetAppTheme {
        ProfileCreatePetCategoryScreen(
            petProfileCreateViewModel = hiltViewModel(),
        ) {}
    }
}
