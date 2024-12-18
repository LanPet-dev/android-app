package com.lanpet.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet
import com.lanpet.domain.usecase.profile.RegisterPetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetProfileCreateViewModel
    @Inject
    constructor(
        private val registerPetProfileUseCase: RegisterPetProfileUseCase,
    ) : ViewModel() {
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

        private val _registerPetProfileResult =
            MutableStateFlow<RegisterPetProfileResult>(RegisterPetProfileResult.Initial)
        val registerPetProfileResult: StateFlow<RegisterPetProfileResult> =
            _registerPetProfileResult.asStateFlow()

        fun setProfileImageUri(uri: String) {
            _petProfileCreate.update { it.copy(profileImageUri = Uri.parse(uri)) }
        }

        fun setNickName(nickName: String) {
            _petProfileCreate.update { it.copy(nickName = nickName) }
        }

        fun setPetCategory(petCategory: PetCategory) {
            _petProfileCreate.update {
                it.copy(
                    pet = it.pet.copy(petCategory = petCategory),
                )
            }
        }

        fun setBreed(breed: String) {
            _petProfileCreate.update {
                it.copy(
                    pet = it.pet.copy(breed = breed),
                )
            }
        }

        fun setBio(bio: String) {
            _petProfileCreate.update { it.copy(bio = bio) }
        }

        fun registerPetProfile() {
            viewModelScope.launch {
                try {
                    _registerPetProfileResult.value = RegisterPetProfileResult.Loading
                    registerPetProfileUseCase(_petProfileCreate.value)
                    _registerPetProfileResult.value = RegisterPetProfileResult.Success
                } catch (e: Exception) {
                    _registerPetProfileResult.value =
                        RegisterPetProfileResult.Error(e.message ?: "Unknown error")
                }
            }
        }

        fun validatePetProfileCreate(): Boolean {
            // TODO
            throw NotImplementedError()
        }

        fun clear() {
            // TODO
            throw NotImplementedError()
        }
    }

sealed class RegisterPetProfileResult {
    data object Loading : RegisterPetProfileResult()

    data object Success : RegisterPetProfileResult()

    data class Error(
        val message: String,
    ) : RegisterPetProfileResult()

    data object Initial : RegisterPetProfileResult()
}
