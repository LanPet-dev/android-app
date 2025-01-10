package com.lanpet.free

import com.lanpet.core.testing.rule.data.freeBoardCommentTestData
import com.lanpet.core.testing.rule.data.freeBoardPostDetailTestData
import com.lanpet.domain.usecase.freeboard.GetFreeBoardCommentListUseCase
import com.lanpet.domain.usecase.freeboard.GetFreeBoardDetailUseCase
import com.lanpet.free.viewmodel.FreeBoardDetailState
import com.lanpet.free.viewmodel.FreeBoardDetailViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class FreeBoardDetailViewModelTest {
    private lateinit var viewModel: FreeBoardDetailViewModel
    private lateinit var getFreeBoardCommentListUseCase: GetFreeBoardCommentListUseCase
    private lateinit var getFreeBoardDetailUseCase: GetFreeBoardDetailUseCase

    @BeforeEach
    fun setUp() {
        getFreeBoardDetailUseCase = mockk()
        getFreeBoardCommentListUseCase = mockk()

        viewModel =
            FreeBoardDetailViewModel(
                getFreeBoardDetailUseCase,
                getFreeBoardCommentListUseCase,
            )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Smoke test`() {
        // check initial uiState
        assert(viewModel.uiState.value == FreeBoardDetailState.Loading)
    }

    @Nested
    inner class `UI State Test` {
        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `CommentData fetch 성공,PostDetailData fetch 성공 시, uiState 은 FreeBoardDetailState_Success 를 반환한다`() =
            runTest {
                // given
                val postId = "1"
                coEvery { getFreeBoardDetailUseCase(postId) } returns
                    flow {
                        emit(
                            freeBoardPostDetailTestData,
                        )
                    }

                coEvery { getFreeBoardCommentListUseCase(postId) } returns
                    flow {
                        emit(
                            freeBoardCommentTestData,
                        )
                    }

                // 상태 변화를 관찰하기 위한 collector
                val states = mutableListOf<FreeBoardDetailState>()
                val job =
                    launch(UnconfinedTestDispatcher()) {
                        viewModel.uiState.collect { states.add(it) }
                    }

                // when
                viewModel.init(postId)
                advanceUntilIdle()

                // then
                assert(states.last() is FreeBoardDetailState.Success)
                job.cancel()
            }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `CommentData fetch 성공,PostDetailData fetch 실패 시, uiState 은 FreeBoardDetailState_Error 을 반환한다`() =
            runTest {
                // given
                val postId = "1"
                coEvery { getFreeBoardDetailUseCase(postId) } returns
                    flow {
                        throw Exception("Failed to fetch detail")
                    }

                coEvery { getFreeBoardCommentListUseCase(postId) } returns
                    flow {
                        emit(
                            freeBoardCommentTestData,
                        )
                    }

                // 상태 변화를 관찰하기 위한 collector
                val states = mutableListOf<FreeBoardDetailState>()
                val job =
                    launch(UnconfinedTestDispatcher()) {
                        viewModel.uiState.collect { states.add(it) }
                    }

                // when
                viewModel.init(postId)
                advanceUntilIdle()

                // then
                assert(states.last() is FreeBoardDetailState.Error)
                assert((states.last() as FreeBoardDetailState.Error).message == "Failed to fetch detail")
                job.cancel()
            }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `CommentData fetch 실패,PostDetailData fetch 성공 시, uiState 은 FreeBoardDetailState_Error 을 반환한다`() =
            runTest {
                // given
                val postId = "1"
                coEvery { getFreeBoardDetailUseCase(postId) } returns
                    flow {
                        emit(
                            freeBoardPostDetailTestData,
                        )
                    }

                coEvery { getFreeBoardCommentListUseCase(postId) } returns
                    flow {
                        throw Exception("Failed to fetch comments")
                    }

                // 상태 변화를 관찰하기 위한 collector
                val states = mutableListOf<FreeBoardDetailState>()
                val job =
                    launch(UnconfinedTestDispatcher()) {
                        viewModel.uiState.collect { states.add(it) }
                    }

                // when
                viewModel.init(postId)
                advanceUntilIdle()

                // then
                assert(states.last() is FreeBoardDetailState.Error)
                assert((states.last() as FreeBoardDetailState.Error).message == "Failed to fetch comments")
                job.cancel()
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
