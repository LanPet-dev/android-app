package com.lanpet.domain.model.profile

import com.lanpet.domain.model.PetCategory

data class Pet(
    val petCategory: PetCategory,
    val breed: String?,
    val feature: List<String> = emptyList(),
    val weight: Double?,
    val birthDate: String?,
)
