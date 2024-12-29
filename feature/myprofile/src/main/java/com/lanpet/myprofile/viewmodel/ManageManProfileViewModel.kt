package com.lanpet.myprofile.viewmodel

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.FormValidator
import com.lanpet.domain.model.Age
import com.lanpet.domain.model.ManProfile
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Butler
import com.lanpet.domain.usecase.profile.CheckNicknameDuplicatedUseCase
import com.lanpet.domain.usecase.profile.GetProfileDetailUseCase
import com.lanpet.domain.usecase.profile.ModifyManProfileUseCase
import com.lanpet.myprofile.model.ManProfileUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManageManProfileViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val modifyManProfileUseCase: ModifyManProfileUseCase,
        private val getProfileDetailUseCase: GetProfileDetailUseCase,
        private val checkNicknameDuplicatedUseCase: CheckNicknameDuplicatedUseCase,
        private val authManager: AuthManager,
    ) : ViewModel() {
        private val _manageManProfileUiState =
            MutableStateFlow(
                ManageManProfileUiState(
                    manProfileUpdate = null,
                ),
            )
        val manageManProfileUiState = _manageManProfileUiState.asStateFlow()

        private val manProfileUpdateValidator =
            ManProfileUpdateValidator(
                profileImageUri =
                    FormValidator { uri ->
                        FormValidationStatus.Valid()
                    },
                nickName =
                    FormValidator { nickName ->
                        if (nickName.isEmpty()) {
                            FormValidationStatus.Invalid("닉네임을 입력해주세요.")
                        } else if (nickName.length < 2 || nickName.length > 20) {
                            FormValidationStatus.Invalid("닉네임은 2자 이상 20자 이하로 입력해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                bio =
                    FormValidator { bio ->
                        FormValidationStatus.Valid()
                    },
                butler =
                    FormValidator { butler ->
                        if (butler?.preferredPet?.isEmpty() == true) {
                            FormValidationStatus.Invalid("선호하는 반려동물을 선택해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
            )

        private val _uiEvent = MutableSharedFlow<Boolean>()
        val uiEvent = _uiEvent.asSharedFlow()

        fun updateProfileImageUri(uri: Uri) {
            _manageManProfileUiState.value =
                _manageManProfileUiState.value.copy(
                    manProfileUpdate =
                        _manageManProfileUiState.value.manProfileUpdate?.copy(
                            profileImageUri = uri,
                        ),
                    validationStatus =
                        _manageManProfileUiState.value.validationStatus.copy(
                            profileImageUri = manProfileUpdateValidator.profileImageUri.validate(uri),
                        ),
                )
        }

        fun updateBio(bio: String) {
            _manageManProfileUiState.value =
                _manageManProfileUiState.value.copy(
                    manProfileUpdate =
                        _manageManProfileUiState.value.manProfileUpdate?.copy(
                            bio = bio,
                        ),
                    validationStatus =
                        _manageManProfileUiState.value.validationStatus.copy(
                            bio = manProfileUpdateValidator.bio.validate(bio),
                        ),
                )
        }

        fun updateButlerAge(age: Age) {
            _manageManProfileUiState.value =
                _manageManProfileUiState.value.copy(
                    manProfileUpdate =
                        _manageManProfileUiState.value.manProfileUpdate?.copy(
                            butler =
                                _manageManProfileUiState.value.manProfileUpdate?.butler?.copy(
                                    age = age,
                                ),
                        ),
                    validationStatus =
                        _manageManProfileUiState.value.validationStatus.copy(
                            butler =
                                manProfileUpdateValidator.butler.validate(
                                    Butler(
                                        age = age,
                                        preferredPet =
                                            _manageManProfileUiState.value.manProfileUpdate
                                                ?.butler
                                                ?.preferredPet
                                                ?: emptyList(),
                                    ),
                                ),
                        ),
                )
        }

        fun updateButlerPreferredPet(preferredPet: PetCategory) {
            val currentPreferredPet =
                _manageManProfileUiState.value.manProfileUpdate
                    ?.butler
                    ?.preferredPet
                    ?.toMutableList()
                    ?: mutableListOf()

            if (currentPreferredPet.contains(preferredPet)) {
                currentPreferredPet.remove(preferredPet)
            } else {
                currentPreferredPet.add(preferredPet)
            }

            _manageManProfileUiState.value =
                _manageManProfileUiState.value.copy(
                    manProfileUpdate =
                        _manageManProfileUiState.value.manProfileUpdate?.copy(
                            butler =
                                _manageManProfileUiState.value.manProfileUpdate?.butler?.copy(
                                    preferredPet = currentPreferredPet,
                                ),
                        ),
                    validationStatus =
                        _manageManProfileUiState.value.validationStatus.copy(
                            butler =
                                manProfileUpdateValidator.butler.validate(
                                    Butler(
                                        age =
                                            _manageManProfileUiState.value.manProfileUpdate
                                                ?.butler
                                                ?.age!!,
                                        preferredPet = currentPreferredPet,
                                    ),
                                ),
                        ),
                )
        }

        fun updateNickName(nickName: String) {
            _manageManProfileUiState.value =
                _manageManProfileUiState.value.copy(
                    nicknameDuplicateCheck = null,
                    manProfileUpdate =
                        _manageManProfileUiState.value.manProfileUpdate?.copy(
                            nickName = nickName,
                        ),
                )
        }

        fun checkNicknameDuplicate() {
            if (_manageManProfileUiState.value.manProfileUpdate
                    ?.nickName
                    .isNullOrEmpty()
            ) {
                return
            }
            viewModelScope.launch {
                checkNicknameDuplicatedUseCase(
                    _manageManProfileUiState.value.manProfileUpdate?.nickName!!,
                ).collect { isDuplicated ->
                    _manageManProfileUiState.value =
                        _manageManProfileUiState.value.copy(nicknameDuplicateCheck = isDuplicated)
                }
            }
        }

        private fun checkValidation(): Boolean =
            _manageManProfileUiState.value.validationStatus.isValid && _manageManProfileUiState.value.nicknameDuplicateCheck == true

        fun modifyManProfile() {
            if (_manageManProfileUiState.value.manProfileUpdate == null) {
                return
            }

            _manageManProfileUiState.value =
                _manageManProfileUiState.value.copy(
                    validationStatus =
                        _manageManProfileUiState.value.validationStatus.copy(
                            profileImageUri =
                                manProfileUpdateValidator.profileImageUri.validate(
                                    _manageManProfileUiState.value.manProfileUpdate!!.profileImageUri,
                                ),
                            nickName =
                                manProfileUpdateValidator.nickName.validate(
                                    _manageManProfileUiState.value.manProfileUpdate!!.nickName,
                                ),
                            bio =
                                manProfileUpdateValidator.bio.validate(
                                    _manageManProfileUiState.value.manProfileUpdate!!.bio,
                                ),
                            butler =
                                manProfileUpdateValidator.butler.validate(
                                    _manageManProfileUiState.value.manProfileUpdate!!.butler,
                                ),
                        ),
                )

            Timber.d("validationStatus: ${_manageManProfileUiState.value.validationStatus}")
            Timber.d("nicknameDuplicateCheck: ${_manageManProfileUiState.value.nicknameDuplicateCheck}")

            if (!checkValidation()) {
                return
            }

            val manProfile =
                ManProfile(
                    profileImageUri = _manageManProfileUiState.value.manProfileUpdate?.profileImageUri,
                    nickName = _manageManProfileUiState.value.manProfileUpdate?.nickName,
                    bio = _manageManProfileUiState.value.manProfileUpdate?.bio,
                    butler = _manageManProfileUiState.value.manProfileUpdate?.butler,
                    type = ProfileType.BUTLER,
                )

            Timber.d("manProfileUpdate: $manProfile")

            viewModelScope.launch {
                modifyManProfileUseCase(
                    _manageManProfileUiState.value.manProfileUpdate!!.id,
                    manProfile,
                ).collect {
                    _uiEvent.emit(it)
                }
            }
        }

        init {
            savedStateHandle.get<String>("profileId")?.let {
                Timber.i("profileId: $it")

                viewModelScope.launch {
                    getProfileDetailUseCase(it).collect { profileDetail ->
                        Timber.i("profileDetail: $profileDetail")

                        _manageManProfileUiState.value =
                            _manageManProfileUiState.value.copy(
                                manProfileUpdate =
                                    ManProfileUpdate(
                                        profileImageUri = Uri.parse(profileDetail.pictureUrl),
                                        nickName = profileDetail.nickname,
                                        bio = profileDetail.introduction,
                                        id = profileDetail.id,
                                        type = ProfileType.BUTLER,
                                        butler =
                                            Butler(
                                                age = profileDetail.butler!!.age,
                                                preferredPet =
                                                    profileDetail.butler?.preferredPet
                                                        ?: emptyList(),
                                            ),
                                    ),
                            )
                    }
                }
            }
        }
    }

@Stable
data class ManageManProfileUiState(
    val manProfileUpdate: ManProfileUpdate?,
    val nicknameDuplicateCheck: Boolean? = null,
    val validationStatus: ManProfileUpdateValidationStatus =
        ManProfileUpdateValidationStatus(
            profileImageUri = FormValidationStatus.Initial(),
            nickName = FormValidationStatus.Initial(),
            bio = FormValidationStatus.Initial(),
            butler = FormValidationStatus.Initial(),
        ),
)

@Stable
data class ManProfileUpdateValidator(
    val profileImageUri: FormValidator<Uri?>,
    val nickName: FormValidator<String>,
    val bio: FormValidator<String?>,
    val butler: FormValidator<Butler?>,
)

@Stable
data class ManProfileUpdateValidationStatus(
    val profileImageUri: FormValidationStatus,
    val nickName: FormValidationStatus,
    val bio: FormValidationStatus,
    val butler: FormValidationStatus,
) {
    val isValid: Boolean
        get() =
            profileImageUri is FormValidationStatus.Valid &&
                nickName is FormValidationStatus.Valid &&
                bio is FormValidationStatus.Valid &&
                butler is FormValidationStatus.Valid
}
