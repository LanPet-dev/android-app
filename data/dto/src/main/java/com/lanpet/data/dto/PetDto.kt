package com.lanpet.data.dto

import com.lanpet.domain.model.PetCategory
import kotlinx.serialization.Serializable

@Serializable
data class PetDto(
    val petType: PetCategory,
    val feature: List<String>,
    // To date format string
    val birthDate: String,
    val weight: Double,
)
