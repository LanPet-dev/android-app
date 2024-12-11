package com.example.usecase

import com.example.model.account.AccountToken
import com.example.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Flow<AccountToken> {
        return accountRepository.registerAccount()
    }
}