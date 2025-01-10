package com.lanpet.free.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.createdAtPostString
import com.lanpet.core.common.loremIpsum
import com.lanpet.core.common.myiconpack.Send
import com.lanpet.core.common.widget.CommonChip
import com.lanpet.core.common.widget.CommonNavigateUpButton
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.free.R
import com.lanpet.free.viewmodel.FreeBoardDetailState
import com.lanpet.free.viewmodel.FreeBoardDetailViewModel
import com.lanpet.free.widgets.FreeBoardCommentItem
import com.lanpet.core.designsystem.R as DS_R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardDetailScreen(
    postId: String,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    freeBoardDetailViewModel: FreeBoardDetailViewModel = hiltViewModel<FreeBoardDetailViewModel>(),
) {
    LaunchedEffect(postId) {
        freeBoardDetailViewModel.init(postId)
    }

    val state = freeBoardDetailViewModel.uiState.collectAsState()

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
            when (state.value) {
                is FreeBoardDetailState.Loading -> {
                    LoadingUI()
                }

                is FreeBoardDetailState.Success -> {
                    ContentUI(state.value as FreeBoardDetailState.Success)
                }

                is FreeBoardDetailState.Error -> {
                    Text((state.value as FreeBoardDetailState.Error).message)
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
    modifier: Modifier = Modifier,
) {
    val verticalScrollState = rememberScrollState()

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
                state.postDetail.tags.map {
                    CommonChip(it)
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxSmall))
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                state.postDetail.petCategory.let {
                    Text(
                        it.value,
                        style = MaterialTheme.customTypography().body3RegularSingle,
                    )
                }
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
                model = it,
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
                error = painterResource(id = DS_R.drawable.img_animals),
            )
        }

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
            comments = state.comments,
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
        CommentInputSection()
        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}

// TODO: Comment section UI
@Composable
fun FreeBoardCommentSection(
    modifier: Modifier = Modifier,
    comments: List<FreeBoardComment> = emptyList(),
) {
    Column {
        Text(
            "댓글 ${comments.size}",
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
        } else {
            Column {
                comments.forEach { comment ->
                    FreeBoardCommentItem(freeBoardComment = comment)
                }
            }
        }
    }
}

@Composable
fun CommentInputSection(modifier: Modifier = Modifier) {
    var input by rememberSaveable { mutableStateOf("") }

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
                    input = it
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
                onClick = {},
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
    val freeBoardComment1 =
        FreeBoardComment(
            id = 1,
            content = loremIpsum().slice(0..100),
            writer = "writer",
            writerImage = null,
            createdAt = "2021-01-01T00:00:00Z",
            updatedAt = "2021-01-01",
            freeBoardId = 1,
            likeCount = 1,
            commentCount = 3,
            subComments =
                listOf(
                    FreeBoardComment(
                        id = 1,
                        content = loremIpsum().slice(0..200),
                        writer = "writer",
                        writerImage = null,
                        createdAt = "2021-01-01T00:00:00Z",
                        updatedAt = "2021-01-01",
                        freeBoardId = 1,
                        likeCount = 1,
                        commentCount = null,
                        subComments = emptyList(),
                    ),
                    FreeBoardComment(
                        id = 1,
                        content = loremIpsum().slice(0..50),
                        writer = "writer",
                        writerImage = null,
                        createdAt = "2021-01-01T00:00:00Z",
                        updatedAt = "2021-01-01",
                        freeBoardId = 1,
                        likeCount = 1,
                        commentCount = null,
                        subComments = emptyList(),
                    ),
                    FreeBoardComment(
                        id = 1,
                        content = loremIpsum(),
                        writer = "writer",
                        writerImage = null,
                        createdAt = "2021-01-01T00:00:00Z",
                        updatedAt = "2021-01-01",
                        freeBoardId = 1,
                        likeCount = 1,
                        commentCount = null,
                        subComments = emptyList(),
                    ),
                ),
        )

    val freeBoardComment2 =
        FreeBoardComment(
            id = 1,
            content = loremIpsum().slice(0..100),
            writer = "writer",
            writerImage = null,
            createdAt = "2021-01-01T00:00:00Z",
            updatedAt = "2021-01-01",
            freeBoardId = 1,
            likeCount = 1,
            commentCount = null,
            subComments = emptyList(),
        )

    LanPetAppTheme {
        FreeBoardDetailScreen(
            postId = "1",
            onNavigateUp = {},
        )
    }
}
