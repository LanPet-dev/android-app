package com.lanpet.myprofile.viewmodel

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.FormValidator
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet
import com.lanpet.domain.usecase.profile.CheckNicknameDuplicatedUseCase
import com.lanpet.domain.usecase.profile.RegisterPetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPetProfileViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val registerPetProfileUseCase: RegisterPetProfileUseCase,
        private val checkNicknameDuplicatedUseCase: CheckNicknameDuplicatedUseCase,
        private val authManager: AuthManager,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                AddPetProfileUiState(
                    petProfileCreate =
                        PetProfileCreate(
                            type = ProfileType.PET,
                            profileImageUri = null,
                            nickName = "",
                            bio = null,
                            pet =
                                Pet(
                                    petCategory = PetCategory.OTHER,
                                    breed = null,
                                    weight = null,
                                    birthDate = null,
                                ),
                        ),
                ),
            )
        val uiState = _uiState.asStateFlow()

        val isValidState =
            _uiState
                .map { state ->
                    val nicknameValidation =
                        petProfileUpdateValidator.nickName.validate(state.petProfileCreate?.nickName)
                    val bioValidation =
                        petProfileUpdateValidator.bio.validate(state.petProfileCreate?.bio)
                    val petCategoryValidation =
                        petProfileUpdateValidator.petCategory.validate(state.petProfileCreate?.pet?.petCategory)
                    val breedValidation =
                        petProfileUpdateValidator.breed.validate(state.petProfileCreate?.pet?.breed)
                    val profileImageUriValidation =
                        petProfileUpdateValidator.profileImageUri.validate(state.petProfileCreate?.profileImageUri)

                    nicknameValidation is FormValidationStatus.Valid &&
                        bioValidation is FormValidationStatus.Valid &&
                        petCategoryValidation is FormValidationStatus.Valid &&
                        breedValidation is FormValidationStatus.Valid &&
                        profileImageUriValidation is FormValidationStatus.Valid &&
                        state.nicknameDuplicateCheck == true
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = false,
                )

        private val _uiEvent = MutableSharedFlow<Boolean>()
        val uiEvent = _uiEvent.asSharedFlow()

        private val petProfileUpdateValidator =
            AddPetProfileValidator(
                profileImageUri =
                    FormValidator {
                        FormValidationStatus.Valid()
                    },
                nickName =
                    FormValidator {
                        if (it.isNullOrBlank()) {
                            FormValidationStatus.Invalid("Nick name is required")
                        } else if (it.length > 20 || it.length < 2) {
                            FormValidationStatus.Invalid("Nick name must be between 2 and 20 characters")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                bio =
                    FormValidator {
                        FormValidationStatus.Valid()
                    },
                petCategory =
                    FormValidator {
                        if (it == null) {
                            FormValidationStatus.Invalid("Pet category is required")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                breed =
                    FormValidator {
                        if (it.isNullOrBlank()) {
                            FormValidationStatus.Invalid("Breed is required")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
            )

        fun checkNicknameDuplicated() {
            val nickname = _uiState.value.petProfileCreate?.nickName ?: return

            viewModelScope.launch {
                checkNicknameDuplicatedUseCase(nickname).collect { isDuplicated ->
                    _uiState.value = _uiState.value.copy(nicknameDuplicateCheck = isDuplicated)
                }
            }
        }

        fun updatePetCategory(petCategory: PetCategory) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileCreate =
                        _uiState.value.petProfileCreate
                            ?.pet
                            ?.copy(petCategory = petCategory)
                            ?.let {
                                _uiState.value.petProfileCreate?.copy(
                                    pet =
                                    it,
                                )
                            },
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            petCategory = petProfileUpdateValidator.petCategory.validate(petCategory),
                        ),
                )
        }

        fun updateBreed(breed: String) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileCreate =
                        _uiState.value.petProfileCreate
                            ?.pet
                            ?.copy(breed = breed)
                            ?.let {
                                _uiState.value.petProfileCreate?.copy(
                                    pet =
                                    it,
                                )
                            },
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            breed = petProfileUpdateValidator.breed.validate(breed),
                        ),
                )
        }

        fun updateProfileImageUri(profileImageUri: Uri) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileCreate =
                        _uiState.value.petProfileCreate?.copy(profileImageUri = profileImageUri),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            profileImageUri =
                                petProfileUpdateValidator.profileImageUri.validate(
                                    profileImageUri,
                                ),
                        ),
                )
        }

        fun updateNickName(nickName: String) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileCreate =
                        _uiState.value.petProfileCreate?.copy(nickName = nickName),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            nickName = petProfileUpdateValidator.nickName.validate(nickName),
                        ),
                )
        }

        fun updateBio(bio: String) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileCreate =
                        _uiState.value.petProfileCreate?.copy(bio = bio),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            bio = petProfileUpdateValidator.bio.validate(bio),
                        ),
                )
        }

        fun checkValidation(): Boolean = _uiState.value.validationStatus.isValid && _uiState.value.nicknameDuplicateCheck == true

        fun addPetProfile() {
            val petProfileCreate = _uiState.value.petProfileCreate ?: return

            if (!isValidState.value) {
                return
            }

            viewModelScope.launch {
                runCatching {
                    registerPetProfileUseCase(petProfileCreate).collect {
                        _uiEvent.emit(true)
                        authManager.getProfiles()
                    }
                }.onFailure {
                    _uiEvent.emit(false)
                }
            }
        }
    }

@Stable
data class AddPetProfileUiState(
    val petProfileCreate: PetProfileCreate?,
    val nicknameDuplicateCheck: Boolean? = null,
    val validationStatus: AddPetProfileValidationStatus =
        AddPetProfileValidationStatus(
            profileImageUri = FormValidationStatus.Initial(),
            nickName = FormValidationStatus.Initial(),
            bio = FormValidationStatus.Initial(),
            petCategory = FormValidationStatus.Initial(),
            breed = FormValidationStatus.Initial(),
        ),
)

@Stable
data class AddPetProfileValidator(
    val profileImageUri: FormValidator<Uri?>,
    val nickName: FormValidator<String?>,
    val bio: FormValidator<String?>,
    val petCategory: FormValidator<PetCategory?>,
    val breed: FormValidator<String?>,
)

@Stable
data class AddPetProfileValidationStatus(
    val profileImageUri: FormValidationStatus,
    val nickName: FormValidationStatus,
    val bio: FormValidationStatus,
    val petCategory: FormValidationStatus,
    val breed: FormValidationStatus,
) {
    val isValid: Boolean
        get() =
            profileImageUri is FormValidationStatus.Valid &&
                nickName is FormValidationStatus.Valid &&
                bio is FormValidationStatus.Valid &&
                petCategory is FormValidationStatus.Valid &&
                breed is FormValidationStatus.Valid
}
