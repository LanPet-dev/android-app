package com.lanpet.feature.myposts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.UserProfile
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.domain.model.free.FreeBoardItem
import com.lanpet.domain.model.free.FreeBoardStat
import com.lanpet.domain.model.free.FreeBoardText

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
                val rememberGetFreeBoardPosts by rememberUpdatedState(getFreeBoardPosts)

                val scrollState = rememberLazyListState()
                val postList = uiState.postList

                // infinite scroll
                LaunchedEffect(Unit) {
                    snapshotFlow { scrollState.firstVisibleItemIndex }
                        .collect { index ->
                            if (index == postList.size - 1) {
                                // fetch more
                                rememberGetFreeBoardPosts()
                            }
                        }
                }

                LazyColumn(
                    state = scrollState,
                ) {
                    items(postList, key = { it.id }) { post ->
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
