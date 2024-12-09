package com.example.dto

import com.example.model.ProfileType
import kotlinx.serialization.Serializable

@Serializable
data class RegisterProfileResponse(
    val type: ProfileType
)
