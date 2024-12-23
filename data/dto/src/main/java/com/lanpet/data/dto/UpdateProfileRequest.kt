package com.lanpet.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    val nickname: String,
    val pictureUrl: String? = null,
    val introduction: String? = null,
    val pet: PetDto? = null,
    val butler : ButlerDto? = null,
    val representative: Boolean,
)
