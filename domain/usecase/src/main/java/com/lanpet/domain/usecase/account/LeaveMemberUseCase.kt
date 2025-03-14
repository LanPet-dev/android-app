package com.lanpet.domain.usecase.account

import com.lanpet.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LeaveMemberUseCase
    @Inject
    constructor(
        private val accountRepository: AccountRepository,
    ) {
        suspend operator fun invoke(): Flow<Boolean> = accountRepository.leaveMember()
    }
