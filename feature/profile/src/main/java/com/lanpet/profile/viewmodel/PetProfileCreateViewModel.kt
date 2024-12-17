package com.lanpet.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PetProfileCreateViewModel
    @Inject
    constructor() : ViewModel() {
        private val _petProfileCreate =
            MutableStateFlow(
                PetProfileCreate(
                    profileImageUri = null,
                    nickName = "",
                    bio = "",
                    type = ProfileType.PET,
                    pet =
                        Pet(
                            petCategory = PetCategory.ETC,
                            breed = null,
                            feature = emptyList(),
                            weight = null,
                            birthDate = null,
                        ),
                ),
            )
        val petProfileCreate: StateFlow<PetProfileCreate> = _petProfileCreate.asStateFlow()

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

        fun validatePetProfileCreate(): Boolean {
            // TODO
            throw NotImplementedError()
        }

        fun clear() {
            // TODO
            throw NotImplementedError()
        }
    }
