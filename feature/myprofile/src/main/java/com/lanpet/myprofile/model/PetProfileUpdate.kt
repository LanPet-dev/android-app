package com.lanpet.myprofile.model

import android.net.Uri
import androidx.compose.runtime.Stable
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet

@Stable
data class PetProfileUpdate(
    val id: String,
    val type: ProfileType?,
    val profileImageUri: Uri?,
    val nickName: String?,
    val bio: String?,
    val pet: Pet?,
    val shouldCheckNicknameDuplicate: Boolean?,
    val nicknameDuplicateChecked: Boolean?,
) {
    fun checkValidation(): Boolean {
        try {
            require(type != null)
            require(pet != null)
            require(!pet.breed.isNullOrBlank())

            nickName?.let {
                require(it.isNotEmpty()) { "nickName must not be empty" }
                require(it.length >= 2) { "nickName must be at least 2 characters" }
                require(it.length <= 20) { "nickName must be at most 20 characters" }
            }

            if (shouldCheckNicknameDuplicate == true) {
                require(nicknameDuplicateChecked == true)
            }

            return true
        } catch (e: Exception) {
            return false
        }
    }

    init {
        require(id.isNotEmpty()) { "id must not be empty" }
    }
}
