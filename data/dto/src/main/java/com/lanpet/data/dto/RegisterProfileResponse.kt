package com.lanpet.data.dto

import com.lanpet.domain.model.ProfileType
import kotlinx.serialization.Serializable

@Serializable
data class RegisterProfileResponse(
    val type: ProfileType
)
