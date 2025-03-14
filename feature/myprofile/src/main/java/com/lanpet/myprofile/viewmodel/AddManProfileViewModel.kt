package com.lanpet.myprofile.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.FormValidator
import com.lanpet.core.common.toCompressedByteArray
import com.lanpet.domain.model.Age
import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Butler
import com.lanpet.domain.usecase.profile.CheckNicknameDuplicatedUseCase
import com.lanpet.domain.usecase.profile.RegisterManProfileUseCase
import com.lanpet.domain.usecase.profile.UploadProfileImageResourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddManProfileViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val registerManProfileUseCase: RegisterManProfileUseCase,
        private val uploadProfileImageResourceUseCase: UploadProfileImageResourceUseCase,
        private val checkNicknameDuplicatedUseCase: CheckNicknameDuplicatedUseCase,
        private val authManager: AuthManager,
    ) : ViewModel() {
        private val _uiState =
            MutableStateFlow(
                AddManProfileUiState(
                    manProfileCreate =
                        ManProfileCreate(
                            profileImageUri = null,
                            nickName = "",
                            bio = "",
                            butler =
                                Butler(
                                    age = Age.NONE,
                                    preferredPet = emptyList(),
                                ),
                            type = ProfileType.BUTLER,
                        ),
                ),
            )
        val uiState = _uiState.asStateFlow()

        val isValidState =
            _uiState
                .map { state ->
                    val nickNameValidation =
                        manProfileUpdateValidator.nickName.validate(
                            state.manProfileCreate?.nickName ?: "",
                        )
                    val bioValidation =
                        manProfileUpdateValidator.bio.validate(state.manProfileCreate?.bio ?: "")
                    val butlerValidation =
                        manProfileUpdateValidator.butler.validate(state.manProfileCreate?.butler)
                    val profileImageValidation =
                        manProfileUpdateValidator.profileImageUri.validate(state.manProfileCreate?.profileImageUri)

                    nickNameValidation is FormValidationStatus.Valid &&
                        bioValidation is FormValidationStatus.Valid &&
                        butlerValidation is FormValidationStatus.Valid &&
                        profileImageValidation is FormValidationStatus.Valid &&
                        state.nicknameDuplicateCheck == true
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = false,
                )

        private val manProfileUpdateValidator =
            AddManProfileValidator(
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
            _uiState.value =
                _uiState.value.copy(
                    manProfileCreate =
                        _uiState.value.manProfileCreate?.copy(
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
                    manProfileCreate =
                        _uiState.value.manProfileCreate?.copy(
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
                    manProfileCreate =
                        _uiState.value.manProfileCreate
                            ?.butler
                            ?.copy(
                                age = age,
                            )?.let {
                                _uiState.value.manProfileCreate?.copy(
                                    butler =
                                    it,
                                )
                            },
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            butler =
                                manProfileUpdateValidator.butler.validate(
                                    Butler(
                                        age = age,
                                        preferredPet =
                                            _uiState.value.manProfileCreate
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
                _uiState.value.manProfileCreate
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
                    manProfileCreate =
                        _uiState.value.manProfileCreate
                            ?.butler
                            ?.copy(
                                preferredPet = currentPreferredPet,
                            )?.let {
                                _uiState.value.manProfileCreate?.copy(
                                    butler =
                                    it,
                                )
                            },
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            butler =
                                manProfileUpdateValidator.butler.validate(
                                    _uiState.value.manProfileCreate
                                        ?.butler
                                        ?.age
                                        ?.let {
                                            Butler(
                                                age =
                                                it,
                                                preferredPet = currentPreferredPet,
                                            )
                                        },
                                ),
                        ),
                )
        }

        fun updateNickName(nickName: String) {
            _uiState.value =
                _uiState.value.copy(
                    nicknameDuplicateCheck = null,
                    manProfileCreate =
                        _uiState.value.manProfileCreate?.copy(
                            nickName = nickName,
                        ),
                )
        }

        fun checkNicknameDuplicate() {
            if (_uiState.value.manProfileCreate
                    ?.nickName
                    .isNullOrEmpty()
            ) {
                return
            }

            viewModelScope.launch {
                runCatching {
                    checkNicknameDuplicatedUseCase(
                        _uiState.value.manProfileCreate?.nickName!!,
                    ).collect { isDuplicated ->
                        _uiState.value =
                            _uiState.value.copy(nicknameDuplicateCheck = isDuplicated)
                    }
                }.onFailure {
                    Timber.e(it)
                }
            }
        }

        fun checkValidation(): Boolean = _uiState.value.validationStatus.isValid && _uiState.value.nicknameDuplicateCheck == true

        fun addManProfile(context: Context) {
            val manProfileCreate = _uiState.value.manProfileCreate ?: return

            if (!isValidState.value) {
                return
            }

            viewModelScope.launch {
                runCatching {
                    registerManProfileUseCase(
                        manProfileCreate = manProfileCreate,
                    ).collect { profileId ->
                        val uri = manProfileCreate.profileImageUri
                        // 업로드 할 profile image 존재 시
                        if (uri != null) {
                            uri.toCompressedByteArray(context)?.let { byteArray ->
                                uploadProfileImageResourceUseCase(
                                    profileId = profileId,
                                    profileImage = byteArray,
                                ).first()
                            }
                        }
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
data class AddManProfileUiState(
    val manProfileCreate: ManProfileCreate?,
    val nicknameDuplicateCheck: Boolean? = null,
    val validationStatus: AddManProfileValidationStatus =
        AddManProfileValidationStatus(
            profileImageUri = FormValidationStatus.Initial(),
            nickName = FormValidationStatus.Initial(),
            bio = FormValidationStatus.Initial(),
            butler = FormValidationStatus.Initial(),
        ),
)

@Stable
data class AddManProfileValidator(
    val profileImageUri: FormValidator<Uri?>,
    val nickName: FormValidator<String>,
    val bio: FormValidator<String?>,
    val butler: FormValidator<Butler?>,
)

@Stable
data class AddManProfileValidationStatus(
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
