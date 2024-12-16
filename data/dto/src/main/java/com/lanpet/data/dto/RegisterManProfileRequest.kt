package com.lanpet.data.dto

import com.google.gson.annotations.SerializedName
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
    @SerializedName("type")
    val profileType: ProfileType = ProfileType.BUTLER,
    val nickname: String,
    @SerializedName("pictureUrl")
    val profileImageUrl: String? = null,
    val introduction: String? = null,
    val butler: Butler,
)

@Serializable
data class Butler(
    val ageRange: Int,
    val preferredPet: PetCategory?,
)
