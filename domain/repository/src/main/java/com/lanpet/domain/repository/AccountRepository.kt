package com.lanpet.domain.repository

import com.lanpet.domain.model.account.Account
import com.lanpet.domain.model.account.AccountToken
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun registerAccount(): Flow<AccountToken>

    suspend fun getAccount(): Flow<Account>
}
