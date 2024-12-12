package com.lanpet.data.dto

import com.lanpet.domain.model.AuthorityType
import com.lanpet.domain.model.account.Account
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

