package com.lanpet.data.dto

import com.lanpet.domain.model.account.AccountToken
import kotlinx.serialization.Serializable

@Serializable
data class RegisterAccountResponse(
    val accountId: String,
)

fun RegisterAccountResponse.toDomain() =
    AccountToken(
        accountId = accountId,
    )
