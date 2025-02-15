package com.lanpet.feature.myposts

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.lanpet.domain.model.free.FreeBoardPost
import com.lanpet.domain.usecase.freeboard.GetFreeBoardPostListUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MyPostsFreeBoardViewModelTest {
    lateinit var myPostsFreeBoardViewModel: MyPostsFreeBoardViewModel
    lateinit var getFreeBoardPostListUseCase: GetFreeBoardPostListUseCase

    @BeforeEach
    fun beforeEach() {
    }

    @AfterEach
    fun afterEach() {
        clearAllMocks()
    }

    @Nested
    inner class `Smoke Test` {
        @Test
        fun `When profileId is not provided, then throw IllegalArgumentException`() {
            val savedStateHandle = SavedStateHandle(mapOf("profileId" to null))

            getFreeBoardPostListUseCase = mockk<GetFreeBoardPostListUseCase>()

            assertThrows<IllegalArgumentException> {
                MyPostsFreeBoardViewModel(
                    getFreeBoardPostListUseCase = getFreeBoardPostListUseCase,
                    savedStateHandle = savedStateHandle,
                )
            }
        }

        @Test
        fun `When profileId is provided, then call init block`() {
            val savedStateHandle = SavedStateHandle(mapOf("profileId" to "profileId"))

            val _getFreeBoardPostListUseCase = mockk<GetFreeBoardPostListUseCase>()

            MyPostsFreeBoardViewModel(
                getFreeBoardPostListUseCase = _getFreeBoardPostListUseCase,
                savedStateHandle = savedStateHandle,
            )

            verify(exactly = 1) { _getFreeBoardPostListUseCase(any()) }
        }
    }

    @Nested
    inner class `getFreeBoardPostList Test` {
        @Test
        fun `When getFreeBoardPostListUseCase success, then return MyPostsFreeBoardUiState_Success`() =
            runTest {
                getFreeBoardPostListUseCase = mockk<GetFreeBoardPostListUseCase>()

                coEvery {
                    getFreeBoardPostListUseCase(any())
                }.returns(
                    flowOf(
                        FreeBoardPost(
                            items = emptyList(),
                            nextCursor = "nextCursor",
                            totalCount = 0,
                        ),
                    ),
                )

                myPostsFreeBoardViewModel =
                    MyPostsFreeBoardViewModel(
                        getFreeBoardPostListUseCase = getFreeBoardPostListUseCase,
                        savedStateHandle = SavedStateHandle(mapOf("profileId" to "profileId")),
                    )

                verify(exactly = 1) { getFreeBoardPostListUseCase(any()) }

                myPostsFreeBoardViewModel.uiState.test {
                    val recent = expectMostRecentItem()

                    assert(recent is MyPostsFreeBoardUiState.Success)
                }
            }

        @Test
        fun `When getFreeBoardPostListUseCase failure, then return MyPostsFreeBoardUiState_Error`() =
            runTest {
                getFreeBoardPostListUseCase = mockk<GetFreeBoardPostListUseCase>()

                coEvery {
                    getFreeBoardPostListUseCase(any())
                }.throws(Exception("error"))

                myPostsFreeBoardViewModel =
                    MyPostsFreeBoardViewModel(
                        getFreeBoardPostListUseCase = getFreeBoardPostListUseCase,
                        savedStateHandle = SavedStateHandle(mapOf("profileId" to "profileId")),
                    )

                verify(exactly = 1) { getFreeBoardPostListUseCase(any()) }

                myPostsFreeBoardViewModel.uiState.test {
                    val recent = expectMostRecentItem()

                    assert(recent is MyPostsFreeBoardUiState.Error)
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
