package com.lanpet.domain.model.profile

import com.lanpet.domain.model.Age
import com.lanpet.domain.model.PetCategory

data class Butler(
    val age: Age,
    val preferredPet: List<PetCategory> = emptyList(),
)
