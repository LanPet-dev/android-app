package com.lanpet.free.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.toast
import com.lanpet.core.common.widget.CommonNavigateUpButton
import com.lanpet.core.common.widget.LanPetCenterAlignedTopAppBar
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.Profile
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardSubComment
import com.lanpet.free.R
import com.lanpet.free.viewmodel.CommentDetailEvent
import com.lanpet.free.viewmodel.FreeBoardCommentDetailViewModel
import com.lanpet.free.viewmodel.SingleCommentUiState
import com.lanpet.free.widgets.CommentInput
import com.lanpet.free.widgets.FreeBoardCommentItem
import com.lanpet.free.widgets.LoadingUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FreeBoardCommentDetailScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    freeBoardCommentDetailViewModel: FreeBoardCommentDetailViewModel = hiltViewModel(),
) {
    val profileNickname =
        LocalAuthManager.current.defaultUserProfile
            .collectAsStateWithLifecycle()
            .value.nickname

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        freeBoardCommentDetailViewModel.event.collect {
            when (it) {
                CommentDetailEvent.WriteSubCommentFail() -> {
                    it.let { message ->
                        withContext(Dispatchers.Main) {
                            context.toast(context.getString(R.string.freeboard_comment_detail_write_sub_comment_fail))
                        }
                    }
                }

                CommentDetailEvent.WriteSubCommentSuccess() -> {
                    it.let { message ->
                        withContext(Dispatchers.Main) {
                            context.toast(context.getString(R.string.freeboard_comment_detail_write_sub_comment_success))
                        }
                    }
                }

                else -> {}
            }
        }
    }

    FreeBoardCommentDetailScreen(
        singleCommentUiState = freeBoardCommentDetailViewModel.singleCommentUiState.collectAsStateWithLifecycle().value,
        modifier = modifier,
        onNavigateUp = onNavigateUp,
        subCommentInput = freeBoardCommentDetailViewModel.commentInput.collectAsStateWithLifecycle().value,
        onWriteSubComment = freeBoardCommentDetailViewModel::writeSubComment,
        onSubCommentInputChange = freeBoardCommentDetailViewModel::updateCommentInput,
        onMoreSubComment = freeBoardCommentDetailViewModel::getSubComment,
        profileNickname = profileNickname,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardCommentDetailScreen(
    singleCommentUiState: SingleCommentUiState,
    profileNickname: String,
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    subCommentInput: String = "",
    onWriteSubComment: () -> Unit = {},
    onSubCommentInputChange: (String) -> Unit = {},
    onMoreSubComment: () -> Unit = {},
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
                        Column(
                            modifier =
                                Modifier
                                    .verticalScroll(
                                        state = rememberScrollState(),
                                    ).weight(1f),
                        ) {
                            FreeBoardCommentItem(
                                freeBoardComment = singleCommentUiState.comment,
                                profileNickname = profileNickname,
                                onMoreSubCommentClick = onMoreSubComment,
                                hasMoreSubComment = singleCommentUiState.hasMoreSubComment,
                            )
                        }
                        CommentInput(
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
        List(20) {
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
            profileNickname = "nickname",
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
