package com.lanpet.data.dto

import com.google.gson.annotations.SerializedName
import com.lanpet.domain.model.PetProfile
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.ProfileType
import kotlinx.serialization.Serializable

@Serializable
data class RegisterPetProfileRequest(
    @SerializedName("type")
    val profileType: ProfileType = ProfileType.PET,
    val nickname: String,
    val pictureUrl: String?,
    val introduction: String?,
    val pet: PetDto,
) {
    companion object {
        @JvmStatic
        fun fromDomainToRegisterRequest(petProfileCreate: PetProfileCreate): RegisterPetProfileRequest =
            RegisterPetProfileRequest(
                nickname = petProfileCreate.nickName,
                pictureUrl =
                    petProfileCreate.profileImageUri?.toString(),
                introduction = petProfileCreate.bio,
                profileType = petProfileCreate.type,
                pet =
                    PetDto(
                        petType = petProfileCreate.pet.petCategory,
                        breed = petProfileCreate.pet.breed,
                        feature = petProfileCreate.pet.feature?.joinToString(","),
                        weight = petProfileCreate.pet.weight,
                    ),
            )

        @JvmStatic
        fun fromDomainToUpdateRequest(petProfile: PetProfile): UpdateProfileRequest =
            UpdateProfileRequest(
                nickname = petProfile.nickName,
                pictureUrl = petProfile.profileImageUri?.toString(),
                introduction = petProfile.bio,
                pet =
                    petProfile.pet?.let {
                        PetDto(
                            petType = it.petCategory,
                            breed = petProfile.pet?.breed,
                            feature = null,
//                            feature = petProfile.pet?.feature?.joinToString(","),
                            weight = petProfile.pet?.weight,
                        )
                    },
            )
    }
}
