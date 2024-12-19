package com.lanpet.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.Age
import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Butler
import com.lanpet.domain.usecase.profile.RegisterManProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManProfileCreateViewModel
    @Inject
    constructor(
        private val registerManProfileUseCase: RegisterManProfileUseCase,
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
                            ageRange = 0,
                            preferredPet = emptyList(),
                        ),
                ),
            )

        private val _registerManProfileResult =
            MutableStateFlow<RegisterManProfileResult>(RegisterManProfileResult.Initial)
        val registerManProfileResult: StateFlow<RegisterManProfileResult> =
            _registerManProfileResult.asStateFlow()

        val manProfileCreate: StateFlow<ManProfileCreate> = _manProfileCreate.asStateFlow()

        fun setProfileImageUri(uri: String) {
            _manProfileCreate.value = _manProfileCreate.value.copy(profileImageUri = Uri.parse(uri))
        }

        fun setNickName(nickName: String) {
            _manProfileCreate.value = _manProfileCreate.value.copy(nickName = nickName)
        }

        fun setAge(age: Age) {
            _manProfileCreate.value =
                _manProfileCreate.value.copy(butler = _manProfileCreate.value.butler.copy(ageRange = age.intValue))
        }

        fun setBio(bio: String) {
            _manProfileCreate.value = _manProfileCreate.value.copy(bio = bio)
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
                            ageRange = 0,
                            preferredPet = emptyList(),
                        ),
                )
        }

        fun updatePreferPet(category: PetCategory) {
            val tmpList =
                _manProfileCreate.value.butler.preferredPet
                    .toMutableList()
            tmpList.add(category)
            tmpList.distinct()

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
    }

sealed class RegisterManProfileResult {
    data object Loading : RegisterManProfileResult()

    data object Success : RegisterManProfileResult()

    data class Error(
        val message: String,
    ) : RegisterManProfileResult()

    data object Initial : RegisterManProfileResult()
}
