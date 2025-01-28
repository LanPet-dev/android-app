package com.lanpet.free

import androidx.lifecycle.SavedStateHandle
import com.lanpet.core.testing.rule.data.freeBoardCommentTestData
import com.lanpet.core.testing.rule.data.freeBoardPostDetailTestData
import com.lanpet.domain.model.PaginationData
import com.lanpet.domain.model.PaginationInfo
import com.lanpet.domain.usecase.freeboard.CancelPostLikeUseCase
import com.lanpet.domain.usecase.freeboard.DoPostLikeUseCase
import com.lanpet.domain.usecase.freeboard.GetFreeBoardCommentListUseCase
import com.lanpet.domain.usecase.freeboard.GetFreeBoardDetailUseCase
import com.lanpet.domain.usecase.freeboard.WriteCommentUseCase
import com.lanpet.free.viewmodel.FreeBoardDetailState
import com.lanpet.free.viewmodel.FreeBoardDetailViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
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
    private lateinit var doPostLikeUseCase: DoPostLikeUseCase
    private lateinit var cancelPostLikeUseCase: CancelPostLikeUseCase
    private lateinit var writeCommentUseCase: WriteCommentUseCase

    @BeforeEach
    fun setUp() {
        getFreeBoardDetailUseCase = mockk()
        getFreeBoardCommentListUseCase = mockk()
        doPostLikeUseCase = mockk()
        cancelPostLikeUseCase = mockk()
        writeCommentUseCase = mockk()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Smoke test`() {
        runTest {
            // given
            val postId = "1"
            val profileId = "profileId"
            val nickname = "nickname"
            coEvery { getFreeBoardDetailUseCase(postId, profileId) } returns
                flow {
                    emit(
                        freeBoardPostDetailTestData,
                    )
                }

            coEvery { getFreeBoardCommentListUseCase(postId, any(), any(), any()) } returns
                flow {
                    emit(
                        PaginationData(
                            data = freeBoardCommentTestData,
                            paginationInfo =
                                PaginationInfo(
                                    hasNext = false,
                                    nextCursor = null,
                                ),
                        ),
                    )
                }

            viewModel =
                FreeBoardDetailViewModel(
                    getFreeBoardDetailUseCase,
                    getFreeBoardCommentListUseCase,
                    savedStateHandle =
                        SavedStateHandle(
                            mapOf(
                                "postId" to postId,
                                "profileId" to profileId,
                                "nickname" to nickname,
                            ),
                        ),
                    writeCommentUseCase = writeCommentUseCase,
                )
            advanceUntilIdle()

            coVerify(exactly = 1) {
                getFreeBoardDetailUseCase(postId, profileId)
                getFreeBoardCommentListUseCase(postId, any(), any(), any())
            }
        }
    }

    @Nested
    inner class `UI State Test` {
        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `CommentData fetch 성공,PostDetailData fetch 성공 시, uiState 은 FreeBoardDetailState_Success 를 반환한다`() =
            runTest {
                // given
                val postId = "1"
                val profileId = "profileId"
                val nickname = "nickname"
                coEvery { getFreeBoardDetailUseCase(postId, profileId) } returns
                    flow {
                        emit(
                            freeBoardPostDetailTestData,
                        )
                    }

                coEvery { getFreeBoardCommentListUseCase(postId, any(), any(), any()) } returns
                    flow {
                        emit(
                            PaginationData(
                                data = freeBoardCommentTestData,
                                paginationInfo =
                                    PaginationInfo(
                                        hasNext = false,
                                        nextCursor = null,
                                    ),
                            ),
                        )
                    }

                viewModel =
                    FreeBoardDetailViewModel(
                        getFreeBoardDetailUseCase,
                        getFreeBoardCommentListUseCase,
                        savedStateHandle =
                            SavedStateHandle(
                                mapOf(
                                    "postId" to postId,
                                    "profileId" to profileId,
                                    "nickname" to nickname,
                                ),
                            ),
                        writeCommentUseCase = writeCommentUseCase,
                    )
                advanceUntilIdle()

                // 상태 변화를 관찰하기 위한 collector
                val states = mutableListOf<FreeBoardDetailState>()
                val job =
                    launch(UnconfinedTestDispatcher()) {
                        viewModel.uiState.collect { states.add(it) }
                    }

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
                val profileId = "profileId"
                val nickname = "nickname"
                coEvery { getFreeBoardDetailUseCase(postId, profileId) } returns
                    flow {
                        throw Exception("Failed to fetch detail")
                    }

                coEvery {
                    getFreeBoardCommentListUseCase(
                        postId,
                        any(),
                        any(),
                        any(),
                    )
                } returns
                    flow {
                        emit(
                            PaginationData(
                                data = freeBoardCommentTestData,
                                paginationInfo =
                                    PaginationInfo(
                                        hasNext = false,
                                        nextCursor = null,
                                    ),
                            ),
                        )
                    }

                viewModel =
                    FreeBoardDetailViewModel(
                        getFreeBoardDetailUseCase,
                        getFreeBoardCommentListUseCase,
                        savedStateHandle =
                            SavedStateHandle(
                                mapOf(
                                    "postId" to postId,
                                    "profileId" to profileId,
                                    "nickname" to nickname,
                                ),
                            ),
                        writeCommentUseCase = writeCommentUseCase,
                    )
                advanceUntilIdle()

                // 상태 변화를 관찰하기 위한 collector
                val states = mutableListOf<FreeBoardDetailState>()
                val job =
                    launch(UnconfinedTestDispatcher()) {
                        viewModel.uiState.collect { states.add(it) }
                    }

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
                val profileId = "profileId"
                val nickname = "nickname"

                coEvery { getFreeBoardDetailUseCase(postId, profileId) } returns
                    flow {
                        emit(
                            freeBoardPostDetailTestData,
                        )
                    }

                coEvery { getFreeBoardCommentListUseCase(postId, any(), any(), any()) } returns
                    flow {
                        throw Exception("Failed to fetch comments")
                    }
                viewModel =
                    FreeBoardDetailViewModel(
                        getFreeBoardDetailUseCase,
                        getFreeBoardCommentListUseCase,
                        savedStateHandle =
                            SavedStateHandle(
                                mapOf(
                                    "postId" to postId,
                                    "profileId" to profileId,
                                    "nickname" to nickname,
                                ),
                            ),
                        writeCommentUseCase = writeCommentUseCase,
                    )
                advanceUntilIdle()

                // 상태 변화를 관찰하기 위한 collector
                val states = mutableListOf<FreeBoardDetailState>()
                val job =
                    launch(UnconfinedTestDispatcher()) {
                        viewModel.uiState.collect { states.add(it) }
                    }

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
