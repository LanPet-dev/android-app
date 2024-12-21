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
import com.lanpet.domain.usecase.account.GetAccountInformationUseCase
import com.lanpet.domain.usecase.account.RegisterAccountUseCase
import com.lanpet.domain.usecase.cognitoauth.GetCognitoSocialAuthTokenUseCase
import com.lanpet.domain.usecase.profile.GetAllProfileUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf

class AuthManagerTest {
    private lateinit var authManager: AuthManager

    private lateinit var getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase
    private lateinit var registerAccountUseCase: RegisterAccountUseCase
    private lateinit var getAccountInformationUseCase: GetAccountInformationUseCase
    private lateinit var getAllProfileUseCase: GetAllProfileUseCase
    private lateinit var authStateHolder: AuthStateHolder

    @BeforeEach
    fun beforeEach() {
        getCognitoSocialAuthTokenUseCase = mockk<GetCognitoSocialAuthTokenUseCase>()
        registerAccountUseCase = mockk()
        getAccountInformationUseCase = mockk()
        getAllProfileUseCase = mockk()
        authStateHolder = AuthStateHolder()

        authManager =
            AuthManager(
                getCognitoSocialAuthTokenUseCase,
                registerAccountUseCase,
                getAccountInformationUseCase,
                getAllProfileUseCase,
                authStateHolder,
            )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `logout 시, 인증상태는 AuthState_Initial 을 반환한다`() {
        // Given
        // When
        authManager.logout()

        // Then
        assert(authManager.authState.value is AuthState.Initial)
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
        @Test
        fun `유저가 존재하는 경우, 인증상태는 AuthState_Success 을 반환한다`(): Unit =
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
                        emit(
                            Account(
                                "accountId",
                                "authId",
                                authority = AuthorityType.USER,
                                exitDate = null,
                                exitReason = null,
                            ),
                        )
                    }

                coEvery { getAllProfileUseCase() } returns
                    flow {
                        emit(
                            listOf(
                                UserProfile(
                                    "profileId",
                                    ProfileType.BUTLER,
                                    "nickName",
                                    "bio",
                                    "profileImageUri",
                                ),
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

                    // Second AuthState value
                    assertInstanceOf<AuthState.Success>(awaitItem())

                    // Third AuthState value
                    assertInstanceOf<AuthState.Success>(awaitItem())

                    ensureAllEventsConsumed()
                    expectNoEvents()

                    coVerify(exactly = 1) {
                        getCognitoSocialAuthTokenUseCase(authCode)
                        getAccountInformationUseCase()
                        getAllProfileUseCase()
                    }
                    coVerify(exactly = 0) {
                        registerAccountUseCase()
                    }
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Nested
    inner class `유저 정보가 존재하지 않는 경우` {
        @Test
        fun `유저정보를 등록하고, 등록이 실패한 경우 인증상태는 AuthState_Fail 을 반환한다`() =
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

                // When
                authManager.handleAuthCode(authCode)
                advanceUntilIdle()

                // Then
                coVerify(exactly = 1) {
                    getCognitoSocialAuthTokenUseCase(authCode)
                    getAccountInformationUseCase()
                    registerAccountUseCase()
                }

                coVerify(exactly = 0) {
                    getAllProfileUseCase()
                }

                assertInstanceOf<AuthState.Fail>(authManager.authState.value)
            }

        @Test
        fun `유저정보를 등록하고, 등록이 성공한 경우 인증상태는 AuthState_Success 을 반환한다`() =
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

                coEvery { registerAccountUseCase() } returns
                    flow {
                        emit(AccountToken("accountId"))
                    }

                coEvery { getAllProfileUseCase() } returns
                    flow {
                        emit(
                            listOf(
                                UserProfile(
                                    "profileId",
                                    ProfileType.BUTLER,
                                    "nickName",
                                    "bio",
                                    "profileImageUri",
                                ),
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

                    // Second AuthState value
                    assertInstanceOf<AuthState.Success>(awaitItem())

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
