package com.lanpet.myprofile.viewmodel

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                ManageManProfileUiState(
                    manProfileUpdate = null,
                ),
            )
        val uiState = _uiState.asStateFlow()

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

        private val _uiEvent = MutableSharedFlow<ManageManProfileUiEvent>()
        val uiEvent = _uiEvent.asSharedFlow()

        fun updateProfileImageUri(uri: Uri) {
            _uiState.value =
                _uiState.value.copy(
                    manProfileUpdate =
                        _uiState.value.manProfileUpdate?.copy(
                            profileImageUri = uri,
                        ),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            profileImageUri = manProfileUpdateValidator.profileImageUri.validate(uri),
                        ),
                )
        }

        fun updateBio(bio: String) {
            _uiState.value =
                _uiState.value.copy(
                    manProfileUpdate =
                        _uiState.value.manProfileUpdate?.copy(
                            bio = bio,
                        ),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            bio = manProfileUpdateValidator.bio.validate(bio),
                        ),
                )
        }

        fun updateButlerAge(age: Age) {
            _uiState.value =
                _uiState.value.copy(
                    manProfileUpdate =
                        _uiState.value.manProfileUpdate?.copy(
                            butler =
                                _uiState.value.manProfileUpdate?.butler?.copy(
                                    age = age,
                                ),
                        ),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            butler =
                                manProfileUpdateValidator.butler.validate(
                                    Butler(
                                        age = age,
                                        preferredPet =
                                            _uiState.value.manProfileUpdate
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
                _uiState.value.manProfileUpdate
                    ?.butler
                    ?.preferredPet
                    ?.toMutableList()
                    ?: mutableListOf()

            if (currentPreferredPet.contains(preferredPet)) {
                currentPreferredPet.remove(preferredPet)
            } else {
                currentPreferredPet.add(preferredPet)
            }

            _uiState.value =
                _uiState.value.copy(
                    manProfileUpdate =
                        _uiState.value.manProfileUpdate?.copy(
                            butler =
                                _uiState.value.manProfileUpdate?.butler?.copy(
                                    preferredPet = currentPreferredPet,
                                ),
                        ),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            butler =
                                manProfileUpdateValidator.butler.validate(
                                    Butler(
                                        age =
                                            _uiState.value.manProfileUpdate
                                                ?.butler
                                                ?.age!!,
                                        preferredPet = currentPreferredPet,
                                    ),
                                ),
                        ),
                )
        }

        fun updateNickName(nickName: String) {
            _uiState.value =
                _uiState.value.copy(
                    nicknameDuplicateCheck = null,
                    manProfileUpdate =
                        _uiState.value.manProfileUpdate?.copy(
                            nickName = nickName,
                        ),
                )
        }

        fun checkNicknameDuplicate() {
            if (_uiState.value.manProfileUpdate
                    ?.nickName
                    .isNullOrEmpty()
            ) {
                return
            }
            viewModelScope.launch {
                checkNicknameDuplicatedUseCase(
                    _uiState.value.manProfileUpdate?.nickName!!,
                ).collect { isDuplicated ->
                    _uiState.value =
                        _uiState.value.copy(nicknameDuplicateCheck = isDuplicated)
                }
            }
        }

        private fun checkValidation(): Boolean = _uiState.value.validationStatus.isValid && _uiState.value.nicknameDuplicateCheck == true

        fun modifyManProfile() {
            if (_uiState.value.manProfileUpdate == null) {
                return
            }

            _uiState.value =
                _uiState.value.copy(
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            profileImageUri =
                                manProfileUpdateValidator.profileImageUri.validate(
                                    _uiState.value.manProfileUpdate!!.profileImageUri,
                                ),
                            nickName =
                                manProfileUpdateValidator.nickName.validate(
                                    _uiState.value.manProfileUpdate!!.nickName,
                                ),
                            bio =
                                manProfileUpdateValidator.bio.validate(
                                    _uiState.value.manProfileUpdate!!.bio,
                                ),
                            butler =
                                manProfileUpdateValidator.butler.validate(
                                    _uiState.value.manProfileUpdate!!.butler,
                                ),
                        ),
                )

            Timber.d("validationStatus: ${_uiState.value.validationStatus}")
            Timber.d("nicknameDuplicateCheck: ${_uiState.value.nicknameDuplicateCheck}")

            if (!checkValidation()) {
                return
            }

            val manProfile =
                ManProfile(
                    profileImageUri = _uiState.value.manProfileUpdate?.profileImageUri,
                    nickName = _uiState.value.manProfileUpdate?.nickName,
                    bio = _uiState.value.manProfileUpdate?.bio,
                    butler = _uiState.value.manProfileUpdate?.butler,
                    type = ProfileType.BUTLER,
                )

            Timber.d("manProfileUpdate: $manProfile")

            viewModelScope.launch {
                runCatching {
                    modifyManProfileUseCase(
                        _uiState.value.manProfileUpdate!!.id,
                        manProfile,
                    ).collect {
                        _uiEvent.emit(
                            ManageManProfileUiEvent.Success,
                        )
                    }
                }.onFailure {
                    _uiEvent.emit(ManageManProfileUiEvent.Fail(it.message))
                }
            }
        }

        init {
            savedStateHandle.get<String>("profileId")?.let {
                Timber.i("profileId: $it")

                viewModelScope.launch {
                    runCatching {
                        getProfileDetailUseCase(it).collect { profileDetail ->
                            Timber.i("profileDetail: $profileDetail")

                            _uiState.value =
                                _uiState.value.copy(
                                    manProfileUpdate =
                                    ManProfileUpdate(
                                        profileImageUri =
                                        profileDetail.pictureUrl?.let {
                                            Uri.parse(
                                                profileDetail.pictureUrl,
                                            )
                                        },
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
                    }.onFailure {
                        Timber.e(it.stackTraceToString())
                        _uiEvent.emit(ManageManProfileUiEvent.Fail(it.message))
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

@Stable
sealed class ManageManProfileUiEvent {
    data object Success : ManageManProfileUiEvent()

    data class Error(
        val message: String?,
    ) : ManageManProfileUiEvent()

    data class Fail(
        val message: String?,
    ) : ManageManProfileUiEvent()
}
