package com.lanpet.myprofile.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.common.toCompressedByteArray
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.PetProfile
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet
import com.lanpet.domain.usecase.profile.CheckNicknameDuplicatedUseCase
import com.lanpet.domain.usecase.profile.GetProfileDetailUseCase
import com.lanpet.domain.usecase.profile.ManageProfileImageResourceUseCase
import com.lanpet.domain.usecase.profile.ModifyPetProfileUseCase
import com.lanpet.myprofile.model.PetProfileUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
        private val manageProfileImageResourceUseCase: ManageProfileImageResourceUseCase,
        private val authManager: AuthManager,
    ) : ViewModel() {
        private lateinit var originPetProfileUpdate: PetProfileUpdate

        private val _uiState =
            MutableStateFlow(
                ManagePetProfileUiState(
                    petProfileUpdate = null,
                ),
            )
        val uiState = _uiState.asStateFlow()

        private val _uiEvent = MutableSharedFlow<PetProfileUpdateEvent>()
        val uiEvent = _uiEvent.asSharedFlow()

        fun checkNicknameDuplicated() {
            val nickname = _uiState.value.petProfileUpdate?.nickName ?: return

            viewModelScope.launch {
                runCatching {
                    checkNicknameDuplicatedUseCase(nickname).collect { isDuplicated ->
                        _uiState.value =
                            _uiState.value.copy(
                                petProfileUpdate =
                                    _uiState.value.petProfileUpdate?.copy(
                                        nicknameDuplicateChecked = isDuplicated,
                                        shouldCheckNicknameDuplicate = checkNicknameModified(nickname),
                                    ),
                            )
                    }
                }.onFailure {
                    Timber.e(it.stackTraceToString())
                    _uiEvent.emit(PetProfileUpdateEvent.Error(it.message))
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
                )
        }

        fun updateProfileImageUri(profileImageUri: Uri) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileUpdate =
                        _uiState.value.petProfileUpdate?.copy(profileImageUri = profileImageUri),
                )
        }

        fun updateNickName(nickName: String) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileUpdate =
                        _uiState.value.petProfileUpdate?.copy(
                            nickName = nickName,
                            nicknameDuplicateChecked = null,
                            shouldCheckNicknameDuplicate = checkNicknameModified(nickName),
                        ),
                )
        }

        fun updateBio(bio: String) {
            _uiState.value =
                _uiState.value.copy(
                    petProfileUpdate =
                        _uiState.value.petProfileUpdate?.copy(bio = bio),
                )
        }

        private fun checkNicknameModified(nickName: String): Boolean = nickName != originPetProfileUpdate.nickName

        private fun checkValidation(): Boolean = _uiState.value.petProfileUpdate?.checkValidation() == true

        fun modifyPetProfile(context: Context) {
            val petProfileUpdate = _uiState.value.petProfileUpdate ?: return

            if (!checkValidation()) return

            val petProfile =
                PetProfile(
                    type = petProfileUpdate.type,
                    profileImageUri = petProfileUpdate.profileImageUri,
                    nickName =
                        if (_uiState.value.petProfileUpdate?.shouldCheckNicknameDuplicate ==
                            true
                        ) {
                            _uiState.value.petProfileUpdate?.nickName
                        } else {
                            null
                        },
                    bio = petProfileUpdate.bio,
                    pet = petProfileUpdate.pet,
                )

            viewModelScope.launch {
                runCatching {
                    modifyPetProfileUseCase(
                        profileId = petProfileUpdate.id,
                        petProfile = petProfile,
                    ).collect {
                        petProfileUpdate.profileImageUri?.let { uri ->
                            if (uri.toString().startsWith("content://")) {
                                uri.toCompressedByteArray(context)?.let {
                                    manageProfileImageResourceUseCase(
                                        profileId = petProfileUpdate.id,
                                        profileImage = it,
                                    ).first()
                                }
                            }
                        }
                        authManager.getProfiles()
                        _uiEvent.emit(PetProfileUpdateEvent.Success(petProfile))
                    }
                }.onFailure {
                    _uiEvent.emit(PetProfileUpdateEvent.Fail(it.message))
                }
            }
        }

        init {
            savedStateHandle.get<String>("profileId")?.let {
                Timber.i("profileId: $it")

                viewModelScope.launch {
                    runCatching {
                        getProfileDetailUseCase(it)
                            .collect { profileDetail ->
                                Timber.i("profileDetail: $profileDetail")

                                val petProfileUpdate =
                                    PetProfileUpdate(
                                        profileImageUri =
                                            profileDetail.pictureUrl?.let {
                                                Uri.parse(
                                                    it,
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
                                                feature = profileDetail.pet?.feature,
                                                weight = profileDetail.pet?.weight,
                                                birthDate = profileDetail.pet?.birthDate,
                                            ),
                                        shouldCheckNicknameDuplicate = null,
                                        nicknameDuplicateChecked = null,
                                    )

                                originPetProfileUpdate = petProfileUpdate

                                _uiState.value =
                                    _uiState.value.copy(
                                        petProfileUpdate = petProfileUpdate,
                                    )
                            }
                    }.onFailure {
                        Timber.e(it.stackTraceToString())
                        _uiEvent.emit(PetProfileUpdateEvent.Error(it.message))
                    }
                }
            }
        }
    }

@Stable
data class ManagePetProfileUiState(
    val petProfileUpdate: PetProfileUpdate?,
)

@Stable
sealed class PetProfileUpdateEvent {
    data class Success(
        val petProfile: PetProfile,
    ) : PetProfileUpdateEvent()

    data class Fail(
        val message: String?,
    ) : PetProfileUpdateEvent()

    data class Error(
        val message: String?,
    ) : PetProfileUpdateEvent()
}
