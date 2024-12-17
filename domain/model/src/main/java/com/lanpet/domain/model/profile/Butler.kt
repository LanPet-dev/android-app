package com.lanpet.domain.model.profile

import com.lanpet.domain.model.PetCategory

data class Butler(
    val ageRange: Int,
    val preferredPet: PetCategory?,
)
