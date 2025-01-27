package com.lanpet.domain.model.free

import com.lanpet.domain.model.Profile

data class FreeBoardComment(
    val id: String,
    val profile: Profile,
    val comment: String?,
    val createdAt: String,
    val subComments: List<FreeBoardSubComment> = emptyList(),
)

data class FreeBoardSubComment(
    val id: String,
    val profile: Profile,
    val comment: String?,
    val createdAt: String?,
)
