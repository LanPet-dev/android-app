package com.lanpet.data.dto

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
) {
    fun toDomain() =
        com.lanpet.domain.model.profile.Pet(
            petCategory = petType,
            breed = breed,
            feature = feature?.split(",") ?: emptyList(),
            weight = weight,
            birthDate = birthDate,
        )
}
