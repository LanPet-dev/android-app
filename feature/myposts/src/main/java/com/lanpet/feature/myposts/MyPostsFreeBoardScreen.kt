package com.lanpet.feature.myposts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lanpet.core.auth.BasePreviewWrapper
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.widget.FreeBoardListItem
import com.lanpet.core.common.widget.LoadingView
import com.lanpet.core.common.widget.PreparingScreen
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.UserProfile
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.domain.model.free.FreeBoardItem
import com.lanpet.domain.model.free.FreeBoardStat
import com.lanpet.domain.model.free.FreeBoardText
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber

@Composable
fun MyPostsFreeBoardScreen(
    onNavigateToFreeBoardDetail: (postId: String, profileId: String, nickname: String) -> Unit,
    myPostsFreeBoardViewModel: MyPostsFreeBoardViewModel = hiltViewModel(),
) {
    val uiState by myPostsFreeBoardViewModel.uiState.collectAsStateWithLifecycle()

    val defaultProfile by LocalAuthManager.current.defaultUserProfile.collectAsStateWithLifecycle()

    MyPostsFreeBoardScreen(
        onNavigateToFreeBoardDetail = onNavigateToFreeBoardDetail,
        defaultProfile = defaultProfile,
        getFreeBoardPosts = { myPostsFreeBoardViewModel.getFreeBoardPostList() },
        uiState = uiState,
    )
}

@Composable
fun MyPostsFreeBoardScreen(
    onNavigateToFreeBoardDetail: (postId: String, profileId: String, nickname: String) -> Unit,
    defaultProfile: UserProfile,
    uiState: MyPostsFreeBoardUiState,
    modifier: Modifier = Modifier,
    getFreeBoardPosts: () -> Unit = {},
) {
    Box(
        modifier = modifier.then(Modifier.fillMaxSize()),
    ) {
        when (uiState) {
            is MyPostsFreeBoardUiState.Loading -> {
                LoadingView()
            }

            is MyPostsFreeBoardUiState.Success -> {
                val scrollState = rememberLazyListState()
                val postList = uiState.postList

                InfiniteScrollSideEffect(scrollState, getFreeBoardPosts)

                if (postList.isEmpty()) {
                    PreparingScreen(titleResId = R.string.my_posts_free_board_empty)
                    return@Box
                }

                LazyColumn(
                    state = scrollState,
                ) {
                    items(postList, ) { post ->
                        FreeBoardListItem(
                            freeBoardPostItem = post,
                            onClick = {
                                onNavigateToFreeBoardDetail(
                                    post.id,
                                    defaultProfile.id,
                                    defaultProfile.nickname,
                                )
                            },
                        )
                    }
                }
            }

            is MyPostsFreeBoardUiState.Error -> {
                Text(text = "Error")
            }
        }
    }
}

@Composable
private fun InfiniteScrollSideEffect(
    scrollState: LazyListState,
    block: () -> Unit = {},
) {
    val rememberBlock by rememberUpdatedState(block)

    LaunchedEffect(Unit) {
        snapshotFlow {
            val layoutInfo = scrollState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleIndex =
                (scrollState.firstVisibleItemIndex + layoutInfo.visibleItemsInfo.size)

            Timber.d(
                "layoutInfo: $layoutInfo," +
                    "totalItems: $totalItems, lastVisibleIndex: $lastVisibleIndex",
            )

            totalItems > 0 && lastVisibleIndex >= (totalItems - 2)
        }.distinctUntilChanged()
            .collect { shouldLoadMore ->
                if (shouldLoadMore) {
                    rememberBlock()
                }
            }
    }
}

@PreviewLightDark
@Composable
private fun MyPostsFreeBoardScreen_UiState_Success_Preview() {
    BasePreviewWrapper {
        MyPostsFreeBoardScreen(
            onNavigateToFreeBoardDetail = { _, _, _ -> },
            defaultProfile =
                UserProfile(
                    id = "1",
                    nickname = "nickname",
                    type = ProfileType.BUTLER,
                    profileImageUri = "",
                    introduction = "TEST",
                ),
            uiState =
                MyPostsFreeBoardUiState.Success(
                    postList =
                        listOf(
                            FreeBoardItem(
                                id = "1",
                                category = FreeBoardCategoryType.ALL,
                                petType = PetCategory.CAT,
                                text =
                                    FreeBoardText(
                                        title = "title",
                                        content = "content",
                                    ),
                                stat =
                                    FreeBoardStat(
                                        likeCount = 1,
                                        commentCount = 1,
                                    ),
                                resources = emptyList(),
                                created = "2021-09-01T00:00:00.000+00:00",
                            ),
                            FreeBoardItem(
                                id = "2",
                                category = FreeBoardCategoryType.ALL,
                                petType = PetCategory.DOG,
                                text =
                                    FreeBoardText(
                                        title = "title",
                                        content = "content",
                                    ),
                                stat =
                                    FreeBoardStat(
                                        likeCount = 1,
                                        commentCount = 1,
                                    ),
                                resources = emptyList(),
                                created = "2021-09-01T00:00:00.000+00:00",
                            ),
                        ),
                ),
        )
    }
}

@PreviewLightDark
@Composable
private fun MyPostsFreeBoardScreen_UiState_Loading_Preview() {
    BasePreviewWrapper {
        MyPostsFreeBoardScreen(
            onNavigateToFreeBoardDetail = { _, _, _ -> },
            defaultProfile =
                UserProfile(
                    id = "1",
                    nickname = "nickname",
                    type = ProfileType.BUTLER,
                    profileImageUri = "",
                    introduction = "TEST",
                ),
            uiState = MyPostsFreeBoardUiState.Loading,
        )
    }
}

@PreviewLightDark
@Composable
private fun MyPostsFreeBoardScreen_UiState_Success_Empty_Preview() {
    BasePreviewWrapper {
        MyPostsFreeBoardScreen(
            onNavigateToFreeBoardDetail = { _, _, _ -> },
            defaultProfile =
                UserProfile(
                    id = "1",
                    nickname = "nickname",
                    type = ProfileType.BUTLER,
                    profileImageUri = "",
                    introduction = "TEST",
                ),
            uiState =
                MyPostsFreeBoardUiState.Success(
                    postList = emptyList(),
                ),
        )
    }
}
