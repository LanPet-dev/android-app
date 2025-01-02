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
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
open class AuthManager
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

        /**
         * 현재 유저의 프로필 상세 정보
         */
        val currentProfileDetail = authStateHolder.currentProfileDetail

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
                        profile = profiles,
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
        suspend fun handleNoDefaultProfileException() {
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
                    profile = emptyList(),
                ),
            )
        }

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
                        profile = profile,
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

        @VisibleForTesting
        suspend fun getAccount(): Account =
            try {
                getAccountInformationUseCase!!().timeout(5.seconds).first()
            } catch (e: Exception) {
                throw AuthException.NoAccountException()
            }

        @VisibleForTesting
        suspend fun getDefaultProfile(
            accountId: String,
            profiles: List<UserProfile>,
        ): UserProfile {
            try {
                var defaultProfileId = getDefaultProfileUseCase!!(accountId).timeout(5.seconds).first()
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

        @VisibleForTesting
        suspend fun getProfileDetail(defaultProfileId: String): UserProfileDetail =
            try {
                getProfileDetailUseCase!!(defaultProfileId).timeout(5.seconds).first()
            } catch (e: Exception) {
                throw AuthException.NoProfileDetailException()
            }

        @VisibleForTesting
        suspend fun getProfiles(account: Account): List<UserProfile> =
            try {
                val res = getAllProfileUseCase!!().timeout(5.seconds).first()
                if (res.isEmpty()) throw AuthException.NoProfileException(account = account) else res
            } catch (e: Exception) {
                throw AuthException.NoProfileException(
                    account = account,
                )
            }

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
                    ).timeout(5.seconds).first()

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
                        profile = profile,
                        defaultProfile = defaultProfile,
                        profileDetail = detail,
                        navigationHandleFlag = false,
                    ),
                )
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        @OptIn(FlowPreview::class)
        suspend fun updateUserProfile(profileId: String) {
            try {
                if (authState.value !is AuthState.Success) {
                    return
                }
                val currentAuthState = authStateHolder.authState.value as AuthState.Success

                val setDefaultProfileRes =
                    currentAuthState.account?.let {
                        setDefaultProfileUseCase!!(
                            it.accountId,
                            profileId,
                        ).timeout(5.seconds).first()
                    } ?: throw IllegalStateException("Account is null")

                if (!setDefaultProfileRes) {
                    throw IllegalStateException("Set default profile failed")
                }

                val res = getAllProfileUseCase!!().timeout(5.seconds).first()

                val defaultProfile =
                    res.firstOrNull { it.id == profileId }
                        ?: throw IllegalStateException("Default profile not found")

                val detail = getProfileDetailUseCase!!(profileId).timeout(5.seconds).first()

                authStateHolder.updateState(
                    AuthState.Success(
                        socialAuthToken = currentAuthState.socialAuthToken,
                        account = currentAuthState.account,
                        profile = res,
                        defaultProfile = defaultProfile,
                        profileDetail = detail,
                        navigationHandleFlag = false,
                    ),
                )
            } catch (e: Exception) {
                Timber.e(e)
                authStateHolder.updateState(
                    AuthState.Fail(),
                )
            }
        }

        fun logout() {
            authStateHolder.updateState(
                AuthState.Logout(),
            )
        }
    }
