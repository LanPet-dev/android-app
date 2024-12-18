package com.lanpet.domain.model

import com.lanpet.domain.model.account.Account

// TODO("Satoshi"): Add authState that whether user has own profile or not
sealed class AuthState {
    /**
     * Navigation 핸들러에의해 처리가 되어야하는지 여부를 결정합니다.
     * true: Navigation 핸들러에의해 처리가 되어야합니다.
     * false: Navigation 핸들러에의해 처리가 되지 않아야합니다.
     */
    abstract val navigationHandleFlag: Boolean

    data class Success(
        val socialAuthToken: SocialAuthToken?,
        val account: Account?,
        val profile: List<UserProfile> = emptyList(),
        override val navigationHandleFlag: Boolean = true,
    ) : AuthState()

    data class Logout(
        override val navigationHandleFlag: Boolean = true,
    ) : AuthState()

    data class Fail(
        override val navigationHandleFlag: Boolean = true,
    ) : AuthState()

    data class Initial(
        override val navigationHandleFlag: Boolean = true,
    ) : AuthState()

    data class Loading(
        val socialAuthToken: SocialAuthToken?,
        override val navigationHandleFlag: Boolean = false,
    ) : AuthState()
}
