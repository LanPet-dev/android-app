package com.lanpet.data.dto

import com.lanpet.data.dto.serializer.UpdateProfileRequestSerializer
import com.lanpet.domain.model.UserProfile
import kotlinx.serialization.Serializable

@Serializable(with = UpdateProfileRequestSerializer::class)
data class UpdateProfileRequest(
    val nickname: String? = null,
    val pictureUrl: String? = null,
    val introduction: String? = null,
    val pet: PetDto? = null,
    val butler: ButlerDto? = null,
) {
    companion object {
        @JvmStatic
        fun fromDomain(userProfile: UserProfile) {
            // TODO
        }
    }
}
