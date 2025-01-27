package com.lanpet.domain.model.free

import com.lanpet.domain.model.Profile
import kotlinx.serialization.Serializable

@Serializable
data class FreeBoardComment(
    val id: String,
    val profile: Profile,
    val comment: String?,
    val createdAt: String,
    val subComments: List<FreeBoardSubComment> = emptyList(),
)

@Serializable
data class FreeBoardSubComment(
    val id: String,
    val profile: Profile,
    val comment: String?,
    val createdAt: String?,
)
