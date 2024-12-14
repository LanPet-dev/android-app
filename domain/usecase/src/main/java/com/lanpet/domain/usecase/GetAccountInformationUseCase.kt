package com.lanpet.domain.usecase

import com.lanpet.domain.model.account.Account
import com.lanpet.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountInformationUseCase
    @Inject
    constructor(
        private val accountRepository: AccountRepository,
    ) {
        suspend operator fun invoke(): Flow<Account> = accountRepository.getAccount()
    }
