package com.lanpet.data.repository

import com.lanpet.data.dto.toDomain
import com.lanpet.data.service.AccountApiService
import com.lanpet.domain.model.account.Account
import com.lanpet.domain.model.account.AccountToken
import com.lanpet.domain.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AccountRepositoryImpl
    @Inject
    constructor(
        private val accountService: AccountApiService,
    ) : AccountRepository {
        override suspend fun registerAccount(): Flow<AccountToken> =
            flow {
                val response = accountService.registerAccount()
                emit(response.toDomain())
            }.flowOn(Dispatchers.IO)

        override suspend fun getAccount(): Flow<Account> =
            flow<Account> {
                val response = accountService.getAccount()
                emit(response.toDomain())
            }.flowOn(Dispatchers.IO)

        override suspend fun leaveMember(): Flow<Boolean> = throw NotImplementedError("Not yet implemented")
    }
