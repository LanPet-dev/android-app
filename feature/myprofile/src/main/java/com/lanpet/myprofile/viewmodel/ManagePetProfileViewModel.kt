package com.lanpet.myprofile.viewmodel

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.FormValidator
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.PetProfile
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet
import com.lanpet.domain.usecase.profile.CheckNicknameDuplicatedUseCase
import com.lanpet.domain.usecase.profile.GetProfileDetailUseCase
import com.lanpet.domain.usecase.profile.ModifyPetProfileUseCase
import com.lanpet.myprofile.model.PetProfileUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManagePetProfileViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val modifyPetProfileUseCase: ModifyPetProfileUseCase,
        private val getProfileDetailUseCase: GetProfileDetailUseCase,
        private val checkNicknameDuplicatedUseCase: CheckNicknameDuplicatedUseCase,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                ManagePetProfileUiState(
                    petProfileUpdate = null,
                ),
            )
        val uiState = _uiState.asStateFlow()

        private val _uiEvent = MutableSharedFlow<Boolean>()
        val uiEvent = _uiEvent.asSharedFlow()

        private val petProfileUpdateValidator =
            PetProfileUpdateValidator(
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
            val nickname = _uiState.value.petProfileUpdate?.nickName ?: return

            viewModelScope.launch {
                checkNicknameDuplicatedUseCase(nickname).collect { isDuplicated ->
                    _uiState.value = _uiState.value.copy(nicknameDuplicateCheck = isDuplicated)
                }
            }
        }

        fun updatePetCategory(petCategory: PetCategory) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileUpdate =
                        _uiState.value.petProfileUpdate?.copy(
                            pet =
                                _uiState.value.petProfileUpdate
                                    ?.pet
                                    ?.copy(petCategory = petCategory),
                        ),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            petCategory = petProfileUpdateValidator.petCategory.validate(petCategory),
                        ),
                )
        }

        fun updateBreed(breed: String) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileUpdate =
                        _uiState.value.petProfileUpdate?.copy(
                            pet =
                                _uiState.value.petProfileUpdate
                                    ?.pet
                                    ?.copy(breed = breed),
                        ),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            breed = petProfileUpdateValidator.breed.validate(breed),
                        ),
                )
        }

        fun updateProfileImageUri(profileImageUri: Uri) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileUpdate =
                        _uiState.value.petProfileUpdate?.copy(profileImageUri = profileImageUri),
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
                    petProfileUpdate =
                        _uiState.value.petProfileUpdate?.copy(nickName = nickName),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            nickName = petProfileUpdateValidator.nickName.validate(nickName),
                        ),
                )
        }

        fun updateBio(bio: String) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileUpdate =
                        _uiState.value.petProfileUpdate?.copy(bio = bio),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            bio = petProfileUpdateValidator.bio.validate(bio),
                        ),
                )
        }

        private fun checkValidation(): Boolean = _uiState.value.validationStatus.isValid && _uiState.value.nicknameDuplicateCheck == true

        fun modifyPetProfile() {
            val petProfileUpdate = _uiState.value.petProfileUpdate ?: return

            _uiState.value =
                _uiState.value.copy(
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            nickName = petProfileUpdateValidator.nickName.validate(petProfileUpdate.nickName),
                            bio = petProfileUpdateValidator.bio.validate(petProfileUpdate.bio),
                            petCategory = petProfileUpdateValidator.petCategory.validate(petProfileUpdate.pet?.petCategory),
                            breed = petProfileUpdateValidator.breed.validate(petProfileUpdate.pet?.breed),
                            profileImageUri =
                                petProfileUpdateValidator.profileImageUri.validate(
                                    petProfileUpdate.profileImageUri,
                                ),
                        ),
                )

            if (!checkValidation()) return

            val petProfile =
                PetProfile(
                    type = petProfileUpdate.type,
                    profileImageUri = petProfileUpdate.profileImageUri,
                    nickName = petProfileUpdate.nickName,
                    bio = petProfileUpdate.bio,
                    pet = petProfileUpdate.pet,
                )

            if (_uiState.value.validationStatus.isValid) {
                viewModelScope.launch {
                    modifyPetProfileUseCase(
                        profileId = petProfileUpdate.id,
                        petProfile = petProfile,
                    ).collect {
                        _uiEvent.emit(true)
                    }
                }
            }
        }

        init {
            savedStateHandle.get<String>("profileId")?.let {
                Timber.i("profileId: $it")

                viewModelScope.launch {
                    getProfileDetailUseCase(it).collect { profileDetail ->
                        Timber.i("profileDetail: $profileDetail")

                        _uiState.value =
                            _uiState.value.copy(
                                petProfileUpdate =
                                    PetProfileUpdate(
                                        profileImageUri =
                                            profileDetail.pictureUrl?.let {
                                                Uri.parse(
                                                    profileDetail.pictureUrl,
                                                )
                                            },
                                        nickName = profileDetail.nickname,
                                        bio = profileDetail.introduction,
                                        id = profileDetail.id,
                                        type = ProfileType.PET,
                                        pet =
                                            Pet(
                                                petCategory =
                                                    profileDetail.pet?.petCategory
                                                        ?: PetCategory.OTHER,
                                                breed = profileDetail.pet?.breed,
                                                feature = profileDetail.pet?.feature ?: emptyList(),
                                                weight = profileDetail.pet?.weight,
                                                birthDate = profileDetail.pet?.birthDate,
                                            ),
                                    ),
                            )
                    }
                }
            }
        }
    }

@Stable
data class ManagePetProfileUiState(
    val petProfileUpdate: PetProfileUpdate?,
    val nicknameDuplicateCheck: Boolean? = null,
    val validationStatus: PetProfileUpdateValidationStatus =
        PetProfileUpdateValidationStatus(
            profileImageUri = FormValidationStatus.Initial(),
            nickName = FormValidationStatus.Initial(),
            bio = FormValidationStatus.Initial(),
            petCategory = FormValidationStatus.Initial(),
            breed = FormValidationStatus.Initial(),
        ),
)

@Stable
data class PetProfileUpdateValidator(
    val profileImageUri: FormValidator<Uri?>,
    val nickName: FormValidator<String?>,
    val bio: FormValidator<String?>,
    val petCategory: FormValidator<PetCategory?>,
    val breed: FormValidator<String?>,
)

@Stable
data class PetProfileUpdateValidationStatus(
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
