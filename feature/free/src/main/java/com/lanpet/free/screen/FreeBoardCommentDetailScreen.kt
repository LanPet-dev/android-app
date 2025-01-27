package com.lanpet.free.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lanpet.core.common.widget.CommonNavigateUpButton
import com.lanpet.core.common.widget.LanPetCenterAlignedTopAppBar
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.Profile
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardSubComment
import com.lanpet.free.viewmodel.FreeBoardCommentDetailViewModel
import com.lanpet.free.viewmodel.SingleCommentUiState
import com.lanpet.free.widgets.FreeBoardCommentItem
import com.lanpet.free.widgets.LoadingUI

@Composable
fun FreeBoardCommentDetailScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    freeBoardCommentDetailViewModel: FreeBoardCommentDetailViewModel = hiltViewModel(),
) {
    FreeBoardCommentDetailScreen(
        singleCommentUiState = freeBoardCommentDetailViewModel.singleCommentUiState.collectAsStateWithLifecycle().value,
        modifier = modifier,
        onNavigateUp = onNavigateUp,
        subCommentInput = freeBoardCommentDetailViewModel.commentInput.collectAsStateWithLifecycle().value,
        onWriteSubComment = freeBoardCommentDetailViewModel::writeSubComment,
        onSubCommentInputChange = freeBoardCommentDetailViewModel::updateCommentInput,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardCommentDetailScreen(
    singleCommentUiState: SingleCommentUiState,
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    subCommentInput: String = "",
    onWriteSubComment: () -> Unit = {},
    onSubCommentInputChange: (String) -> Unit = {},
) {
    when (singleCommentUiState) {
        is SingleCommentUiState.Loading -> {
            LoadingUI()
        }

        is SingleCommentUiState.Success -> {
            Scaffold(
                topBar = {
                    LanPetCenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "댓글",
                                style = MaterialTheme.customTypography().body1SemiBoldSingle,
                            )
                        },
                        navigationIcon = {
                            CommonNavigateUpButton(
                                onNavigateUp = onNavigateUp,
                            )
                        },
                    )
                },
            ) {
                Surface(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(it),
                ) {
                    Column {
                        FreeBoardCommentItem(
                            freeBoardComment = singleCommentUiState.comment,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        CommentInputSection(
                            input = subCommentInput,
                            onWriteComment = onWriteSubComment,
                            onInputValueChange = onSubCommentInputChange,
                        )
                    }
                }
            }
        }

        is SingleCommentUiState.Error -> {
            Text(text = "Error")
        }
    }
}

@PreviewLightDark
@Composable
private fun FreeBoardCommentDetailScreenPreview() {
    val subComments =
        List(3) {
            FreeBoardSubComment(
                id = it.toString(),
                createdAt = "2021-01-01T00:00:00.123.000000",
                profile =
                    Profile(
                        nickname = "nickname",
                        profileImage = null,
                    ),
                comment = "subComment",
            )
        }

    LanPetAppTheme {
        FreeBoardCommentDetailScreen(
            singleCommentUiState =
                SingleCommentUiState.Success(
                    comment =
                        FreeBoardComment(
                            id = "1",
                            subComments = subComments,
                            createdAt = "2021-01-01T00:00:00.123.000000",
                            profile =
                                Profile(
                                    nickname = "nickname",
                                    profileImage = null,
                                ),
                            comment = "comment",
                        ),
                ),
        )
    }
}
