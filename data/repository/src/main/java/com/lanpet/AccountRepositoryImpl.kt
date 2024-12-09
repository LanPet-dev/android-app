package com.lanpet

import com.example.dto.toDomain
import com.example.model.account.Account
import com.example.model.account.AccountToken
import com.example.repository.AccountRepository
import com.lanpet.service.AccountApiService
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountApiService
) : AccountRepository {
    override suspend fun registerAccount(): Result<AccountToken> {
        return try {
            val response = accountService.registerAccount()
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAccount(): Result<Account> {
        return try {
            val response = accountService.getAccount()
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}