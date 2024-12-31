package com.lanpet.core.common.exception

import com.lanpet.domain.model.account.Account

sealed class AuthException : Exception() {
    class NoAccountException(
        override val message: String? = null,
    ) : AuthException()

    data class NoProfileException(
        override val message: String? = null,
        val account: Account,
    ) : AuthException()

    data class NoDefaultProfileException(
        override val message: String? = null,
        val accountId: String,
    ) : AuthException()

    data class NoProfileDetailException(
        override val message: String? = null,
    ) : AuthException()
}