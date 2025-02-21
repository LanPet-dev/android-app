package com.lanpet.core.auth

import androidx.annotation.VisibleForTesting
import com.lanpet.core.common.exception.AuthException
import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.model.UserProfile
import com.lanpet.domain.model.account.Account
import com.lanpet.domain.model.profile.UserProfileDetail
import com.lanpet.domain.usecase.account.GetAccountInformationUseCase
import com.lanpet.domain.usecase.account.RegisterAccountUseCase
import com.lanpet.domain.usecase.cognitoauth.GetCognitoSocialAuthTokenUseCase
import com.lanpet.domain.usecase.profile.GetAllProfileUseCase
import com.lanpet.domain.usecase.profile.GetDefaultProfileUseCase
import com.lanpet.domain.usecase.profile.GetProfileDetailUseCase
import com.lanpet.domain.usecase.profile.SetDefaultProfileUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
open class AuthManager

/**
     * AuthManager 의 생성자에서 각 UseCase 들이 null 을 초기값으로 가지는 이유는, AuthManager 이 CompositionLocalComponent 에 등록되는것과 관련이 있습니다.
     * 특히 View 영역에서 Preview 를 사용할 때, 해당 Preview 에서 AuthManager 을 포함하는 View 영역을 포함하고 있다면, AuthManager 가 주입되지 않은 경우 Preview 가 제대로 실행되지 않습니다.
     * 이를 해결하기 위해 AuthManager 의 생성자에서 각 UseCase 들을 null 로 초기화하고,
     * Compose Preview 에서는 AuthManager 을 Fake 로 생성하여 사용하도록 합니다.
     * 그러나 preview 가 아닌 실제 runtime 에서는 반드시 null 이 아닌 UseCase 들이 주입되어야 하고, 실제로 그렇게 동작하고 있습니다.
     * 따라서 아래 생성자의 수정의 경우 반드시 주의해야 합니다.
     */
    @Inject
    constructor(
        private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase? = null,
        private val registerAccountUseCase: RegisterAccountUseCase? = null,
        private val getAccountInformationUseCase: GetAccountInformationUseCase? = null,
        private val getAllProfileUseCase: GetAllProfileUseCase? = null,
        private val getProfileDetailUseCase: GetProfileDetailUseCase? = null,
        private val getDefaultProfileUseCase: GetDefaultProfileUseCase? = null,
        private val setDefaultProfileUseCase: SetDefaultProfileUseCase? = null,
        private val authStateHolder: AuthStateHolder,
    ) {
        val authState = authStateHolder.authState

        /**
         * 대표 프로필
         */
        val defaultUserProfile = authStateHolder.defaultProfile

        /**
         * 유저의 프로필 목록
         */
        val userProfiles = authStateHolder.userProfiles

        @OptIn(FlowPreview::class)
        fun handleAuthCode(code: String) {
            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    val socialAuthToken =
                        getCognitoSocialAuthTokenUseCase!!(code).timeout(5.seconds).first()
                    authStateHolder.updateState(
                        AuthState.Loading(socialAuthToken = socialAuthToken),
                    )
                    handleAuthentication(socialAuthToken)
                }.onFailure {
                    Timber.e(it)
                    authStateHolder.updateState(
                        AuthState.Fail(),
                    )
                }
            }
        }

        @VisibleForTesting
        suspend fun handleAuthentication(socialAuthToken: SocialAuthToken) {
            try {
                val account = getAccount()
                val profiles = getProfiles(account)
                val defaultProfile = getDefaultProfile(account.accountId, profiles)
                val detail = getProfileDetail(defaultProfile.id)

                authStateHolder.updateState(
                    AuthState.Success(
                        socialAuthToken = socialAuthToken,
                        account = account,
                        profile = profiles.toHashSet(),
                        defaultProfile = defaultProfile,
                        profileDetail = detail,
                        navigationHandleFlag = true,
                    ),
                )
            } catch (e: AuthException.NoAccountException) {
                handleNoAccount(socialAuthToken)
            } catch (e: AuthException.NoProfileException) {
                handleNoProfile(socialAuthToken, e.account)
            } catch (e: AuthException.NoDefaultProfileException) {
                handleNoProfileDetailException()
            } catch (e: AuthException.NoProfileDetailException) {
                handleNoDefaultProfileException()
            } catch (e: Exception) {
                // 그밖의 예외
                Timber.e(e)
                authStateHolder.updateState(
                    AuthState.Fail(),
                )
            }
        }

        @VisibleForTesting
        fun handleNoDefaultProfileException() {
            authStateHolder.updateState(
                AuthState.Fail(),
            )
        }

        @VisibleForTesting
        fun handleNoProfileDetailException() {
            authStateHolder.updateState(
                AuthState.Fail(),
            )
        }

        @VisibleForTesting
        fun handleNoProfile(
            socialAuthToken: SocialAuthToken,
            account: Account,
        ) {
            authStateHolder.updateState(
                AuthState.Success(
                    socialAuthToken = socialAuthToken,
                    account = account,
                    navigationHandleFlag = true,
                    profile = emptySet(),
                ),
            )
        }

        @OptIn(FlowPreview::class)
        @VisibleForTesting
        suspend fun handleNoAccount(socialAuthToken: SocialAuthToken) {
            try {
                val accountToken = registerAccountUseCase!!().timeout(5.seconds).first()
                val account = getAccountInformationUseCase!!().timeout(5.seconds).first()
                val profile = getProfiles(account)
                val defaultProfile = getDefaultProfile(account.accountId, profile)
                val detail = getProfileDetail(defaultProfile.id)

                authStateHolder.updateState(
                    AuthState.Success(
                        socialAuthToken = socialAuthToken,
                        account = account,
                        profile = profile.toHashSet(),
                        defaultProfile = defaultProfile,
                        profileDetail = detail,
                        navigationHandleFlag = true,
                    ),
                )
            } catch (e: AuthException.NoProfileException) {
                handleNoProfile(socialAuthToken, e.account)
            } catch (e: Exception) {
                Timber.e(e)
                authStateHolder.updateState(
                    AuthState.Fail(),
                )
            }
        }

        @OptIn(FlowPreview::class)
        @VisibleForTesting
        suspend fun getAccount(): Account =
            try {
                getAccountInformationUseCase!!().timeout(5.seconds).first()
            } catch (e: Exception) {
                throw AuthException.NoAccountException()
            }

        @OptIn(FlowPreview::class)
        @VisibleForTesting
        suspend fun getDefaultProfile(
            accountId: String,
            profiles: List<UserProfile>,
        ): UserProfile {
            try {
                var defaultProfileId =
                    getDefaultProfileUseCase!!(accountId).timeout(5.seconds).firstOrNull()
                if (defaultProfileId.isNullOrEmpty()) {
                    setDefaultProfileUseCase!!(accountId, profiles.first().id)
                        .timeout(5.seconds)
                        .first()
                    defaultProfileId = profiles.first().id
                }

                val defaultProfile =
                    profiles.firstOrNull { it.id == defaultProfileId } ?: profiles.first()
                return defaultProfile
            } catch (e: Exception) {
                throw AuthException.NoDefaultProfileException(accountId = accountId)
            }
        }

        @OptIn(FlowPreview::class)
        @VisibleForTesting
        suspend fun getProfileDetail(defaultProfileId: String): UserProfileDetail =
            try {
                getProfileDetailUseCase!!(defaultProfileId).timeout(5.seconds).first()
            } catch (e: Exception) {
                throw AuthException.NoProfileDetailException()
            }

        @OptIn(FlowPreview::class)
        @VisibleForTesting
        suspend fun getProfiles(account: Account): List<UserProfile> =
            try {
                val res = getAllProfileUseCase!!().timeout(5.seconds).first()
                res.ifEmpty { throw AuthException.NoProfileException(account = account) }
            } catch (e: Exception) {
                throw AuthException.NoProfileException(
                    account = account,
                )
            }

        // TODO("Satoshi"): refactor
        @OptIn(FlowPreview::class)
        suspend fun getProfiles() {
            try {
                if (authState.value !is AuthState.Success) {
                    return
                }

                val account = (authState.value as AuthState.Success).account ?: return
                val socialAuthToken = (authState.value as AuthState.Success).socialAuthToken

                val profile = getAllProfileUseCase!!().timeout(5.seconds).first()

                var defaultProfileId =
                    getDefaultProfileUseCase!!(
                        account.accountId,
                    ).timeout(5.seconds).firstOrNull()

                if (defaultProfileId == null) {
                    setDefaultProfileUseCase!!(
                        account.accountId,
                        profile.first().id,
                    ).timeout(5.seconds).first()

                    defaultProfileId = profile.first().id
                }

                val detail =
                    getProfileDetailUseCase!!(defaultProfileId).timeout(5.seconds).first()

                val defaultProfile =
                    profile.firstOrNull { it.id == defaultProfileId } ?: throw Exception(
                        "Default profile not found",
                    )

                authStateHolder.updateState(
                    AuthState.Success(
                        socialAuthToken = socialAuthToken,
                        account = account,
                        profile = profile.toHashSet(),
                        defaultProfile = defaultProfile,
                        profileDetail = detail,
                        navigationHandleFlag = false,
                    ),
                )
            } catch (e: Exception) {
                Timber.e(e)
                throw e
            }
        }

        @OptIn(FlowPreview::class)
        suspend fun updateUserProfile(profileId: String) {
            try {
                if (authState.value !is AuthState.Success) {
                    throw IllegalStateException("AuthState is not Success")
                }

                val currentAuthState = authStateHolder.authState.value as AuthState.Success

                currentAuthState.account?.let {
                    val setDefaultProfileRes =
                        setDefaultProfileUseCase!!(
                            it.accountId,
                            profileId,
                        ).timeout(5.seconds).first()

                    if (!setDefaultProfileRes) {
                        throw AuthException.NoDefaultProfileException(
                            accountId = currentAuthState.account!!.accountId,
                            message = "Set default profile failed",
                        )
                    }
                } ?: throw AuthException.NoAccountException("Account is null")

                val res =
                    try {
                        getAllProfileUseCase!!().timeout(5.seconds).first()
                    } catch (e: Exception) {
                        throw AuthException.NoProfileException(
                            account = currentAuthState.account!!,
                        )
                    }

                val defaultProfile =
                    res.firstOrNull { it.id == profileId }
                        ?: throw AuthException.NoDefaultProfileException(
                            accountId = currentAuthState.account!!.accountId,
                            message = "Default profile not found",
                        )

                val detail =
                    try {
                        getProfileDetailUseCase!!(profileId).timeout(5.seconds).first()
                    } catch (e: Exception) {
                        throw AuthException.NoProfileDetailException()
                    }

                authStateHolder.updateState(
                    AuthState.Success(
                        socialAuthToken = currentAuthState.socialAuthToken,
                        account = currentAuthState.account,
                        profile = res.toHashSet(),
                        defaultProfile = defaultProfile,
                        profileDetail = detail,
                        navigationHandleFlag = false,
                    ),
                )
            } catch (e: Exception) {
                Timber.e(e)
                when (e) {
                    is AuthException -> throw e
                    else -> throw AuthException.UpdateProfileFailException(
                        message = e.message,
                    )
                }
            }
        }

        fun logout() {
            authStateHolder.updateState(
                AuthState.Logout(),
            )
        }
    }
