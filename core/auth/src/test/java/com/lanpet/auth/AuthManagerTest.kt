package com.lanpet.auth

import app.cash.turbine.test
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.manager.AuthStateHolder
import com.lanpet.domain.model.AuthState
import com.lanpet.domain.model.AuthorityType
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.model.SocialAuthType
import com.lanpet.domain.model.UserProfile
import com.lanpet.domain.model.account.Account
import com.lanpet.domain.model.account.AccountToken
import com.lanpet.domain.model.profile.UserProfileDetail
import com.lanpet.domain.usecase.account.GetAccountInformationUseCase
import com.lanpet.domain.usecase.account.RegisterAccountUseCase
import com.lanpet.domain.usecase.cognitoauth.GetCognitoSocialAuthTokenUseCase
import com.lanpet.domain.usecase.profile.GetAllProfileUseCase
import com.lanpet.domain.usecase.profile.GetDefaultProfileUseCase
import com.lanpet.domain.usecase.profile.GetProfileDetailUseCase
import com.lanpet.domain.usecase.profile.SetDefaultProfileUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf

// TODO: fix. AuthDatabase, authhandle
class AuthManagerTest {
    private lateinit var authManager: AuthManager

    private lateinit var getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase
    private lateinit var registerAccountUseCase: RegisterAccountUseCase
    private lateinit var getProfileDetailUseCase: GetProfileDetailUseCase
    private lateinit var getAccountInformationUseCase: GetAccountInformationUseCase
    private lateinit var getAllProfileUseCase: GetAllProfileUseCase
    private lateinit var getDefaultProfileUseCase: GetDefaultProfileUseCase
    private lateinit var setDefaultProfileUseCase: SetDefaultProfileUseCase
    private lateinit var authStateHolder: AuthStateHolder

    @BeforeEach
    fun beforeEach() {
        getCognitoSocialAuthTokenUseCase = mockk<GetCognitoSocialAuthTokenUseCase>()
        registerAccountUseCase = mockk()
        getAccountInformationUseCase = mockk()
        getAllProfileUseCase = mockk()
        getProfileDetailUseCase = mockk()
        getDefaultProfileUseCase = mockk()
        setDefaultProfileUseCase = mockk()
        authStateHolder = AuthStateHolder()

        authManager =
            AuthManager(
                getCognitoSocialAuthTokenUseCase,
                registerAccountUseCase,
                getAccountInformationUseCase,
                getAllProfileUseCase,
                getProfileDetailUseCase,
                getDefaultProfileUseCase,
                setDefaultProfileUseCase,
                authStateHolder,
            )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `logout 시, 인증상태는 AuthState_Logout 을 반환한다`() {
        // Given
        // When
        authManager.logout()

        // Then
        assert(authManager.authState.value is AuthState.Logout)
    }

    @Test
    fun `Cognito 토큰 받기 실패 시, 인증상태는 AuthState_Fail 을 반환한다`() =
        runTest {
            // Given
            val authCode = "AUTH-CODE"
            coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns
                flow {
                    throw Exception("Failed to fetch cognito token")
                }

            authManager.authState.test {
                // When
                authManager.handleAuthCode(authCode)

                // Then
                // first AuthState value
                assertInstanceOf<AuthState.Initial>(awaitItem())

                // second AuthState value
                assertInstanceOf<AuthState.Fail>(awaitItem())

                coVerify(exactly = 1) {
                    getCognitoSocialAuthTokenUseCase(authCode)
                }
                coVerify(exactly = 0) {
                    getAccountInformationUseCase()
                    registerAccountUseCase()
                    getAccountInformationUseCase()
                    getAllProfileUseCase()
                }
            }
        }

    @Nested
    inner class `Cognito 토큰 받기 성공 시` {
        @Nested
        inner class `account 정보가 존재하지 않는 경우` {
            @Test
            fun `account 정보를 등록하고, 등록이 실패한 경우 인증상태는 AuthState_Fail 을 반환한다`() =
                runTest {
                    // Given
                    val authCode = "AUTH-CODE"
                    coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns
                        flow {
                            emit(
                                SocialAuthToken(
                                    SocialAuthType.GOOGLE,
                                    "accessToken",
                                    "refreshToken",
                                ),
                            )
                        }

                    coEvery { getAccountInformationUseCase() } returns
                        flow {
                            throw Exception("Failed to get account information")
                        }

                    coEvery { registerAccountUseCase() } returns
                        flow {
                            throw Exception("Failed to register account")
                        }

                    authManager.authState.test {
                        // Default AuthState value
                        assertInstanceOf<AuthState.Initial>(awaitItem())

                        // When
                        authManager.handleAuthCode(authCode)

                        // First AuthState value
                        assertInstanceOf<AuthState.Loading>(awaitItem())

                        // Second AuthState value
                        assertInstanceOf<AuthState.Fail>(awaitItem())

                        ensureAllEventsConsumed()
                        expectNoEvents()

                        // Then
                        coVerify(exactly = 1) {
                            getCognitoSocialAuthTokenUseCase(authCode)
                            getAccountInformationUseCase()
                            registerAccountUseCase()
                        }

                        assertInstanceOf<AuthState.Fail>(authManager.authState.value)
                    }
                }

            @Test
            fun `profile 정보를 가져오지 못한 경우, AuthState_Success 를 반환한다`() =
                runTest {
                    // Given
                    val authCode = "AUTH-CODE"
                    coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns
                        flow {
                            emit(
                                SocialAuthToken(
                                    SocialAuthType.GOOGLE,
                                    "accessToken",
                                    "refreshToken",
                                ),
                            )
                        }

                    coEvery { registerAccountUseCase() } returns
                        flow {
                            emit(AccountToken("accountId"))
                        }

                    coEvery { getAccountInformationUseCase() } returnsMany
                        listOf(
                            flow {
                                throw Exception("Failed to get account information")
                            },
                            flow {
                                emit(
                                    Account(
                                        "accountId",
                                        "authId",
                                        authority = AuthorityType.USER,
                                        exitDate = null,
                                        exitReason = null,
                                    ),
                                )
                            },
                        )

                    coEvery {
                        getAllProfileUseCase()
                    } returns
                        flow {
                            throw Exception("Failed to get profile")
                        }

                    authManager.authState.test {
                        // Default AuthState value
                        assertInstanceOf<AuthState.Initial>(awaitItem())

                        // When
                        authManager.handleAuthCode(authCode)

                        // First AuthState value
                        assertInstanceOf<AuthState.Loading>(awaitItem())

                        // Second AuthState value
                        assertInstanceOf<AuthState.Success>(awaitItem())

                        ensureAllEventsConsumed()
                        expectNoEvents()

                        coVerify(exactly = 2) {
                            getAccountInformationUseCase()
                        }

                        coVerify(exactly = 1) {
                            getAllProfileUseCase()
                            getCognitoSocialAuthTokenUseCase(authCode)
                        }
                    }
                }
        }

        @Test
        fun `defaultProfile 정보를 가져오지 못한 경우, AuthState_Fail 을 반환한다`() =
            runTest {
                // Given
                val authCode = "AUTH-CODE"
                coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns
                    flow {
                        emit(
                            SocialAuthToken(
                                SocialAuthType.GOOGLE,
                                "accessToken",
                                "refreshToken",
                            ),
                        )
                    }

                coEvery { registerAccountUseCase() } returns
                    flow {
                        emit(AccountToken("accountId"))
                    }

                coEvery { getAccountInformationUseCase() } returnsMany
                    listOf(
                        flow {
                            throw Exception("Failed to get account information")
                        },
                        flow {
                            emit(
                                Account(
                                    "accountId",
                                    "authId",
                                    authority = AuthorityType.USER,
                                    exitDate = null,
                                    exitReason = null,
                                ),
                            )
                        },
                    )
                coEvery { getAllProfileUseCase() } returns
                    flow {
                        emit(
                            listOf(
                                UserProfile(
                                    "profileId",
                                    ProfileType.BUTLER,
                                    "nickName",
                                    "profileImageUri",
                                    "bio",
                                ),
                            ),
                        )
                    }

                coEvery { getDefaultProfileUseCase("accountId") } returns
                    flow {
                        throw Exception("Failed to get default profile")
                    }

                authManager.authState.test {
                    // Default AuthState value
                    assertInstanceOf<AuthState.Initial>(awaitItem())

                    // When
                    authManager.handleAuthCode(authCode)

                    // First AuthState value
                    assertInstanceOf<AuthState.Loading>(awaitItem())

                    // Third AuthState value
                    assertInstanceOf<AuthState.Fail>(awaitItem())

                    ensureAllEventsConsumed()
                    expectNoEvents()

                    coVerify(exactly = 2) {
                        getAccountInformationUseCase()
                    }

                    coVerify(exactly = 1) {
                        getAllProfileUseCase()
                        getCognitoSocialAuthTokenUseCase(authCode)
                        registerAccountUseCase()
                        getDefaultProfileUseCase("accountId")
                    }
                }
            }

        @Test
        fun `detailProfile 정보를 가져오지 못한경우, AuthState_Fail 을 반환한다`() {
            runTest {
                // Given
                val authCode = "AUTH-CODE"
                coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns
                    flow {
                        emit(
                            SocialAuthToken(
                                SocialAuthType.GOOGLE,
                                "accessToken",
                                "refreshToken",
                            ),
                        )
                    }

                coEvery { registerAccountUseCase() } returns
                    flow {
                        emit(AccountToken("accountId"))
                    }

                coEvery { getAccountInformationUseCase() } returnsMany
                    listOf(
                        flow {
                            throw Exception("Failed to get account information")
                        },
                        flow {
                            emit(
                                Account(
                                    "accountId",
                                    "authId",
                                    authority = AuthorityType.USER,
                                    exitDate = null,
                                    exitReason = null,
                                ),
                            )
                        },
                    )
                coEvery { getAllProfileUseCase() } returns
                    flow {
                        emit(
                            listOf(
                                UserProfile(
                                    "profileId",
                                    ProfileType.BUTLER,
                                    "nickName",
                                    "profileImageUri",
                                    "bio",
                                ),
                            ),
                        )
                    }

                coEvery { getDefaultProfileUseCase("accountId") } returns
                    flow {
                        emit("profileId")
                    }

                coEvery { getProfileDetailUseCase("profileId") } returns
                    flow {
                        throw Exception("Failed to get profile detail")
                    }

                authManager.authState.test {
                    // Default AuthState value
                    assertInstanceOf<AuthState.Initial>(awaitItem())

                    // When
                    authManager.handleAuthCode(authCode)

                    // First AuthState value
                    assertInstanceOf<AuthState.Loading>(awaitItem())

                    // Third AuthState value
                    assertInstanceOf<AuthState.Fail>(awaitItem())

                    ensureAllEventsConsumed()
                    expectNoEvents()

                    coVerify(exactly = 2) {
                        getAccountInformationUseCase()
                    }

                    coVerify(exactly = 1) {
                        getAllProfileUseCase()
                        getCognitoSocialAuthTokenUseCase(authCode)
                        registerAccountUseCase()
                        getDefaultProfileUseCase("accountId")
                        getProfileDetailUseCase("profileId")
                    }
                }
            }
        }

        @Test
        fun `handleNoAccount 성공 시, AuthState_Success 을 반환한다`() =
            runTest {
                // Given
                val authCode = "AUTH-CODE"
                coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns
                    flow {
                        emit(
                            SocialAuthToken(
                                SocialAuthType.GOOGLE,
                                "accessToken",
                                "refreshToken",
                            ),
                        )
                    }

                coEvery { registerAccountUseCase() } returns
                    flow {
                        emit(AccountToken("accountId"))
                    }

                coEvery { getAccountInformationUseCase() } returnsMany
                    listOf(
                        flow {
                            throw Exception("Failed to get account information")
                        },
                        flow {
                            emit(
                                Account(
                                    "accountId",
                                    "authId",
                                    authority = AuthorityType.USER,
                                    exitDate = null,
                                    exitReason = null,
                                ),
                            )
                        },
                    )
                coEvery { getAllProfileUseCase() } returns
                    flow {
                        emit(
                            listOf(
                                UserProfile(
                                    "profileId",
                                    ProfileType.BUTLER,
                                    "nickName",
                                    "profileImageUri",
                                    "bio",
                                ),
                            ),
                        )
                    }

                coEvery { getDefaultProfileUseCase("accountId") } returns
                    flow {
                        emit("profileId")
                    }

                coEvery { getProfileDetailUseCase("profileId") } returns
                    flow {
                        emit(
                            UserProfileDetail(
                                "profileId",
                                ProfileType.BUTLER,
                                "nickName",
                                "profileImageUri",
                                "bio",
                            ),
                        )
                    }

                authManager.authState.test {
                    // Default AuthState value
                    assertInstanceOf<AuthState.Initial>(awaitItem())

                    // When
                    authManager.handleAuthCode(authCode)

                    // First AuthState value
                    assertInstanceOf<AuthState.Loading>(awaitItem())

                    // Third AuthState value
                    assertInstanceOf<AuthState.Success>(awaitItem())

                    ensureAllEventsConsumed()
                    expectNoEvents()

                    coVerify(exactly = 2) {
                        getAccountInformationUseCase()
                    }

                    coVerify(exactly = 1) {
                        getAllProfileUseCase()
                        getCognitoSocialAuthTokenUseCase(authCode)
                        registerAccountUseCase()
                        getDefaultProfileUseCase("accountId")
                        getProfileDetailUseCase("profileId")
                    }
                }
            }

        @Nested
        inner class `UpdateUserProfile test` {
            @Test
            @Disabled(value = "UserProfile, UserProfileDetail 내부에서 대표프로필을 구분하는 필드가 사라져서 해당 로직은 변경되어야 합니다. 이로인하여 테스트를 Skip 합니다.")
            fun `AuthState 가 Success 인 경우, 새로운 profile 데이터를 받은 경우, AuthState 의 상태는 Success 이고, profile 은 새로운 profile로 업데이트 된다`() =
                runTest {
                    val oldProfiles =
                        listOf(
                            UserProfile(
                                "oldProfileId",
                                ProfileType.BUTLER,
                                "nickName",
                                "bio",
                                "profileImageUri",
                            ),
                        )

                    coEvery {
                        getAllProfileUseCase()
                    } returns
                        flow {
                            emit(
                                listOf(
                                    UserProfile(
                                        "newProfileId",
                                        ProfileType.BUTLER,
                                        "nickName",
                                        "bio",
                                        "profileImageUri",
                                    ),
                                ),
                            )
                        }

                    // 초기 state 설정
                    authStateHolder.updateState(
                        AuthState.Success(
                            SocialAuthToken(
                                SocialAuthType.GOOGLE,
                                "accessToken",
                                "refreshToken",
                            ),
                            Account(
                                "accountId",
                                "authId",
                                authority = AuthorityType.USER,
                                exitDate = null,
                                exitReason = null,
                            ),
                            oldProfiles,
                        ),
                    )

                    authManager.authState.test {
                        // 초기상태 검증
                        assert(authManager.authState.value is AuthState.Success)
                        assert(
                            (authManager.authState.value as AuthState.Success).profile.first().id ==
                                "oldProfileId",
                        )
                        // Default AuthState value
                        assertInstanceOf<AuthState.Success>(awaitItem())

                        // When
                        authManager.updateUserProfile("profileId")

                        // First AuthState value
                        assertInstanceOf<AuthState.Success>(awaitItem())

                        ensureAllEventsConsumed()
                        expectNoEvents()

                        coVerify(exactly = 1) {
                            getAllProfileUseCase()
                        }

                        assert(authManager.authState.value is AuthState.Success)
                        assert(
                            (authManager.authState.value as AuthState.Success).profile.first().id ==
                                "newProfileId",
                        )
                    }
                }

            @Test
            fun `AuthState 가 Success 인 경우, 새로운 profile 데이터를 받기 실패한 경우, AuthState 의 상태는 Fail 을 반환한다`() =
                runTest {
                    coEvery { getAllProfileUseCase() } returns
                        flow {
                            throw Exception("Failed to get profile")
                        }

                    // 초기 state 설정
                    authStateHolder.updateState(
                        AuthState.Success(
                            SocialAuthToken(
                                SocialAuthType.GOOGLE,
                                "accessToken",
                                "refreshToken",
                            ),
                            Account(
                                "accountId",
                                "authId",
                                authority = AuthorityType.USER,
                                exitDate = null,
                                exitReason = null,
                            ),
                            listOf(
                                UserProfile(
                                    "profileId",
                                    ProfileType.BUTLER,
                                    "nickName",
                                    "bio",
                                    "profileImageUri",
                                ),
                            ),
                        ),
                    )

                    authManager.authState.test {
                        // 초기상태 검증
                        assert(authManager.authState.value is AuthState.Success)
                        assert(
                            (authManager.authState.value as AuthState.Success).profile.first().id ==
                                "profileId",
                        )

                        // Default AuthState value
                        assertInstanceOf<AuthState.Success>(awaitItem())

                        // When
                        authManager.updateUserProfile("profileId")

                        // First AuthState value
                        assertInstanceOf<AuthState.Fail>(awaitItem())

                        ensureAllEventsConsumed()
                        expectNoEvents()

                        coVerify(exactly = 1) {
                            getAllProfileUseCase()
                        }

                        assert(authManager.authState.value is AuthState.Fail)
                    }
                }
        }
    }

    companion object {
        @OptIn(ExperimentalCoroutinesApi::class)
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            Dispatchers.setMain(UnconfinedTestDispatcher())
        }
    }
}
