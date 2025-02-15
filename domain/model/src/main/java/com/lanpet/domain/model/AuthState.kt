package com.lanpet.domain.model

import com.lanpet.domain.model.account.Account
import com.lanpet.domain.model.profile.UserProfileDetail

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
        val profile: Set<UserProfile> = emptySet(),
        val profileDetail: UserProfileDetail? = null,
        val defaultProfile: UserProfile? = null,
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
        // 다른 상태와 달리, default 값이 false 입니다.
        override val navigationHandleFlag: Boolean = false,
    ) : AuthState()
}
