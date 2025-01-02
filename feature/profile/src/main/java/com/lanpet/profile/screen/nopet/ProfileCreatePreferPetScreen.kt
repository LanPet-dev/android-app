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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.domain.model.PetCategory
import com.lanpet.profile.R
import com.lanpet.profile.viewmodel.ManProfileCreateViewModel
import com.lanpet.profile.widget.Heading
import com.lanpet.profile.widget.HeadingHint
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileCreatePreferPetScreen(
    manProfileCreateViewModel: ManProfileCreateViewModel,
    modifier: Modifier = Modifier,
    onNavigateToHumanBio: () -> Unit = {},
) {
    val manProfileCreate =
        manProfileCreateViewModel.manProfileCreate
            .collectAsState()

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
                PreferPetChipSection(
                    preferPets =
                        manProfileCreate
                            .value.butler.preferredPet,
                ) { pet ->
                    manProfileCreateViewModel.updatePreferPet(pet)
                }
                Spacer(Modifier.weight(1f))
                CommonButton(
                    title = stringResource(DS_R.string.next_button_string),
                    isActive =
                        manProfileCreateViewModel.manProfileCreateValidationResult
                            .collectAsStateWithLifecycle()
                            .value.preferredPet is FormValidationStatus.Valid,
                ) {
                    onNavigateToHumanBio()
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun PreferPetChipSection(
    preferPets: List<PetCategory>,
    modifier: Modifier = Modifier,
    onItemSelect: (PetCategory) -> Unit = {},
) {
    FlowRow {
        SelectableChip.Rounded(
            title = "고양이",
            isSelected = preferPets.contains(PetCategory.CAT),
        ) {
            onItemSelect(PetCategory.CAT)
        }
        SelectableChip.Rounded(
            title = "강아지",
            isSelected = preferPets.contains(PetCategory.DOG),
        ) {
            onItemSelect(PetCategory.DOG)
        }
        SelectableChip.Rounded(
            title = "햄스터",
            isSelected = preferPets.contains(PetCategory.HAMSTER),
        ) {
            onItemSelect(PetCategory.HAMSTER)
        }
        SelectableChip.Rounded(
            title = "도마뱀",
            isSelected = preferPets.contains(PetCategory.LIZARD),
        ) {
            onItemSelect(PetCategory.LIZARD)
        }
        SelectableChip.Rounded(
            title = "앵무새",
            isSelected = preferPets.contains(PetCategory.PARROT),
        ) {
            onItemSelect(PetCategory.PARROT)
        }
        SelectableChip.Rounded(
            title = "물고기",
            isSelected = preferPets.contains(PetCategory.FISH),
        ) {
            onItemSelect(PetCategory.FISH)
        }
        SelectableChip.Rounded(
            title = "뱀",
            isSelected = preferPets.contains(PetCategory.SNAKE),
        ) {
            onItemSelect(PetCategory.SNAKE)
        }
        SelectableChip.Rounded(
            title = "기타",
            isSelected = preferPets.contains(PetCategory.OTHER),
        ) {
            onItemSelect(PetCategory.OTHER)
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewProfileCreatePreferPetScreen() {
    LanPetAppTheme {
        ProfileCreatePreferPetScreen(
            manProfileCreateViewModel = hiltViewModel(),
        ) {}
    }
}
