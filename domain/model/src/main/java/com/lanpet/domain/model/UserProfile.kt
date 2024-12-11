package com.lanpet.domain.model

data class UserProfile(
    val id: String,
    val name: String,
    val age: Age,
    val pet: PetCategory,
    val preferPet: List<PetCategory>,
    val profileImage: String?,
)
