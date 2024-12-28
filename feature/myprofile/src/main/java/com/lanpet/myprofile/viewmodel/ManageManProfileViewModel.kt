package com.lanpet.myprofile.viewmodel

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.common.FormValidator
import com.lanpet.domain.model.profile.Butler
import com.lanpet.domain.model.profile.UserProfileDetail
import com.lanpet.domain.usecase.profile.ModifyManProfileUseCase
import com.lanpet.myprofile.model.ManProfileUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ManageManProfileViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val modifyManProfileUseCase: ModifyManProfileUseCase,
        private val authManager: AuthManager,
    ) : ViewModel() {
        private val _manProfileUpdate =
            MutableStateFlow<ManProfileUpdate?>(null)
        val manProfileUpdate = _manProfileUpdate.asStateFlow()

        private val _uiEvent = MutableSharedFlow<Boolean>()
        val uiEvent = _uiEvent.asSharedFlow()
//
//    fun initProfileDetail(profileDetail: UserProfileDetail) {
//        manProfileUpdate.value = profileDetail
//    }
//
//    fun updateProfileDetail(profileDetail: UserProfileDetail) {
//        manProfileUpdate.value = profileDetail
//    }
//
//    fun modifyManProfile(profileId: String) {
//        val manProfile =
//            ManProfile(
//                profileImageUri = manProfileUpdate.value?.let { Uri.parse(manProfileUpdate.value?.pictureUrl) },
//                nickName = manProfileUpdate.value?.nickname,
//                bio = manProfileUpdate.value?.introduction,
//                butler = manProfileUpdate.value?.butler,
//                type = ProfileType.BUTLER,
//            )
//
//        Timber.d("manProfileUpdate: $manProfile")
//
//        viewModelScope.launch {
//            modifyManProfileUseCase(profileId, manProfile).collect {
//                _uiEvent.emit(it)
//            }
//        }
//    }

        init {
            savedStateHandle.get<String>("profileId")?.let {
                Timber.i("profileId: $it")
                authManager.currentProfileDetail.value.let { profileDetail ->
                    _manProfileUpdate.value =
                        ManProfileUpdate(
                            profileImageUri = Uri.parse(profileDetail?.pictureUrl),
                            nickName = profileDetail?.nickname,
                            bio = profileDetail?.introduction,
                            butler =
                                profileDetail?.butler?.age?.let { it1 ->
                                    profileDetail.butler?.preferredPet?.let { it2 ->
                                        Butler(
                                            age = it1,
                                            preferredPet = it2,
                                        )
                                    }
                                },
                        )
                }
            }
        }
    }

@Stable
data class ManageManProfileUiState(
    val profileDetail: UserProfileDetail?,
    val validator: ManProfileUpdateValidator,
)

@Stable
data class ManProfileUpdateValidator(
    val profileImageUri: FormValidator<Uri>,
    val nickName: FormValidator<String>,
    val bio: FormValidator<String>,
    val butler: FormValidator<Butler>,
)
