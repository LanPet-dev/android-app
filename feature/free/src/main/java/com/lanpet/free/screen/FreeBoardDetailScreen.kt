package com.lanpet.free.screen

import android.content.res.Configuration
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.lanpet.core.auth.BasePreviewWrapper
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.createdAtPostString
import com.lanpet.core.common.toast
import com.lanpet.core.common.widget.ActionButton
import com.lanpet.core.common.widget.BaseDialog
import com.lanpet.core.common.widget.CommonChip
import com.lanpet.core.common.widget.CommonNavigateUpButton
import com.lanpet.core.common.widget.IOSActionSheet
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.PreparingScreen
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.Profile
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardPostDetail
import com.lanpet.free.R
import com.lanpet.free.viewmodel.FreeBoardDetailEvent
import com.lanpet.free.viewmodel.FreeBoardDetailState
import com.lanpet.free.viewmodel.FreeBoardDetailViewModel
import com.lanpet.free.viewmodel.FreeBoardLikeEvent
import com.lanpet.free.viewmodel.FreeBoardLikesViewModel
import com.lanpet.free.widgets.CommentInput
import com.lanpet.free.widgets.FreeBoardCommentItem
import com.lanpet.free.widgets.LoadingUI
import kotlinx.coroutines.launch
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardDetailScreen(
    onNavigateUp: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    freeBoardDetailViewModel: FreeBoardDetailViewModel = hiltViewModel<FreeBoardDetailViewModel>(),
    onNavigateToFreeBoardCommentDetail: (postId: String, freeBoardComment: FreeBoardComment) -> Unit = { _, _ -> },
) {
    val state = freeBoardDetailViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val contentActionState = rememberModalBottomSheetState()
    var deleteContentState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    when (val currentState = state.value) {
        is FreeBoardDetailState.Loading -> {
            Scaffold(
                topBar = {
                    LanPetTopAppBar(
                        navigationIcon = {
                            CommonNavigateUpButton {
                                onNavigateUp()
                            }
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
                    LoadingUI()
                }
            }
        }

        is FreeBoardDetailState.Success -> {
            var input by rememberSaveable { mutableStateOf("") }
            val authManager = LocalAuthManager.current
            val defaultProfile =
                authManager.defaultUserProfile
                    .collectAsState()
                    .value

            LaunchedEffect(Unit) {
                freeBoardDetailViewModel.uiEvent.collect { event ->
                    when (event) {
                        FreeBoardDetailEvent.WriteCommentFail -> {
                            context.toast("댓글 작성에 실패했습니다.")
                        }

                        FreeBoardDetailEvent.WriteCommentSuccess -> {
                            context.toast("댓글이 작성되었습니다.")
                            input = ""
                        }
                    }
                }
            }

            if (deleteContentState) {
                Dialog(
                    onDismissRequest = {
                        deleteContentState = false
                    },
                ) {
                    BaseDialog(
                        content = {
                            Text(
                                text = "게시글을 삭제할까요?",
                                style = MaterialTheme.customTypography().body1SemiBoldSingle,
                            )
                        },
                        confirm = {
                            TextButton(
                                onClick = {
                                    deleteContentState = false
                                    // TODO
                                    navController.previousBackStackEntry?.savedStateHandle?.set(
                                        "deletedPostId",
                                        currentState.postDetail.id,
                                    )
                                    onNavigateUp()
                                },
                            ) {
                                Text(
                                    text = "삭제",
                                )
                            }
                        },
                        dismiss = {
                            TextButton(
                                onClick = {
                                    deleteContentState = false
                                },
                            ) {
                                Text(
                                    text = "취소할게요",
                                )
                            }
                        },
                    )
                }
            }

            if (contentActionState.isVisible) {
                ModalBottomSheet(
                    onDismissRequest = {},
                    sheetState = contentActionState,
                    containerColor = Color.Transparent,
                ) {
                    IOSActionSheet(
                        cancelButton = {
                            ActionButton(
                                text = "닫기",
                                onClick = {
                                    scope.launch {
                                        contentActionState.hide()
                                    }
                                },
                            )
                        },
                        content = {
                            Column {
                                ActionButton(text = "수정", onClick = { /* */ })
                                HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                                ActionButton(
                                    text = "삭제",
                                    buttonColors =
                                        ButtonColors(
                                            contentColor = Color.Red,
                                            containerColor = Color.White,
                                            disabledContainerColor = GrayColor.Gray950,
                                            disabledContentColor = Color.White,
                                        ),
                                    onClick = {
                                        scope.launch {
                                            deleteContentState = true
                                            contentActionState.hide()
                                        }
                                    },
                                )
                            }
                        },
                        modifier = Modifier,
                    )
                }
            }

            Scaffold(
                topBar = {
                    LanPetTopAppBar(
                        navigationIcon = {
                            CommonNavigateUpButton {
                                onNavigateUp()
                            }
                        },
                        actions =
                            if (currentState.isOwner) {
                                {
                                    IconButton(onClick = {
                                        scope.launch {
                                            contentActionState.show()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.MoreVert,
                                            contentDescription = "ic_MoreVert",
                                        )
                                    }
                                }
                            } else {
                                {}
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
                    ContentUI(
                        currentState,
                        isOwner = currentState.isOwner,
                        commentInput = input,
                        onInputValueChange = { value ->
                            input = value
                        },
                        onDeleteComment = { postId, commentId ->
                            freeBoardDetailViewModel.deleteComment(postId, commentId)
                        },
                        onWriteComment = {
                            freeBoardDetailViewModel.writeComment(
                                postId = (state.value as FreeBoardDetailState.Success).postDetail.id,
                                comment = input,
                                profileId = defaultProfile.id,
                                profile =
                                    Profile(
                                        nickname = defaultProfile.nickname,
                                        profileImage = defaultProfile.profileImageUri,
                                    ),
                            )
                        },
                        onFetchComment = {
                            freeBoardDetailViewModel.fetchComments()
                        },
                        onLikeChange = { like ->
                            if (like) {
                                freeBoardDetailViewModel.likePost()
                            } else {
                                freeBoardDetailViewModel.dislikePost()
                            }
                        },
                        onNavigateToFreeBoardCommentDetail = onNavigateToFreeBoardCommentDetail,
                    )
                }
            }
        }

        is FreeBoardDetailState.Error -> {
            Scaffold(
                topBar = {
                    LanPetTopAppBar(
                        navigationIcon = {
                            CommonNavigateUpButton {
                                onNavigateUp()
                            }
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        PreparingScreen(
                            titleResId = R.string.title_error_freeboard_detail,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContentUI(
    state: FreeBoardDetailState.Success,
    onInputValueChange: (String) -> Unit,
    commentInput: String,
    onWriteComment: () -> Unit,
    onLikeChange: (Boolean) -> Unit,
    onFetchComment: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteComment: (String, String) -> Unit = { _, _ -> },
    isOwner: Boolean = false,
    onNavigateToFreeBoardCommentDetail: (postId: String, freeBoardComment: FreeBoardComment) -> Unit = { _, _ -> },
    freeBoardLikesViewModel: FreeBoardLikesViewModel = hiltViewModel(),
) {
    val verticalScrollState = rememberScrollState()

    val rememberOnLikeChange = remember { onLikeChange }

    LaunchedEffect(Unit) {
        freeBoardLikesViewModel.uiEvent.collect {
            when (it) {
                is FreeBoardLikeEvent.Success -> {
                    rememberOnLikeChange(it.isLike)
                }

                FreeBoardLikeEvent.Error -> {}
            }
        }
    }

    Column(
        modifier =
            Modifier
                .verticalScroll(
                    state = verticalScrollState,
                ),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.small),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CommonChip(state.postDetail.freeBoardCategory.title)
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxSmall))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                Text(
                    state.postDetail.petCategory.value,
                    style = MaterialTheme.customTypography().body3RegularSingle,
                )
            }
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))

            Text(
                state.postDetail.title,
                style = MaterialTheme.customTypography().title3SemiBoldMulti,
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (state.postDetail.writerImage != null) {
                    AsyncImage(
                        state.postDetail.writerImage,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clip(shape = CircleShape),
                    )
                } else {
                    Image(
                        painter = painterResource(id = DS_R.drawable.ic_bottom_nav_mypage_unselected),
                        contentDescription = "ic_profile",
                        modifier =
                            Modifier
                                .size(24.dp),
                    )
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxSmall))
                Text(
                    state.postDetail.writer,
                    style = MaterialTheme.customTypography().body3RegularSingle,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    createdAtPostString(state.postDetail.createdAt),
                    style =
                        MaterialTheme.customTypography().body2RegularSingle.copy(
                            color = GrayColor.Gray300,
                        ),
                )
            }
        }

        // line
        Spacer(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = LanPetDimensions.Spacing.medium)
                    .size(1.dp)
                    .background(GrayColor.Gray50),
        )

        Text(
            state.postDetail.content,
            style = MaterialTheme.customTypography().body2RegularMulti,
            modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.small),
        )

        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))

        state.postDetail.images.map {
//            AsyncImage(
//                model = it.url,
//                contentDescription = "post_image",
//                contentScale = ContentScale.Crop,
//                modifier =
//                    Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            horizontal = LanPetDimensions.Spacing.small,
//                            vertical = LanPetDimensions.Spacing.small,
//                        ).clip(
//                            shape =
//                                RoundedCornerShape(
//                                    LanPetDimensions.Corner.medium,
//                                ),
//                        ),
//                error = painterResource(id = DS_R.drawable.img_preparing),
//            )
            ImageWithFullscreenViewer(
                imageUrl = it.url,
                contentDescription = "post_image",
            )
        }
        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
        LikeButton(
            isLiked = state.postDetail.isLike,
            likeCount = state.postDetail.likeCount,
            onLikeClick = {
                if (state.postDetail.isLike) {
                    freeBoardLikesViewModel.cancelPostLike()
                } else {
                    freeBoardLikesViewModel.doPostLike()
                }
            },
        )
        // line
        Spacer(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = LanPetDimensions.Spacing.medium)
                    .size(4.dp)
                    .background(GrayColor.Gray50),
        )
        FreeBoardCommentSection(
            postId = state.postDetail.id,
            commentCount = state.postDetail.commentCount,
            comments = state.comments,
            canLoadMore = state.canLoadMoreComments,
            onDeleteComment = { postId, commentId ->
                onDeleteComment(postId, commentId)
            },
            onLoadMore = {
                onFetchComment()
            },
            onMoreSubCommentClick = onNavigateToFreeBoardCommentDetail,
            onCommentClick = onNavigateToFreeBoardCommentDetail,
        )
        // line
        Spacer(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = LanPetDimensions.Spacing.medium)
                    .size(4.dp)
                    .background(GrayColor.Gray50),
        )
        Spacer(modifier = Modifier.weight(1f))
        CommentInput(
            onWriteComment = onWriteComment,
            input = commentInput,
            onInputValueChange = onInputValueChange,
        )
        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FreeBoardCommentSection(
    postId: String,
    canLoadMore: Boolean,
    modifier: Modifier = Modifier,
    commentCount: Int = 0,
    comments: List<FreeBoardComment> = emptyList(),
    onDeleteComment: (String, String) -> Unit = { _, _ -> },
    onLoadMore: () -> Unit = {},
    onMoreSubCommentClick: (String, FreeBoardComment) -> Unit = { _, _ -> },
    onCommentClick: (String, FreeBoardComment) -> Unit = { _, _ -> },
) {
    val nickname =
        LocalAuthManager.current.defaultUserProfile
            .collectAsStateWithLifecycle()
            .value.nickname

    val scope = rememberCoroutineScope()

    val commentActionState = rememberModalBottomSheetState()
    val selectedCommentId = remember { mutableStateOf("") }

    if (commentActionState.isVisible) {
        ModalBottomSheet(
            sheetState = commentActionState,
            onDismissRequest = {},
            containerColor = Color.Transparent,
        ) {
            IOSActionSheet(
                cancelButton = {
                    ActionButton(
                        text = "닫기",
                        onClick = {
                            scope.launch {
                                commentActionState.hide()
                            }
                        },
                    )
                },
                content = {
                    Column {
                        ActionButton(text = "수정", onClick = { /* */ })
                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        ActionButton(
                            text = "삭제",
                            buttonColors =
                                ButtonColors(
                                    contentColor = Color.Red,
                                    containerColor = Color.White,
                                    disabledContainerColor = GrayColor.Gray950,
                                    disabledContentColor = Color.White,
                                ),
                            onClick = {
                                scope.launch {
                                    commentActionState.hide()
                                    onDeleteComment(postId, selectedCommentId.value)
                                }
                            },
                        )
                    }
                },
                modifier = Modifier,
            )
        }
    }

    Column {
        Text(
            "댓글 $commentCount",
            style = MaterialTheme.customTypography().body2RegularMulti,
            modifier =
                Modifier.padding(
                    horizontal = LanPetDimensions.Spacing.small,
                    vertical = LanPetDimensions.Spacing.small,
                ),
        )
        if (comments.isEmpty()) {
            Text(
                stringResource(R.string.body_no_comment_freeboard_detail),
                style =
                    MaterialTheme.customTypography().body2RegularSingle.copy(
                        color = GrayColor.Gray400,
                    ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
        } else {
            Column {
                comments.forEachIndexed { index, comment ->
                    androidx.compose.animation.AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        FreeBoardCommentItem(
                            freeBoardComment = comment,
                            isOwner = comment.profile.nickname == nickname,
                            onOwnerActionClick = {
                                scope.launch {
                                    selectedCommentId.value = comment.id
                                    commentActionState.show()
                                }
                            },
                            onMoreSubCommentClick = {
                                onMoreSubCommentClick(
                                    postId,
                                    comment,
                                )
                            },
                            onCommentClick = {
                                onCommentClick(
                                    postId,
                                    comment,
                                )
                            },
                            profileNickname = nickname,
                            hasMoreSubComment = comment.subComments.size > 9,
                        )
                    }
                }
                if (canLoadMore) {
                    Text(
                        stringResource(R.string.freeboard_detail_button_more_comment),
                        modifier =
                            Modifier
                                .padding(
                                    horizontal = LanPetDimensions.Margin.medium,
                                    vertical = LanPetDimensions.Margin.small,
                                ).clickable(
                                    interactionSource = null,
                                    indication = null,
                                ) { onLoadMore() },
                        style = MaterialTheme.customTypography().body2RegularSingle.copy(color = GrayColor.Gray400),
                    )
                }
            }
        }
    }
}

@Composable
fun LikeButton(
    isLiked: Boolean,
    likeCount: Int,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val likedBorderColor = if (isLiked) PrimaryColor.PRIMARY else GrayColor.Gray500
    val likedTextColor = if (isLiked) PrimaryColor.PRIMARY else GrayColor.Gray500

    Box(
        modifier =
            modifier
                .padding(
                    horizontal = LanPetDimensions.Spacing.small,
                    vertical = LanPetDimensions.Spacing.small,
                ).border(
                    width = 1.dp,
                    color = likedBorderColor,
                    shape = CircleShape,
                ).clip(CircleShape)
                .clickable { onLikeClick() }
                .padding(LanPetDimensions.Spacing.small),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            "좋아요 $likeCount",
            style =
                MaterialTheme.customTypography().body2RegularSingle.copy(
                    color = likedTextColor,
                ),
        )
    }
}

@Composable
private fun EmojiPicker(
    onEmojiSelect: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    val emojis = listOf("😀", "😍", "🥰", "😎", "🤔", "😊", "🎉", "👍", "❤️", "🌟")

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(LanPetDimensions.Spacing.small),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("이모티콘", style = MaterialTheme.customTypography().body2RegularSingle)
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "닫기")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(LanPetDimensions.Spacing.small),
        ) {
            items(emojis) { emoji ->
                Box(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .clickable { onEmojiSelect(emoji) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        emoji,
                        style = MaterialTheme.customTypography().body1RegularSingle,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewCommentInputSection() {
    LanPetAppTheme {
        CommentInput()
    }
}

@Preview(heightDp = 2000)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 2000)
@Composable
private fun FreeBoardDetailPreview() {
    LanPetAppTheme {
        FreeBoardDetailScreen(
            onNavigateUp = {},
            navController = rememberNavController(),
            onNavigateToFreeBoardCommentDetail = { _, _ -> },
        )
    }
}

@Composable
@PreviewLightDark
private fun FreeBoardCommentSection_Empty_Preview() {
    BasePreviewWrapper {
        Column {
            FreeBoardCommentSection(
                comments = emptyList(),
                canLoadMore = false,
                onLoadMore = {},
                postId = "1",
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun FreeBoardCommentSection_Filled_Preview() {
    BasePreviewWrapper {
        FreeBoardCommentSection(
            onLoadMore = {},
            canLoadMore = true,
            postId = "1",
        )
    }
}

@Composable
@PreviewLightDark
private fun SuccessUIPreview() {
    BasePreviewWrapper {
        ContentUI(
            state =
                FreeBoardDetailState.Success(
                    postDetail =
                        FreeBoardPostDetail(
                            id = "1",
                            title = "title",
                            content = "content",
                            writer = "Writer",
                            writerImage = null,
                            petCategory = PetCategory.DOG,
                            createdAt = "2021-01-01T00:00:00Z",
                            likeCount = 1,
                            commentCount = 1,
                            images = emptyList(),
                            freeBoardCategory = FreeBoardCategoryType.CURIOUS,
                            isLike = true,
                            subCommentCount = 0,
                        ),
                    comments = emptyList(),
                ),
            onInputValueChange = {},
            commentInput = "",
            onWriteComment = {},
            onLikeChange = {},
            onFetchComment = {},
            modifier = Modifier,
            isOwner = true,
            onNavigateToFreeBoardCommentDetail = { _, _ -> },
        )
    }
}

@Composable
@PreviewLightDark
private fun LikeButtonPreview() {
    LanPetAppTheme {
        Column {
            LikeButton(
                isLiked = false,
                likeCount = 10,
                onLikeClick = {},
            )
            LikeButton(
                isLiked = true,
                likeCount = 10,
                onLikeClick = {},
            )
        }
    }
}

@Composable
fun ImageWithFullscreenViewer(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    var showFullscreen by remember { mutableStateOf(false) }

    // 썸네일 이미지
    AsyncImage(
        model =
            ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
        contentDescription = contentDescription,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    horizontal = LanPetDimensions.Spacing.small,
                    vertical = LanPetDimensions.Spacing.small,
                ).clip(RoundedCornerShape(8.dp))
                .then(
                    Modifier.clickable {
                        showFullscreen = true
                    },
                ),
        contentScale = ContentScale.Crop,
    )

    // 전체화면 다이얼로그
    if (showFullscreen) {
        Dialog(
            onDismissRequest = { showFullscreen = false },
            properties =
                DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    usePlatformDefaultWidth = false,
                ),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black),
            ) {
                var scale by remember { mutableFloatStateOf(1f) }
                var offset by remember { mutableStateOf(Offset.Zero) }

                // 이미지와 화면 크기를 저장할 변수들
                var imageSize by remember { mutableStateOf(IntSize.Zero) }
                var boxSize by remember { mutableStateOf(IntSize.Zero) }

                // 오프셋 제한 함수
                fun limitOffset(offset: Offset): Offset {
                    // 이미지의 실제 표시 크기 계산
                    val scaledWidth = imageSize.width * scale
                    val scaledHeight = imageSize.height * scale

                    // 이미지가 화면보다 작으면 중앙에 고정
                    if (scaledWidth <= boxSize.width) {
                        return Offset(0f, offset.y)
                    }
                    if (scaledHeight <= boxSize.height) {
                        return Offset(offset.x, 0f)
                    }

                    // 최대 이동 가능 거리 계산
                    val maxX = (scaledWidth - boxSize.width) / 2
                    val maxY = (scaledHeight - boxSize.height) / 2

                    return Offset(
                        x = offset.x.coerceIn(-maxX, maxX),
                        y = offset.y.coerceIn(-maxY, maxY),
                    )
                }

                AsyncImage(
                    model =
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                    contentDescription = contentDescription,
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .onSizeChanged { boxSize = it },
                    contentScale = ContentScale.Fit,
                    onSuccess = { state ->
                        imageSize =
                            IntSize(
                                state.painter.intrinsicSize.width
                                    .toInt(),
                                state.painter.intrinsicSize.height
                                    .toInt(),
                            )
                    },
                )

                // 닫기 버튼
                IconButton(
                    onClick = {
                        scale = 1f
                        offset = Offset.Zero
                        showFullscreen = false
                    },
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                    )
                }
            }
        }
    }
}
