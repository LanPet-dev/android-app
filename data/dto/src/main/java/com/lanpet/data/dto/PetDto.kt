package com.lanpet.data.dto

import androidx.compose.runtime.Stable
import com.lanpet.domain.model.PetCategory
import kotlinx.serialization.Serializable

@Serializable
data class PetDto(
    val petType: PetCategory,
    val breed: String? = null,
    val feature: String? = null,
    // To date format string
    val birthDate: String? = null,
    val weight: Double? = null,
)
