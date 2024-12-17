package com.lanpet.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.lanpet.domain.model.Age
import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Butler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ManProfileCreateViewModel
    @Inject
    constructor() : ViewModel() {
        init {
            println("ManProfileCreateViewModel ${hashCode()} init")
        }

        private val _manProfileCreate =
            MutableStateFlow(
                ManProfileCreate(
                    profileImageUri = null,
                    nickName = "",
                    bio = "",
                    preferPets = emptyList(),
                    type = ProfileType.BUTLER,
                    butler =
                        Butler(
                            ageRange = 0,
                            preferredPet = null,
                        ),
                ),
            )

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
                    preferPets = emptyList(),
                    type = ProfileType.BUTLER,
                    butler =
                        Butler(
                            ageRange = 0,
                            preferredPet = null,
                        ),
                )
        }

        fun updatePreferPet(category: PetCategory) {
            val preferPets = _manProfileCreate.value.preferPets.toMutableSet()

            if (preferPets.contains(category)) {
                preferPets.remove(category)
            } else {
                preferPets.add(category)
            }
            _manProfileCreate.value =
                _manProfileCreate.value.copy(preferPets = preferPets.toList())
        }
    }
