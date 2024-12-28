package com.lanpet.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.FormValidator
import com.lanpet.domain.model.Age
import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Butler
import com.lanpet.domain.usecase.profile.CheckNicknameDuplicatedUseCase
import com.lanpet.domain.usecase.profile.RegisterManProfileUseCase
import com.lanpet.profile.model.RegisterManProfileResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManProfileCreateViewModel
    @Inject
    constructor(
        private val registerManProfileUseCase: RegisterManProfileUseCase,
        private val checkNicknameDuplicatedUseCase: CheckNicknameDuplicatedUseCase,
    ) : ViewModel() {
        private val _manProfileCreate =
            MutableStateFlow(
                ManProfileCreate(
                    profileImageUri = null,
                    nickName = "",
                    bio = "",
                    type = ProfileType.BUTLER,
                    butler =
                        Butler(
                            age = Age.NONE,
                            preferredPet = emptyList(),
                        ),
                    representative = true,
                ),
            )
        val manProfileCreate: StateFlow<ManProfileCreate> = _manProfileCreate.asStateFlow()

        private val _registerManProfileResult =
            MutableStateFlow<RegisterManProfileResult>(RegisterManProfileResult.Initial)
        val registerManProfileResult: StateFlow<RegisterManProfileResult> =
            _registerManProfileResult.asStateFlow()

        private val _isNicknameDuplicated = MutableStateFlow<Boolean?>(null)
        val isNicknameDuplicated: StateFlow<Boolean?> = _isNicknameDuplicated.asStateFlow()

        private val manProfileCreateValidator =
            ManProfileCreateValidator(
                profileImageUri =
                    FormValidator { uri ->
                        FormValidationStatus.Valid()
                    },
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
                bio =
                    FormValidator { bio ->
                        FormValidationStatus.Valid()
                    },
                age =
                    FormValidator { age ->
                        if (age == null) {
                            FormValidationStatus.Invalid("나이를 선택해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                preferredPet =
                    FormValidator { preferredPet ->
                        if (preferredPet.isNullOrEmpty()) {
                            FormValidationStatus.Invalid("선호하는 반려동물을 선택해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
            )
        private val _manProfileCreateValidationResult =
            MutableStateFlow(
                ManProfileCreateValidationResult(
                    profileImageUri = FormValidationStatus.Initial(),
                    nickName = FormValidationStatus.Initial(),
                    bio = FormValidationStatus.Initial(),
                    age = FormValidationStatus.Initial(),
                    preferredPet = FormValidationStatus.Initial(),
                ),
            )
        val manProfileCreateValidationResult: StateFlow<ManProfileCreateValidationResult> =
            _manProfileCreateValidationResult.asStateFlow()

        fun setProfileImageUri(uri: String) {
            _manProfileCreateValidationResult.value =
                _manProfileCreateValidationResult.value.copy(
                    profileImageUri = manProfileCreateValidator.profileImageUri.validate(Uri.parse(uri)),
                )
            _manProfileCreate.value = _manProfileCreate.value.copy(profileImageUri = Uri.parse(uri))
        }

        fun setNickName(nickName: String) {
            _manProfileCreateValidationResult.value =
                _manProfileCreateValidationResult.value.copy(
                    nickName = manProfileCreateValidator.nickName.validate(nickName),
                )
            _manProfileCreate.value = _manProfileCreate.value.copy(nickName = nickName)
        }

        fun setAge(age: Age) {
            _manProfileCreateValidationResult.value =
                _manProfileCreateValidationResult.value.copy(
                    age = manProfileCreateValidator.age.validate(age),
                )
            _manProfileCreate.value =
                _manProfileCreate.value.copy(butler = _manProfileCreate.value.butler.copy(age = age))
        }

        fun setBio(bio: String) {
            _manProfileCreateValidationResult.value =
                _manProfileCreateValidationResult.value.copy(
                    bio = manProfileCreateValidator.bio.validate(bio),
                )
            _manProfileCreate.value = _manProfileCreate.value.copy(bio = bio)
        }

        fun updatePreferPet(category: PetCategory) {
            val tmpList =
                _manProfileCreate.value.butler.preferredPet
                    .toMutableList()

            if (_manProfileCreate.value.butler.preferredPet
                    .any { it.value == category.value }
            ) {
                tmpList.removeAll {
                    it.value == category.value
                }
                _manProfileCreate.value =
                    _manProfileCreate.value.copy(
                        butler = _manProfileCreate.value.butler.copy(preferredPet = tmpList),
                    )
                return
            } else {
                tmpList.add(category)
                tmpList.distinct()
            }

            _manProfileCreateValidationResult.value =
                _manProfileCreateValidationResult.value.copy(
                    preferredPet = manProfileCreateValidator.preferredPet.validate(tmpList),
                )

            _manProfileCreate.value =
                _manProfileCreate.value.copy(butler = _manProfileCreate.value.butler.copy(preferredPet = tmpList))
        }

        fun registerManProfile() {
            viewModelScope.launch {
                _registerManProfileResult.value = RegisterManProfileResult.Loading
                try {
                    registerManProfileUseCase(_manProfileCreate.value).collect {
                        _registerManProfileResult.value = RegisterManProfileResult.Success
                    }
                } catch (e: Exception) {
                    _registerManProfileResult.value =
                        RegisterManProfileResult.Error(e.message ?: "Unknown error")
                }
            }
        }

        suspend fun checkNickNameDuplicate() {
            if (_manProfileCreateValidationResult.value.nickName !is FormValidationStatus.Valid) {
                return
            }

            try {
                val nickname = _manProfileCreate.value.nickName

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

        fun clearProfileCreate() {
            _manProfileCreate.value =
                ManProfileCreate(
                    profileImageUri = null,
                    nickName = "",
                    bio = "",
                    type = ProfileType.BUTLER,
                    butler =
                        Butler(
                            age = Age.NONE,
                            preferredPet = emptyList(),
                        ),
                    representative = true,
                )
        }
    }

data class ManProfileCreateValidator(
    val profileImageUri: FormValidator<Uri?>,
    val nickName: FormValidator<String?>,
    val bio: FormValidator<String?>,
    val age: FormValidator<Age?>,
    val preferredPet: FormValidator<List<PetCategory>?>,
)

data class ManProfileCreateValidationResult(
    val profileImageUri: FormValidationStatus,
    val nickName: FormValidationStatus,
    val bio: FormValidationStatus,
    val age: FormValidationStatus,
    val preferredPet: FormValidationStatus,
)
