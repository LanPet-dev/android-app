package com.lanpet.data.dto

import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.UserProfile
import kotlinx.serialization.Serializable

@Serializable
data class FindProfileResponse(
    val items: List<FindProfileData>,
    val totalCount: Int = items.size,
)

fun FindProfileResponse.toDomain() =
    items.map {
        UserProfile(
            id = it.id,
            type = it.type,
            introduction = it.introduction,
            nickname = it.nickname,
            profileImageUri = it.pictureUrl,
        )
    }

@Serializable
data class FindProfileData(
    val id: String,
    val type: ProfileType,
    val nickname: String,
    val pictureUrl: String? = null,
    val introduction: String? = null,
)
