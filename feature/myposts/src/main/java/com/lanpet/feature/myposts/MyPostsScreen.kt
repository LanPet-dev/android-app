package com.lanpet.feature.myposts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.widget.CommonNavigateUpButton
import com.lanpet.core.common.widget.FreeBoardListItem
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.customTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPostsScreen(
    onNavigateUp: (() -> Unit)? = null
) {
    var currentTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPage = 0
    )

    LaunchedEffect(pagerState.currentPage) {
        currentTabIndex = pagerState.currentPage
    }

    LaunchedEffect(currentTabIndex) {
        pagerState.animateScrollToPage(currentTabIndex)
    }

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(stringResource(R.string.title_appbar_my_posts))
                },
                navigationIcon = {
                    if (onNavigateUp != null)
                        CommonNavigateUpButton {
                            onNavigateUp()
                        }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column {
                // Top tabbar
                TabBarSection(currentTabIndex) {
                    currentTabIndex = it
                }
                // TODO: content section. PageView with lists
                HorizontalPager(
                    pageSize = PageSize.Fill,
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState
                ) { index ->
                    if (index == 0) {
                        Column {
                            Text("Wiki")
                        }
                    } else if (index == 1) {
                        MyFreeBoardPosts()
                    }
                }
            }
        }
    }
}

@Composable
private fun TabBarSection(currentTabIndex: Int, onTabBarIndexChanged: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = currentTabIndex,
        divider = {
            HorizontalDivider(
                color = Color.Transparent,
            )
        },
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[currentTabIndex])
                    .padding(horizontal = 24.dp),
            )
        },
        //TODO: indicator
        tabs = {
            Tab(
                selected = currentTabIndex == 0,
                selectedContentColor = PrimaryColor.PRIMARY,
                unselectedContentColor = GrayColor.Gray400,
                onClick = {
                    onTabBarIndexChanged(0)
                },
            ) {
                Text(
                    stringResource(R.string.tab_title_wiki),
                    style = MaterialTheme.customTypography().body1SemiBoldSingle,
                    modifier = Modifier.padding(vertical = LanPetDimensions.Margin.medium)
                )
            }
            Tab(
                selectedContentColor = PrimaryColor.PRIMARY,
                unselectedContentColor = GrayColor.Gray400,
                selected = currentTabIndex == 1,
                onClick = {
                    onTabBarIndexChanged(1)
                },
            ) {
                Text(
                    stringResource(R.string.tab_title_freeboard),
                    style = MaterialTheme.customTypography().body1SemiBoldSingle,
                    modifier = Modifier.padding(vertical = LanPetDimensions.Margin.medium)
                )
            }
        }
    )
}

@Composable
fun MyFreeBoardPosts() {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(horizontal = LanPetDimensions.Margin.small)
            .fillMaxSize(),
    ) {
        items(10) { index ->
            key(index) {
                FreeBoardListItem()

                if (index < 10 - 1)
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = GrayColor.Gray100,
                        thickness = 1.dp
                    )
            }
        }

    }
}

@Composable
@PreviewLightDark
fun PreviewMyPostsScreen() {
    LanPetAppTheme {
        MyPostsScreen(
            onNavigateUp = {}
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewMyFreeBoardPosts() {
    LanPetAppTheme {
        MyFreeBoardPosts()
    }
}