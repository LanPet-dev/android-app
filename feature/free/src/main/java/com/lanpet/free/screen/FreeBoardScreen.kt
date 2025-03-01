package com.lanpet.free.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.widget.FreeBoardListItem
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.PreparingScreen
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.designsystem.R
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.WhiteColor
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.domain.model.free.FreeBoardItem
import com.lanpet.domain.model.free.FreeBoardResource
import com.lanpet.domain.model.free.FreeBoardStat
import com.lanpet.domain.model.free.FreeBoardText
import com.lanpet.free.viewmodel.FreeBoardListState
import com.lanpet.free.viewmodel.FreeBoardListViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    freeBoardListViewModel: FreeBoardListViewModel = hiltViewModel<FreeBoardListViewModel>(),
    onNavigateToFreeBoardWrite: () -> Unit = {},
    onNavigateToFreeBoardDetail: (String, String, String) -> Unit = { _, _, _ -> },
) {
    val scrollState = rememberScrollState()
    val uiState by freeBoardListViewModel.uiState.collectAsStateWithLifecycle()
    val savedStateHandle =
        navController.currentBackStackEntry
            ?.savedStateHandle

    LaunchedEffect(Unit) {
        if (savedStateHandle?.contains("deletedPostId") == true) {
            Timber.i("deletedPostId: ${savedStateHandle.get<String>("deletedPostId")}")
            freeBoardListViewModel.removePostCache(savedStateHandle.get<String>("deletedPostId")!!)
            savedStateHandle.remove<String>("deletedPostId")
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateToFreeBoardWrite()
                },
                containerColor = PrimaryColor.PRIMARY,
                contentColor = WhiteColor.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 80.dp),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_edit_outline),
                    "Floating action button.",
                )
            }
        },
        topBar = {
            LanPetTopAppBar(
                title = { Text(stringResource(R.string.title_appbar_freeboard)) },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(bottom = 64.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize(),
            ) {
                FreeBoardCategorySection(
                    selectedCategory = freeBoardListViewModel.selectedCategoryFlow.collectAsStateWithLifecycle().value,
                    onCategoryClick = {
                        freeBoardListViewModel.setCategory(it, forceRefresh = true)
                    },
                )
                Spacer(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .size(LanPetDimensions.Spacing.xxSmall)
                            .background(MaterialTheme.customColorScheme.spacerLine),
                )
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .weight(1f),
                ) {
                    when (uiState) {
                        is FreeBoardListState.Initial -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .weight(1f),
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is FreeBoardListState.Success -> {
                            if ((uiState as FreeBoardListState.Success).data.isEmpty()) {
                                PreparingScreen(
                                    titleResId = R.string.title_no_freeboard_post,
                                )
                            } else {
                                PullToRefreshBox(
                                    onRefresh = { freeBoardListViewModel.refresh() },
                                    isRefreshing = false,
                                    modifier =
                                        Modifier
                                            .fillMaxSize()
                                            .weight(1f),
                                ) {
                                    FreeBoardPostList(
                                        modifier =
                                            Modifier.verticalScroll(
                                                state = scrollState,
                                            ),
                                        isLoading = (uiState as FreeBoardListState.Success).isLoading,
                                        freeBoardItemList = (uiState as FreeBoardListState.Success).data,
                                        onNavigateToFreeBoardDetail = onNavigateToFreeBoardDetail,
                                        onLoadMore = {
                                            freeBoardListViewModel.getFreeBoardPostList()
                                        },
                                    )
                                }
                            }
                        }

                        is FreeBoardListState.Error -> Text((uiState as FreeBoardListState.Error).message.toString())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FreeBoardCategorySection(
    selectedCategory: FreeBoardCategoryType,
    modifier: Modifier = Modifier,
    onCategoryClick: (FreeBoardCategoryType) -> Unit = {},
) {
    FlowRow(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                ),
        horizontalArrangement = Arrangement.Start,
    ) {
        SelectableChip.Rounded(
            isSelected = selectedCategory == FreeBoardCategoryType.ALL,
            title = FreeBoardCategoryType.ALL.title,
            onSelectedValueChange = {
                onCategoryClick(FreeBoardCategoryType.ALL)
            },
        )

        SelectableChip.Rounded(
            isSelected = selectedCategory == FreeBoardCategoryType.COMMUNICATION,
            title = FreeBoardCategoryType.COMMUNICATION.title,
            onSelectedValueChange = {
                onCategoryClick(FreeBoardCategoryType.COMMUNICATION)
            },
        )

        SelectableChip.Rounded(
            isSelected = selectedCategory == FreeBoardCategoryType.RECOMMENDATION,
            title = FreeBoardCategoryType.RECOMMENDATION.title,
            onSelectedValueChange = {
                onCategoryClick(FreeBoardCategoryType.RECOMMENDATION)
            },
        )

        SelectableChip.Rounded(
            isSelected = selectedCategory == FreeBoardCategoryType.CURIOUS,
            title = FreeBoardCategoryType.CURIOUS.title,
            onSelectedValueChange = {
                onCategoryClick(FreeBoardCategoryType.CURIOUS)
            },
        )
    }
}

@Composable
fun FreeBoardPostList(
    freeBoardItemList: List<FreeBoardItem>,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onNavigateToFreeBoardDetail: (String, String, String) -> Unit = { _, _, _ -> },
    onLoadMore: () -> Unit = {},
) {
    val state = rememberLazyListState()
    val rememberOnLoadMore = remember { onLoadMore }
    val profileId =
        LocalAuthManager.current.defaultUserProfile
            .collectAsStateWithLifecycle()
            .value.id
    val nickname =
        LocalAuthManager.current.defaultUserProfile
            .collectAsStateWithLifecycle()
            .value.nickname

    LaunchedEffect(state) {
        snapshotFlow {
            val layoutInfo = state.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleIndex = (state.firstVisibleItemIndex + layoutInfo.visibleItemsInfo.size)

            totalItems > 0 && lastVisibleIndex >= (totalItems - 2)
        }.distinctUntilChanged()
            .collect { shouldLoadMore ->
                Timber.d("shouldLoadMore: $shouldLoadMore")
                if (shouldLoadMore) {
                    rememberOnLoadMore()
                }
            }
    }

    LazyColumn(
        state = state,
    ) {
        items(freeBoardItemList.size, key = { freeBoardItemList[it].id }) { index ->
            FreeBoardListItem(
                modifier = Modifier.animateItem(),
                freeBoardPostItem = freeBoardItemList[index],
                onClick = {
                    onNavigateToFreeBoardDetail(
                        freeBoardItemList[index].id,
                        profileId,
                        nickname,
                    )
                },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FreeBoardScreenPreview() {
    LanPetAppTheme {
        FreeBoardScreen(
            navController = rememberNavController(),
        )
    }
}

@PreviewLightDark
@Composable
private fun FreeBoardPostListPreview() {
    LanPetAppTheme {
        FreeBoardPostList(
            freeBoardItemList =
                listOf(
                    FreeBoardItem(
                        id = "5",
                        category = FreeBoardCategoryType.RECOMMENDATION,
                        petType = PetCategory.OTHER,
                        text =
                            FreeBoardText(
                                title = "제목5",
                                content = "내용5",
                            ),
                        stat =
                            FreeBoardStat(
                                likeCount = 10,
                                commentCount = 5,
                            ),
                        resources =
                            listOf(
                                FreeBoardResource(
                                    id = "5",
                                    url = "https://www.naver.com",
                                ),
                            ),
                        created = "2021-08-01T12:34:56+09:00",
                    ),
                    FreeBoardItem(
                        id = "6",
                        category = FreeBoardCategoryType.CURIOUS,
                        petType = PetCategory.OTHER,
                        text =
                            FreeBoardText(
                                title = "제목6",
                                content = "내용6",
                            ),
                        stat =
                            FreeBoardStat(
                                likeCount = 10,
                                commentCount = 5,
                            ),
                        resources =
                            listOf(
                                FreeBoardResource(
                                    id = "6",
                                    url = "https://www.naver.com",
                                ),
                            ),
                        created = "2021-08-01T12:34:56+09:00",
                    ),
                ),
            isLoading = true,
            onNavigateToFreeBoardDetail = { _, _, _ -> },
            onLoadMore = {},
        )
    }
}
