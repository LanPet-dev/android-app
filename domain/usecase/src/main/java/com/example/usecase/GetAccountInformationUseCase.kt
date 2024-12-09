package com.example.usecase

import com.example.model.account.Account
import com.example.repository.AccountRepository
import javax.inject.Inject

class GetAccountInformationUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<Account> {
        return accountRepository.getAccount()
    }
}