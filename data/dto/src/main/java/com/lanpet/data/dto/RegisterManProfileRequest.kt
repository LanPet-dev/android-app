package com.lanpet.data.dto

import com.google.gson.annotations.SerializedName
import com.lanpet.domain.model.Age
import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import kotlinx.serialization.Serializable

/** ex)
 * {
 *   "type": "BUTLER",
 *   "nickname": "test2",
 *   "pictureUrl": "https://www.naver.com",
 *   "introduction": "잘 부탁 드립니다~",
 *   "butler": {
 *     "ageRange": 30,
 *     "preferredPet": "DOG"
 *   }
 * }
 */
@Serializable
data class RegisterManProfileRequest(
    @SerializedName("type") val profileType: ProfileType = ProfileType.BUTLER,
    val nickname: String,
    @SerializedName("pictureUrl") val profileImageUrl: String? = null,
    val introduction: String? = null,
    val butler: ButlerDto,
) {
    companion object {
        @JvmStatic
        fun fromDomain(manProfileCreate: ManProfileCreate): RegisterManProfileRequest =
            RegisterManProfileRequest(
                nickname = manProfileCreate.nickName,
                profileImageUrl = manProfileCreate.profileImageUri.toString(),
                introduction = manProfileCreate.bio,
                profileType = manProfileCreate.type,
                butler =
                    ButlerDto(
                        ageRange = manProfileCreate.butler.age.intValue,
                        preferredPet = manProfileCreate.butler.preferredPet,
                    ),
            )
    }
}

@Serializable
data class ButlerDto(
    val ageRange: Int,
    val preferredPet: List<PetCategory> = emptyList(),
) {
    fun toDomain() =
        com.lanpet.domain.model.profile.Butler(
            age = Age.entries.firstOrNull { it.intValue == ageRange } ?: Age.NONE,
            preferredPet = preferredPet,
        )
}
