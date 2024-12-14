package com.lanpet.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.PetProfileCreate
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
                    petCategory = null,
                    species = "",
                    bio = "",
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
            _petProfileCreate.update { it.copy(petCategory = petCategory) }
        }

        fun setSpecies(species: String) {
            _petProfileCreate.update { it.copy(species = species) }
        }

        fun setBio(bio: String) {
            _petProfileCreate.update { it.copy(bio = bio) }
        }

        fun validatePetProfileCreate(): Boolean {
            val currentValue = _petProfileCreate.value
            return currentValue.nickName.isNotEmpty() &&
                currentValue.petCategory != null &&
                currentValue.species.isNotEmpty() &&
                currentValue.bio.isNotEmpty()
        }

        fun clear() {
            _petProfileCreate.update {
                PetProfileCreate(
                    profileImageUri = null,
                    nickName = "",
                    petCategory = null,
                    species = "",
                    bio = "",
                )
            }
        }
    }
