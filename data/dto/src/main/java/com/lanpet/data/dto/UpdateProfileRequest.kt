package com.lanpet.data.dto

import com.lanpet.domain.model.UserProfile
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val nickname: String,
    val pictureUrl: String? = null,
    val introduction: String? = null,
    val pet: PetDto? = null,
    val butler: ButlerDto? = null,
    val representative: Boolean,
) {
    companion object {
        @JvmStatic
        fun fromDomain(userProfile: UserProfile) {
            // TODO
        }
    }
}
