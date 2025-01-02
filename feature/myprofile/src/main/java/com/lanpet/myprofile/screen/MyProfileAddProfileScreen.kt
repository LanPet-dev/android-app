package com.lanpet.myprofile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lanpet.core.auth.BasePreviewWrapper
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.ArrowLeft
import com.lanpet.core.common.toast
import com.lanpet.core.common.widget.CommonAppBarTitle
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.CommonIconButtonBox
import com.lanpet.core.common.widget.CommonSubHeading1
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.ProfileImagePicker
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.common.widget.TextFieldWithDeleteButton
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.Age
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.myprofile.R
import com.lanpet.myprofile.navigation.MyProfileAddProfile
import com.lanpet.myprofile.viewmodel.AddManProfileViewModel
import com.lanpet.myprofile.viewmodel.AddPetProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileAddProfileScreen(
    modifier: Modifier = Modifier,
    args: MyProfileAddProfile? = null,
    onNavigateUp: () -> Unit = { },
) {
    assert(args?.profileType != null)

    val verticalScrollState = rememberScrollState()

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                navigationIcon = {
                    CommonIconButtonBox(
                        content = {
                            Image(
                                imageVector = MyIconPack.ArrowLeft,
                                contentDescription = "ic_back",
                                colorFilter = ColorFilter.tint(MaterialTheme.customColorScheme.defaultIconColor),
                            )
                        },
                        onClick = onNavigateUp,
                    )
                },
                title = {
                    CommonAppBarTitle(
                        title = stringResource(R.string.title_appbar_my_profile_add_profile),
                    )
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
            Column(
                modifier =
                    Modifier
                        .verticalScroll(
                            verticalScrollState,
                        ),
            ) {
                when (args!!.profileType) {
                    ProfileType.PET -> {
                        PetProfileAddView(
                            onNavigateUp = onNavigateUp,
                        )
                    }

                    ProfileType.BUTLER -> {
                        ManProfileAddView(
                            onNavigateUp = onNavigateUp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PetProfileAddView(
    modifier: Modifier = Modifier,
    @Suppress("ktlint:compose:lambda-param-in-effect")
    onNavigateUp: () -> Unit = { },
    addPetProfileViewModel: AddPetProfileViewModel = hiltViewModel(),
) {
    val petProfileUiState by addPetProfileViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val rememberOnNavigateUp by rememberUpdatedState {
        onNavigateUp()
        onNavigateUp()
    }

    LaunchedEffect(Unit) {
        addPetProfileViewModel.uiEvent.collect { event ->
            when (event) {
                true -> {
                    context.toast(context.getString(R.string.toast_profile_create_success))
                    rememberOnNavigateUp()
                }

                false -> {
                    context.toast(context.getString(R.string.toast_profile_create_fail))
                }
            }
        }
    }

    Column {
        ProfileImagePicker(
            profileImageUri = petProfileUiState.petProfileCreate?.profileImageUri,
            profileType = ProfileType.PET,
            onImageSelect = {
                addPetProfileViewModel.updateProfileImageUri(it)
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        NickNameSection(
            duplicatedStatus = petProfileUiState.nicknameDuplicateCheck,
            nickname = petProfileUiState.petProfileCreate?.nickName ?: "",
            onNicknameChange = {
                addPetProfileViewModel.updateNickName(it)
            },
            onCheckDuplicatedNickname = {
                addPetProfileViewModel.checkNicknameDuplicated()
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        PetCategorySection(
            petCategory = petProfileUiState.petProfileCreate?.pet?.petCategory,
            onPetCategoryChange = {
                addPetProfileViewModel.updatePetCategory(it)
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        PetBreedSection(
            petBreed = petProfileUiState.petProfileCreate?.pet?.breed ?: "",
            onPetBreedChange = {
                addPetProfileViewModel.updateBreed(it)
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        BioInputSection(
            text = petProfileUiState.petProfileCreate?.bio ?: "",
            onTextChange = {
                addPetProfileViewModel.updateBio(it)
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        CommonButton(
            title = stringResource(R.string.title_register_button),
            isActive = addPetProfileViewModel.isValidState.collectAsStateWithLifecycle().value,
        ) {
            addPetProfileViewModel.addPetProfile()
        }
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
    }
}

@Composable
private fun ManProfileAddView(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = { },
    addManProfileViewModel: AddManProfileViewModel = hiltViewModel(),
) {
    val manageProfileUiState by addManProfileViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val rememberOnNavigateUp by rememberUpdatedState {
        onNavigateUp()
        onNavigateUp()
    }

    LaunchedEffect(Unit) {
        addManProfileViewModel.uiEvent.collect { event ->
            when (event) {
                true -> {
                    context.toast(context.getString(R.string.toast_profile_create_success))
                    rememberOnNavigateUp()
                }

                false -> {
                    context.toast(context.getString(R.string.toast_profile_create_fail))
                }
            }
        }
    }

    Column {
        ProfileImagePicker(
            profileImageUri = manageProfileUiState.manProfileCreate?.profileImageUri,
            profileType = ProfileType.BUTLER,
            onImageSelect = {
                addManProfileViewModel.updateProfileImageUri(it)
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.large))
        NickNameSection(
            duplicatedStatus = manageProfileUiState.nicknameDuplicateCheck,
            nickname = manageProfileUiState.manProfileCreate?.nickName ?: "",
            onNicknameChange = {
                addManProfileViewModel.updateNickName(it)
            },
            onCheckDuplicatedNickname = {
                addManProfileViewModel.checkNicknameDuplicate()
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        SelectAgeSection(
            age = manageProfileUiState.manProfileCreate?.butler?.age,
            onAgeChange = {
                addManProfileViewModel.updateButlerAge(it)
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        SelectPreferPetSection(
            preferPet = manageProfileUiState.manProfileCreate?.butler?.preferredPet ?: emptyList(),
            onPreferPetChange = {
                addManProfileViewModel.updateButlerPreferredPet(it)
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        BioInputSection(
            text = manageProfileUiState.manProfileCreate?.bio ?: "",
            onTextChange = {
                addManProfileViewModel.updateBio(it)
            },
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
        CommonButton(
            title = stringResource(R.string.title_register_button),
            isActive = addManProfileViewModel.isValidState.collectAsStateWithLifecycle().value,
        ) {
            addManProfileViewModel.addManProfile()
        }
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
    }
}

@Composable
private fun BioInputSection(
    text: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
) {
    val maxLength = 200

    Column {
        CommonSubHeading1(
            title = stringResource(R.string.bio_hint_my_profile_add_profile),
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
        Box {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
                maxLines = 7,
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
                        onTextChange(newText)
                    }
                },
                placeholder = {
                    Text(
                        stringResource(R.string.bio_input_placeholder_my_profile_add_profile),
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
                    text = "${text.length}/$maxLength",
                    style = MaterialTheme.typography.bodySmall.copy(color = GrayColor.LIGHT),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectPreferPetSection(
    preferPet: List<PetCategory>,
    modifier: Modifier = Modifier,
    onPreferPetChange: (PetCategory) -> Unit = {},
) {
    Column {
        CommonSubHeading1(
            title = stringResource(R.string.prefer_pet_hint_my_profile_add_profile),
        )

        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
        FlowRow {
            SelectableChip(
                title = "고양이",
                isSelected = preferPet.contains(PetCategory.CAT),
            ) {
                onPreferPetChange(PetCategory.CAT)
            }
            SelectableChip(
                title = "강아지",
                isSelected = preferPet.contains(PetCategory.DOG),
            ) {
                onPreferPetChange(PetCategory.DOG)
            }
            SelectableChip(
                title = "햄스터",
                isSelected = preferPet.contains(PetCategory.HAMSTER),
            ) {
                onPreferPetChange(PetCategory.HAMSTER)
            }
            SelectableChip(
                title = "물고기",
                isSelected = preferPet.contains(PetCategory.FISH),
            ) {
                onPreferPetChange(PetCategory.FISH)
            }
            SelectableChip(
                title = "앵무새",
                isSelected = preferPet.contains(PetCategory.PARROT),
            ) {
                onPreferPetChange(PetCategory.PARROT)
            }
            // 뱀
            SelectableChip(
                title = "뱀",
                isSelected = preferPet.contains(PetCategory.SNAKE),
            ) {
                onPreferPetChange(PetCategory.SNAKE)
            }
            SelectableChip(
                title = "도마뱀",
                isSelected = preferPet.contains(PetCategory.LIZARD),
            ) {
                onPreferPetChange(PetCategory.LIZARD)
            }
            SelectableChip(
                title = "거북이",
                isSelected = preferPet.contains(PetCategory.TURTLE),
            ) {
                onPreferPetChange(PetCategory.TURTLE)
            }
            SelectableChip(
                title = "기타",
                isSelected = preferPet.contains(PetCategory.OTHER),
            ) {
                onPreferPetChange(PetCategory.OTHER)
            }
        }
    }
}

@Composable
private fun PetBreedSection(
    petBreed: String,
    modifier: Modifier = Modifier,
    onPetBreedChange: (String) -> Unit = {},
) {
    Column {
        CommonSubHeading1(
            title = stringResource(R.string.heading_pet_breed_manage_profile),
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
        TextFieldWithDeleteButton(
            value = petBreed,
            onValueChange = {
                onPetBreedChange(it)
            },
            placeholder = "ex)푸들",
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PetCategorySection(
    petCategory: PetCategory?,
    modifier: Modifier = Modifier,
    onPetCategoryChange: (PetCategory) -> Unit = {},
) {
    Column {
        CommonSubHeading1(
            title = stringResource(R.string.heading_pet_category_manage_profile),
        )

        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
        FlowRow {
            SelectableChip(
                title = "고양이",
                isSelected = petCategory == PetCategory.CAT,
            ) {
                onPetCategoryChange(PetCategory.CAT)
            }
            SelectableChip(
                title = "강아지",
                isSelected = petCategory == PetCategory.DOG,
            ) {
                onPetCategoryChange(PetCategory.DOG)
            }
            SelectableChip(
                title = "햄스터",
                isSelected = petCategory == PetCategory.HAMSTER,
            ) {
                onPetCategoryChange(PetCategory.HAMSTER)
            }
            SelectableChip(
                title = "물고기",
                isSelected = petCategory == PetCategory.FISH,
            ) {
                onPetCategoryChange(PetCategory.FISH)
            }
            SelectableChip(
                title = "앵무새",
                isSelected = petCategory == PetCategory.PARROT,
            ) {
                onPetCategoryChange(PetCategory.PARROT)
            }
            // 뱀
            SelectableChip(
                title = "뱀",
                isSelected = petCategory == PetCategory.SNAKE,
            ) {
                onPetCategoryChange(PetCategory.SNAKE)
            }
            SelectableChip(
                title = "도마뱀",
                isSelected = petCategory == PetCategory.LIZARD,
            ) {
                onPetCategoryChange(PetCategory.LIZARD)
            }
            SelectableChip(
                title = "거북이",
                isSelected = petCategory == PetCategory.TURTLE,
            ) {
                onPetCategoryChange(PetCategory.TURTLE)
            }
            SelectableChip(
                title = "기타",
                isSelected = petCategory == PetCategory.OTHER,
            ) {
                onPetCategoryChange(PetCategory.OTHER)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectAgeSection(
    age: Age?,
    modifier: Modifier = Modifier,
    onAgeChange: (Age) -> Unit = {},
) {
    Column {
        CommonSubHeading1(
            title = stringResource(R.string.age_hint_my_profile_add_profile),
        )

        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
        FlowRow {
            SelectableChip(
                title = Age.TENS.value,
                isSelected = age == Age.TENS,
            ) {
                onAgeChange(Age.TENS)
            }
            SelectableChip(
                title = Age.TWENTIES.value,
                isSelected = age == Age.TWENTIES,
            ) {
                onAgeChange(Age.TWENTIES)
            }
            SelectableChip(
                title = Age.THIRTIES.value,
                isSelected = age == Age.THIRTIES,
            ) {
                onAgeChange(Age.THIRTIES)
            }
            SelectableChip(
                title = Age.FORTIES.value,
                isSelected = age == Age.FORTIES,
            ) { onAgeChange(Age.FORTIES) }
            SelectableChip(
                title = Age.FIFTIES.value,
                isSelected = age == Age.FIFTIES,
            ) {
                onAgeChange(Age.FIFTIES)
            }
        }
    }
}

@Composable
private fun NickNameSection(
    nickname: String,
    duplicatedStatus: Boolean?,
    modifier: Modifier = Modifier,
    onNicknameChange: (String) -> Unit = {},
    onCheckDuplicatedNickname: () -> Unit = {},
) {
    Column {
        CommonSubHeading1(
            title = stringResource(R.string.nickname_hint_my_profile_add_profile),
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
        Row {
            TextFieldWithDeleteButton(
                modifier = Modifier.weight(3f),
                value = nickname,
                onValueChange = {
                    onNicknameChange(it)
                },
                placeholder = stringResource(R.string.nickname_placeholder_my_profile_add_profile),
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            CommonButton(
                modifier = Modifier.width(100.dp),
                title = stringResource(R.string.check_duplicated_nickname_button_string),
                onClick = {
                    onCheckDuplicatedNickname()
                },
            )
        }
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
        when (duplicatedStatus) {
            true -> {
                DuplicatedNicknameOkText()
            }

            false -> {
                DuplicatedNicknameErrorText()
            }

            null -> {
            }
        }
    }
}

@Composable
private fun DuplicatedNicknameOkText(modifier: Modifier = Modifier) {
    Text(
        stringResource(R.string.check_duplicated_nickname_ok_string),
        style = MaterialTheme.customTypography().body3MediumSingle.copy(color = MaterialTheme.customColorScheme.confirmText),
    )
}

@Composable
private fun DuplicatedNicknameErrorText(modifier: Modifier = Modifier) {
    Text(
        stringResource(R.string.check_duplicated_nickname_error_string),
        style = MaterialTheme.customTypography().body3MediumSingle.copy(color = MaterialTheme.customColorScheme.errorText),
    )
}

@Composable
@PreviewLightDark
private fun MyProfileAddPetProfilePreview() {
    BasePreviewWrapper {
        MyProfileAddProfileScreen(
            args =
                MyProfileAddProfile(
                    profileType = ProfileType.PET,
                ),
        )
    }
}

@Composable
@PreviewLightDark
private fun MyProfileAddManProfilePreview() {
    BasePreviewWrapper {
        MyProfileAddProfileScreen(
            args =
                MyProfileAddProfile(
                    profileType = ProfileType.BUTLER,
                ),
        )
    }
}
