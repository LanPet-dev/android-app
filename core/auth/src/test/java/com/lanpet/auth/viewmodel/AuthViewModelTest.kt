package com.lanpet.auth.viewmodel

import com.example.model.AuthState
import com.example.model.AuthorityType
import com.example.model.SocialAuthToken
import com.example.model.SocialAuthType
import com.example.model.account.Account
import com.example.model.account.AccountToken
import com.example.usecase.GetAccountInformationUseCase
import com.example.usecase.GetCognitoSocialAuthTokenUseCase
import com.example.usecase.RegisterAccountUseCase
import com.lanpet.core.auth.viewmodel.AuthViewModel
import com.lanpet.core.manager.AuthStateHolder
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

class AuthViewModelTest {
    private lateinit var viewModel: AuthViewModel

    private lateinit var getCognitoSocialAuthTokenUseCase: GetCognitoSocialAuthTokenUseCase
    private lateinit var registerAccountUseCase: RegisterAccountUseCase
    private lateinit var getAccountInformationUseCase: GetAccountInformationUseCase
    private lateinit var authStateHolder: AuthStateHolder

    @BeforeEach
    fun beforeEach() {
        getCognitoSocialAuthTokenUseCase = mockk<GetCognitoSocialAuthTokenUseCase>()
        registerAccountUseCase = mockk()
        getAccountInformationUseCase = mockk()
        authStateHolder = AuthStateHolder()

        viewModel = AuthViewModel(
            getCognitoSocialAuthTokenUseCase,
            registerAccountUseCase,
            getAccountInformationUseCase,
            authStateHolder
        )
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
        authStateHolder = AuthStateHolder()
    }

    @Test
    fun `logout 시, 인증상태는 AuthState_Initial 을 반환한다`() {
        // Given
        // When
        viewModel.logout()

        // Then
        assert(viewModel.authState.value is AuthState.Initial)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Cognito 토큰 받기 실패 시, 인증상태는 AuthState_Fail 을 반환한다`() = runTest() {
        // Given
        val authCode = "AUTH-CODE"
        coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns flow {
            throw Exception("Failed to fetch cognito token")
        }

        // When
        viewModel.handleAuthCode(authCode)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) {
            getCognitoSocialAuthTokenUseCase(authCode)
            assert(viewModel.authState.value is AuthState.Fail)
        }
    }

    @Nested
    inner class `Cognito 토큰 받기 성공 시` {
        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `유저가 존재하는 경우, 인증상태는 AuthState_Success 을 반환한다`(): Unit = runTest() {
            // Given
            val authCode = "AUTH-CODE"
            coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns flow {
                emit(SocialAuthToken(SocialAuthType.GOOGLE, "accessToken", "refreshToken"))
            }

            coEvery { getAccountInformationUseCase() } returns flow {
                emit(
                    Account(
                        "accountId",
                        "authId",
                        authority = AuthorityType.USER,
                        exitDate = null,
                        exitReason = null
                    )
                )
            }

            // When
            viewModel.handleAuthCode(authCode)
            advanceUntilIdle()

            // Then
            coVerify(exactly = 1) {
                getCognitoSocialAuthTokenUseCase(authCode)
                getAccountInformationUseCase()
            }

            assert(viewModel.authState.value is AuthState.Success)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Nested
        inner class `유저 정보가 존재하지 않는 경우` {
            @Test
            fun `유저정보를 등록하고, 등록이 실패한 경우 인증상태는 AuthState_Fail 을 반환한다`() = runTest {
                // Given
                val authCode = "AUTH-CODE"
                coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns flow {
                    emit(SocialAuthToken(SocialAuthType.GOOGLE, "accessToken", "refreshToken"))
                }

                coEvery { getAccountInformationUseCase() } returns flow {
                    throw Exception("Failed to get account information")
                }

                coEvery { registerAccountUseCase() } returns flow {
                    throw Exception("Failed to register account")
                }

                // When
                viewModel.handleAuthCode(authCode)
                advanceUntilIdle()

                // Then
                coVerify(exactly = 1) {
                    getCognitoSocialAuthTokenUseCase(authCode)
                    getAccountInformationUseCase()
                    registerAccountUseCase()
                }

                assert(viewModel.authState.value is AuthState.Fail)
            }


            @Test
            fun `유저정보를 등록하고, 등록이 성공한 경우 인증상태는 AuthState_Success 을 반환한다`() = runTest {
                // Given
                val authCode = "AUTH-CODE"
                coEvery { getCognitoSocialAuthTokenUseCase(authCode) } returns flow {
                    emit(SocialAuthToken(SocialAuthType.GOOGLE, "accessToken", "refreshToken"))
                }

                coEvery { getAccountInformationUseCase() } returnsMany listOf(
                    flow {
                        throw Exception("Failed to get account information")
                    }, flow {
                        emit(
                            Account(
                                "accountId",
                                "authId",
                                authority = AuthorityType.USER,
                                exitDate = null,
                                exitReason = null
                            )
                        )
                    })

                coEvery { registerAccountUseCase() } returns flow {
                    emit(AccountToken("accountId"))
                }

                // When
                viewModel.handleAuthCode(authCode)
                advanceUntilIdle()


                // Then
                coVerify(exactly = 2) {
                    getAccountInformationUseCase()
                }

                coVerify(exactly = 1) {
                    getCognitoSocialAuthTokenUseCase(authCode)
                    registerAccountUseCase()
                }

                assert(viewModel.authState.value is AuthState.Success)
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