package com.lanpet.data.dto

import com.lanpet.domain.model.ProfileType
import kotlinx.serialization.Serializable

@Serializable
data class FindProfileDetailResponse(
    val id: String,
    val type: ProfileType,
    val nickname: String,
    val pictureUrl: String? = null,
    val introduction: String? = null,
    val pet: PetDto? = null,
    val butler: ButlerDto? = null,
) {
    fun toDomain() =
        com.lanpet.domain.model.profile.UserProfileDetail(
            id = id,
            type = type,
            nickname = nickname,
            pictureUrl = pictureUrl,
            introduction = introduction,
            pet = pet?.toDomain(),
            butler = butler?.toDomain(),
        )
}
