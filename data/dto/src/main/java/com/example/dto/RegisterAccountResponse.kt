package com.example.dto

import com.example.model.account.AccountToken
import kotlinx.serialization.Serializable

@Serializable
data class RegisterAccountResponse(
    val accountId: String
)

fun RegisterAccountResponse.toDomain() = AccountToken(
    accountId = accountId
)


