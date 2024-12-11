package com.lanpet.domain.model.account

import com.lanpet.domain.model.AuthorityType

data class Account(
    val accountId: String,
    val authId: String,
    val authority: AuthorityType,
    val exitDate: String?,
    val exitReason: String?
)
