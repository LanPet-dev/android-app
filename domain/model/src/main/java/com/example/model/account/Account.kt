package com.example.model.account

import com.example.model.AuthorityType

data class Account(
    val accountId: String,
    val authId: String,
    val authority: AuthorityType,
    val exitDate: String?,
    val exitReason: String?
)
