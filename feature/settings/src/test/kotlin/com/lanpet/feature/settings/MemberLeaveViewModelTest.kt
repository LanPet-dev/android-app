package com.lanpet.feature.settings

import app.cash.turbine.test
import com.lanpet.domain.usecase.account.LeaveMemberUseCase
import com.lanpet.feature.settings.viewmodel.MemberLeaveState
import com.lanpet.feature.settings.viewmodel.MemberLeaveViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertInstanceOf

class MemberLeaveViewModelTest {
    private lateinit var viewModel: MemberLeaveViewModel
    private lateinit var leaveMemberUseCase: LeaveMemberUseCase

    @BeforeEach
    fun setUp() {
        leaveMemberUseCase = mockk()
        viewModel = MemberLeaveViewModel(leaveMemberUseCase)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class `LeaveMember test` {
        @Test
        fun `LeaveMemberUseCase 가 true 를 반환하는경우, LeaveState 는 MemberLeaveState_Success 를 반환한다`() =
            runTest {
                // given
                coEvery { leaveMemberUseCase() } returns flowOf(true)

                viewModel.leaveState.test {
                    // when
                    viewModel.leaveMember()

                    // then
                    assertInstanceOf<MemberLeaveState.Loading>(awaitItem())
                    assertInstanceOf<MemberLeaveState.Success>(awaitItem())

                    coVerify(exactly = 1) {
                        leaveMemberUseCase()
                    }
                }
            }

        @Test
        fun `LeaveMemberUseCase 가 false 를 반환하는경우, LeaveState 는 MemberLeaveState_Error 을 반환한다`() =
            runTest {
                // given
                coEvery { leaveMemberUseCase() } returns flowOf(false)

                viewModel.leaveState.test {
                    // when
                    viewModel.leaveMember()

                    // then
                    assertInstanceOf<MemberLeaveState.Loading>(awaitItem())
                    assertInstanceOf<MemberLeaveState.Error>(awaitItem())

                    coVerify(exactly = 1) {
                        leaveMemberUseCase()
                    }
                }
            }

        @Test
        fun `LeaveMemberUseCase 가 Exception 를 반환하는경우, LeaveState 는 MemberLeaveState_Error 을 반환한다`() =
            runTest {
                // given
                coEvery { leaveMemberUseCase() } throws Exception()

                viewModel.leaveState.test {
                    // when
                    viewModel.leaveMember()

                    // then
                    assertInstanceOf<MemberLeaveState.Loading>(awaitItem())
                    assertInstanceOf<MemberLeaveState.Error>(awaitItem())

                    coVerify(exactly = 1) {
                        leaveMemberUseCase()
                    }
                }
            }
    }

    companion object {
        @OptIn(ExperimentalCoroutinesApi::class)
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            Dispatchers.setMain(StandardTestDispatcher())
        }
    }
}
