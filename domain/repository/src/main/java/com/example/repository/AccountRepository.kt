package com.example.repository

import com.example.model.account.Account
import com.example.model.account.AccountToken

interface AccountRepository {
    suspend fun registerAccount(): Result<AccountToken>

    suspend fun getAccount(): Result<Account>
}