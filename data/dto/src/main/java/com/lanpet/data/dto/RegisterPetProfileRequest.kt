package com.lanpet.data.dto

import com.google.gson.annotations.SerializedName
import com.lanpet.domain.model.ProfileType
import kotlinx.serialization.Serializable

/** ex)
 * {
 *   "type": "PET",
 *   "nickname": "test2",
 *   "pictureUrl": "https://www.naver.com",
 *   "introduction": "잘 부탁 드립니다~",
 *   "pet": {
 *     "petType": "DOG",
 *     "breed": "푸들",
 *     "feature": "잘 먹어요, 분노를 조절을 잘 해요, 신기가 들린거 같아요",
 *     "weight": 5.5
 *   }
 * }
 */
@Serializable
data class RegisterPetProfileRequest(
    @SerializedName("type")
    val profileType: ProfileType = ProfileType.PET,
    val nickname: String,
    val pictureUrl: String? = null,
    val introduction: String? = null,
    val pet: PetDto,
)
