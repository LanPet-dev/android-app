package com.lanpet.data.dto

import com.lanpet.domain.model.FreeBoardPostLike
import kotlinx.serialization.Serializable

@Serializable
data class DoPostLikeRequest(
    val profileId: String,
) {
    companion object {
        @JvmStatic
        fun fromDomainToRequest(freeBoardPostLike: FreeBoardPostLike): DoPostLikeRequest =
            DoPostLikeRequest(
                profileId = freeBoardPostLike.profileId,
            )
    }
}
