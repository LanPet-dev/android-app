package com.lanpet.data.dto

import com.lanpet.domain.model.PetCategory
import kotlinx.serialization.Serializable

@Serializable
data class PetDto(
    val petType: PetCategory,
    val breed: String? = null,
    val feature: List<String> = emptyList(),
    // To date format string
    val birthDate: String? = null,
    val weight: Double? = null,
)
