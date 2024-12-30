package com.lanpet.core.auth

import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
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
        private val getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase?=null,
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

        @OptIn(FlowPreview::class)
        fun handleAuthCode(code: String) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val socialAuthToken =
                        getCognitoSocialAuthTokenUseCase!!(code).timeout(5.seconds).first()
                    authStateHolder.updateState(
                        AuthState.Loading(socialAuthToken = socialAuthToken),
                    )

                    try {
                        val account = getAccountInformationUseCase!!().timeout(5.seconds).first()
                        authStateHolder.updateState(
                            AuthState.Success(
                                socialAuthToken = socialAuthToken,
                                account = account,
                                navigationHandleFlag = false,
                            ),
                        )
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
                            ),
                        )
                    } catch (e: Exception) {
                        val accountToken = registerAccountUseCase!!().timeout(5.seconds).first()
                        val account = getAccountInformationUseCase!!().timeout(5.seconds).first()
                        authStateHolder.updateState(
                            AuthState.Success(
                                socialAuthToken = socialAuthToken,
                                account = account,
                                navigationHandleFlag = false,
                            ),
                        )
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
                            ),
                        )
                    }
                } catch (e: Exception) {
                    authStateHolder.updateState(
                        AuthState.Fail(),
                    )
                }
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
