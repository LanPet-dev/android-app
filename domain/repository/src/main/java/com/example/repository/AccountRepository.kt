package com.example.repository

import com.example.model.account.Account
import com.example.model.account.AccountToken
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun registerAccount(): Flow<AccountToken>

    suspend fun getAccount(): Flow<Account>
}