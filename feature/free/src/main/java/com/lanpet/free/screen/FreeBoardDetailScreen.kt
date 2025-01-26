package com.lanpet.free.screen

import android.content.res.Configuration
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lanpet.core.auth.BasePreviewWrapper
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.createdAtPostString
import com.lanpet.core.common.myiconpack.Send
import com.lanpet.core.common.toast
import com.lanpet.core.common.widget.CommonChip
import com.lanpet.core.common.widget.CommonNavigateUpButton
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
import com.lanpet.free.widgets.FreeBoardCommentItem
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardDetailScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    freeBoardDetailViewModel: FreeBoardDetailViewModel = hiltViewModel<FreeBoardDetailViewModel>(),
) {
    val state = freeBoardDetailViewModel.uiState.collectAsState()
    val context = LocalContext.current

    when (state.value) {
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

            val isOwner =
                (state.value as FreeBoardDetailState.Success).postDetail.writer == defaultProfile.nickname

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

            Scaffold(
                topBar = {
                    LanPetTopAppBar(
                        navigationIcon = {
                            CommonNavigateUpButton {
                                onNavigateUp()
                            }
                        },
                        actions = if(isOwner) {
                            {
                                IconButton(onClick = {
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "ic_MoreVert",
                                    )
                                }
                            }
                        } else {
                            {}
                        }
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
                        state.value as FreeBoardDetailState.Success,
                        isOwner = isOwner,
                        commentInput = input,
                        onInputValueChange = { value ->
                            input = value
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
fun LoadingUI(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier =
                Modifier
                    .size(36.dp),
        )
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
    isOwner: Boolean = false,
    freeBoardLikesViewModel: FreeBoardLikesViewModel = hiltViewModel(),
) {
    val verticalScrollState = rememberScrollState()

    val rememberOnLikeChange = remember { onLikeChange }

    val context = LocalContext.current

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
            AsyncImage(
                model = it.url,
                contentDescription = "post_image",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = LanPetDimensions.Spacing.small,
                            vertical = LanPetDimensions.Spacing.small,
                        ).clip(
                            shape =
                                RoundedCornerShape(
                                    LanPetDimensions.Corner.medium,
                                ),
                        ),
                error = painterResource(id = DS_R.drawable.img_preparing),
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
            commentCount = state.postDetail.commentCount,
            comments = state.comments,
            canLoadMore = state.canLoadMoreComments,
            onLoadMore = {
                onFetchComment()
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
        Spacer(modifier = Modifier.weight(1f))
        CommentInputSection(
            onWriteComment = onWriteComment,
            input = commentInput,
            onInputValueChange = onInputValueChange,
        )
        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}

@Composable
fun FreeBoardCommentSection(
    canLoadMore: Boolean,
    modifier: Modifier = Modifier,
    commentCount: Int = 0,
    comments: List<FreeBoardComment> = emptyList(),
    onLoadMore: () -> Unit = {},
) {
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
                comments.forEach { comment ->
                    FreeBoardCommentItem(freeBoardComment = comment)
                }
                if (canLoadMore) {
                    Text(
                        "댓글 더보기",
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
fun CommentInputSection(
    modifier: Modifier = Modifier,
    input: String = "",
    onInputValueChange: (String) -> Unit = {},
    onWriteComment: () -> Unit = {},
) {
    Column {
        Spacer(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .size(1.dp)
                    .background(GrayColor.Gray50),
        )
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(LanPetDimensions.Spacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = input,
                maxLines = 4,
                onValueChange = {
                    onInputValueChange(it)
                },
                placeholder = { Text(stringResource(R.string.placeholder_textfield_enter_reply_freeboard_detail)) },
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = LanPetDimensions.Spacing.small)
                        .clip(
                            shape =
                                RoundedCornerShape(
                                    LanPetDimensions.Corner.medium,
                                ),
                        ),
                textStyle =
                    MaterialTheme.customTypography().body2RegularSingle.copy(
                        color = GrayColor.Gray400,
                    ),
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = GrayColor.Gray100,
                        unfocusedContainerColor = GrayColor.Gray100,
                        unfocusedPlaceholderColor = GrayColor.Gray400,
                        focusedPlaceholderColor = GrayColor.Gray400,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = GrayColor.Gray400,
                    ),
            )
            IconButton(
                onClick = {
                    onWriteComment()
                },
            ) {
                Image(
                    imageVector = MyIconPack.Send,
                    contentDescription = "ic_send",
                    colorFilter = ColorFilter.tint(color = GrayColor.Gray400),
                )
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
        CommentInputSection()
    }
}

@Preview(heightDp = 2000)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 2000)
@Composable
private fun FreeBoardDetailPreview() {
    LanPetAppTheme {
        FreeBoardDetailScreen(
            onNavigateUp = {},
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
