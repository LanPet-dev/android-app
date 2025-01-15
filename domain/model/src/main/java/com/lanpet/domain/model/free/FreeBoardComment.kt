package com.lanpet.domain.model.free

import com.lanpet.domain.model.Profile

data class FreeBoardComment(
    val id: String,
    val profile: Profile,
    val comment: String?,
)
