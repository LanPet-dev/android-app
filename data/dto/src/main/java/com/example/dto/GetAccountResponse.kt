package com.example.dto

import com.example.model.AuthorityType
import com.example.model.account.Account
import kotlinx.serialization.Serializable

@Serializable
data class GetAccountResponse(
    val accountId: String,
    val authId: String,
    val authority: AuthorityType,
    val exitDate: String?,
    val exitReason: String?
)

fun GetAccountResponse.toDomain() = Account(
    accountId = accountId,
    authId = authId,
    authority = authority,
    exitDate = exitDate,
    exitReason = exitReason
)

