package com.lanpet

import com.example.dto.toDomain
import com.example.model.account.Account
import com.example.model.account.AccountToken
import com.example.repository.AccountRepository
import com.lanpet.service.AccountApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountApiService
) : AccountRepository {
    override suspend fun registerAccount(): Flow<AccountToken> {
        return flow {
            val response = accountService.registerAccount()
            emit(response.toDomain())
        }
    }

    override suspend fun getAccount(): Flow<Account> {
        return flow<Account> {
            val response = accountService.getAccount()
            emit(response.toDomain())
        }
    }
}