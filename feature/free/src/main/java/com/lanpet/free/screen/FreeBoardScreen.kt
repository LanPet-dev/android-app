package com.lanpet.free.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.designsystem.R
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.WhiteColor
import com.lanpet.domain.model.FreeBoardCategoryType
import com.lanpet.free.viewmodel.FreeBoardListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardScreen(
    modifier: Modifier = Modifier,
    freeBoardListViewModel: FreeBoardListViewModel = hiltViewModel<FreeBoardListViewModel>(),
    onNavigateUp: (() -> Unit)? = null,
    onNavigateToFreeBoardWrite: () -> Unit = {},
    // TODO: 게시글 디테일 페이지로 이동. 게시글 디테일 id 필요할듯.
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        freeBoardListViewModel.getFreeBoardPostList()
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
                    .padding(it),
        ) {
            Column(
                modifier =
                    Modifier.verticalScroll(
                        state = scrollState,
                    ),
            ) {
                FreeBoardCategorySection(
                    modifier = Modifier.fillMaxSize(),
                    selectedCategory = FreeBoardCategoryType.ALL,
                    onCategoryClick = {},
                )
                FreeBoardPostList()
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FreeBoardCategorySection(
    selectedCategory: FreeBoardCategoryType,
    modifier: Modifier = Modifier,
    onCategoryClick: (String) -> Unit = {},
) {
    FlowRow(
        modifier =
            modifier.fillMaxWidth().padding(
                horizontal = LanPetDimensions.Margin.Layout.horizontal,
            ),
        horizontalArrangement = Arrangement.Start,
    ) {
        FreeBoardCategoryType.entries.forEach {
            SelectableChip.Rounded(
                isSelected = selectedCategory == it,
                title = it.title,
                onSelectedValueChange = {
                    // TODO
                    true
                },
            )
        }
    }
}

@Composable
fun FreeBoardPostList(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
    }
}

@PreviewLightDark
@Composable
private fun FreeBoardScreenPreview() {
    LanPetAppTheme {
        FreeBoardScreen()
    }
}
