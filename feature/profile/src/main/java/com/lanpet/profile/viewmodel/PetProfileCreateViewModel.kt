package com.lanpet.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.FormValidator
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet
import com.lanpet.domain.usecase.profile.CheckNicknameDuplicatedUseCase
import com.lanpet.domain.usecase.profile.RegisterPetProfileUseCase
import com.lanpet.profile.model.RegisterPetProfileResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PetProfileCreateViewModel
    @Inject
    constructor(
        private val registerPetProfileUseCase: RegisterPetProfileUseCase,
        private val checkNicknameDuplicatedUseCase: CheckNicknameDuplicatedUseCase,
    ) : ViewModel() {
        /**
         * PetProfile 생성 모델
         */
        private val _petProfileCreate =
            MutableStateFlow(
                PetProfileCreate(
                    profileImageUri = null,
                    nickName = "",
                    bio = "",
                    type = ProfileType.PET,
                    pet =
                        Pet(
                            petCategory = PetCategory.NONE,
                            breed = null,
                            feature = emptyList(),
                            weight = null,
                            birthDate = null,
                        ),
                ),
            )
        val petProfileCreate: StateFlow<PetProfileCreate> = _petProfileCreate.asStateFlow()

        /**
         * 최종 PetProfile 등록 결과 상태
         */
        private val _registerPetProfileResult =
            MutableStateFlow<RegisterPetProfileResult>(RegisterPetProfileResult.Initial)
        val registerPetProfileResult: StateFlow<RegisterPetProfileResult> =
            _registerPetProfileResult.asStateFlow()

        /**
         * PetProfileCreate 검증 상태
         */
        private val _petProfileCreateValidationResult =
            MutableStateFlow(
                PetProfileCreateValidationResult(
                    profileImageUri = FormValidationStatus.Initial(),
                    nickName = FormValidationStatus.Initial(),
                    bio = FormValidationStatus.Initial(),
                    petCategory = FormValidationStatus.Initial(),
                    breed = FormValidationStatus.Initial(),
                    feature = FormValidationStatus.Initial(),
                ),
            )

        val petProfileCreateValidationResult: StateFlow<PetProfileCreateValidationResult> =
            _petProfileCreateValidationResult.asStateFlow()

        /**
         * 닉네임 중복 체크 상태
         */
        private val _isNicknameDuplicated: MutableStateFlow<Boolean?> = MutableStateFlow(null)
        val isNicknameDuplicated: StateFlow<Boolean?> = _isNicknameDuplicated.asStateFlow()

        private val petProfileCreateValidator =
            PetProfileCreateValidator(
                profileImageUri =
                    FormValidator<Uri?>(
                        validate = { uri ->
                            if (uri == null) {
                                FormValidationStatus.Invalid("Please select a profile image")
                            } else {
                                FormValidationStatus.Valid()
                            }
                        },
                    ),
                nickName =
                    FormValidator { nickName ->
                        if (nickName.isNullOrBlank()) {
                            FormValidationStatus.Invalid("닉네임을 입력해주세요.")
                        } else if (nickName.length < 2 || nickName.length > 20) {
                            FormValidationStatus.Invalid(" 닉네임은 2자 이상 20자 이하로 입력해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                petCategory =
                    FormValidator<PetCategory?>(
                        validate = { petCategory ->
                            if (petCategory == null || petCategory == PetCategory.NONE) {
                                FormValidationStatus.Invalid("Please select a pet category")
                            } else {
                                FormValidationStatus.Valid()
                            }
                        },
                    ),
                breed =
                    FormValidator<String?>(
                        validate = { breed ->
                            FormValidationStatus.Valid()
                        },
                    ),
                bio =
                    FormValidator<String?>(
                        validate = { bio ->
                            if (bio.isNullOrEmpty()) {
                                FormValidationStatus.Invalid("Please enter a bio")
                            } else {
                                if (bio.length > 100) {
                                    FormValidationStatus.Invalid("Bio should be less than 100 characters")
                                } else {
                                    FormValidationStatus.Valid()
                                }
                            }
                        },
                    ),
                feature =
                    FormValidator<List<String>?>(
                        validate = { feature ->
                            if (feature.isNullOrEmpty()) {
                                FormValidationStatus.Invalid("Please select at least one feature")
                            } else {
                                FormValidationStatus.Valid()
                            }
                        },
                    ),
            )

        fun setProfileImageUri(uri: String) {
            _petProfileCreate.update { it.copy(profileImageUri = Uri.parse(uri)) }
        }

        fun setNickName(nickName: String) {
            _petProfileCreateValidationResult.value =
                _petProfileCreateValidationResult.value.copy(
                    nickName = petProfileCreateValidator.nickName.validate.invoke(nickName),
                )
            _petProfileCreate.update { it.copy(nickName = nickName) }
        }

        fun setPetCategory(petCategory: PetCategory) {
            _petProfileCreateValidationResult.value =
                _petProfileCreateValidationResult.value.copy(
                    petCategory = petProfileCreateValidator.petCategory.validate.invoke(petCategory),
                )
            _petProfileCreate.update {
                it.copy(
                    pet = it.pet.copy(petCategory = petCategory),
                )
            }
        }

        fun setBreed(breed: String) {
            _petProfileCreateValidationResult.value =
                _petProfileCreateValidationResult.value.copy(
                    breed = petProfileCreateValidator.breed.validate.invoke(breed),
                )
            _petProfileCreate.update {
                it.copy(
                    pet = it.pet.copy(breed = breed),
                )
            }
        }

        fun setBio(bio: String) {
            _petProfileCreateValidationResult.value =
                _petProfileCreateValidationResult.value.copy(
                    bio = petProfileCreateValidator.bio.validate.invoke(bio),
                )
            _petProfileCreate.update { it.copy(bio = bio) }
        }

        fun registerPetProfile() {
            viewModelScope.launch {
                _registerPetProfileResult.value = RegisterPetProfileResult.Loading
                try {
                    registerPetProfileUseCase(_petProfileCreate.value).collect {
                        _registerPetProfileResult.value = RegisterPetProfileResult.Success
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _registerPetProfileResult.value =
                        RegisterPetProfileResult.Error(e.message ?: "Unknown error")
                }
            }
        }

        suspend fun checkNickNameDuplicate() {
            if (_petProfileCreateValidationResult.value.nickName !is FormValidationStatus.Valid) {
                return
            }

            try {
                val nickname = _petProfileCreate.value.nickName

                checkNicknameDuplicatedUseCase(nickname).collect {
                    _isNicknameDuplicated.value = it
                }
            } catch (e: Exception) {
                Timber.e(e.stackTraceToString())
            }
        }

        fun clearNicknameDuplicated() {
            _isNicknameDuplicated.value = null
        }
    }

/**
 * PetProfile 생성 검증 모델
 * 각 항목에 대한 검증 로직을 담고 있음
 */
data class PetProfileCreateValidator(
    val profileImageUri: FormValidator<Uri?>,
    val nickName: FormValidator<String?>,
    val bio: FormValidator<String?>,
    val petCategory: FormValidator<PetCategory?>,
    val breed: FormValidator<String?>,
    val feature: FormValidator<List<String>?>,
)

/**
 * PetProfile 생성 검증 결과
 * 각 항목에 대한 검증 결과를 담고 있음
 */
data class PetProfileCreateValidationResult(
    val profileImageUri: FormValidationStatus,
    val nickName: FormValidationStatus,
    val bio: FormValidationStatus,
    val petCategory: FormValidationStatus,
    val breed: FormValidationStatus,
    val feature: FormValidationStatus,
)
