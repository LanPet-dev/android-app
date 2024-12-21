package com.lanpet.data.dto

import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.UserProfile
import kotlinx.serialization.Serializable

/** ex)
 * {
 *   "items": [
 *     {
 *       "id": "01JF5KCKQ965CPC56597Z029FP",
 *       "type": "PET",
 *       "nickname": "test",
 *       "pictureUrl": "https://www.naver.com",
 *       "introduction": "잘 부탁 드립니다~"
 *     },
 *     {
 *       "id": "01JF5KFASJPCAA5JFYE87FTBEN",
 *       "type": "BUTLER",
 *       "nickname": "test2",
 *       "pictureUrl": "https://www.naver.com",
 *       "introduction": "잘 부탁 드립니다~"
 *     },
 *     {
 *       "id": "01JF5KH712B47D69WT5Q9J43D8",
 *       "type": "BUTLER",
 *       "nickname": "test2",
 *       "pictureUrl": "https://www.naver.com",
 *       "introduction": "잘 부탁 드립니다~"
 *     },
 *     {
 *       "id": "01JF5KQ03NAG069X28K1G5ZN1N",
 *       "type": "PET",
 *       "nickname": "test2",
 *       "pictureUrl": "https://www.naver.com",
 *       "introduction": "잘 부탁 드립니다~"
 *     }
 *   ],
 *   "totalCount": 4
 * }
 */
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
