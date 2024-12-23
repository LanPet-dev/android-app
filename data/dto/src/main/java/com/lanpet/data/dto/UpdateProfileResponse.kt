package com.lanpet.data.dto

import com.lanpet.domain.model.ProfileType
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileResponse(
    val id: String,
    val type: ProfileType,
    val nickname: String,
    val pictureUrl: String? = null,
    val introduction: String? = null,
    val pet: PetDto? = null,
    val butler: ButlerDto? = null,
    val representative: Boolean,
)
