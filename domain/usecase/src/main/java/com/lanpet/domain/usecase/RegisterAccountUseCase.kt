package com.lanpet.domain.usecase

import com.lanpet.domain.model.account.AccountToken
import com.lanpet.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Flow<AccountToken> {
        return accountRepository.registerAccount()
    }
}