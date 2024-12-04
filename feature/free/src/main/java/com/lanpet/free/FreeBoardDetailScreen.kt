package com.lanpet.free

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.model.FreeBoardComment
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.loremIpsum
import com.lanpet.core.common.myiconpack.My
import com.lanpet.core.common.myiconpack.Send
import com.lanpet.core.common.widget.CommonChip
import com.lanpet.core.common.widget.CommonNavigateUpButton
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.R
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.WhiteColor
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.free.widgets.FreeBoardCommentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardDetailScreen(
    postId: Int,
    onNavigateUp: () -> Unit,
) {
    val verticalScrollState = rememberScrollState()

    //TODO: Fetch Post Detail & Comment list

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                navigationIcon = {
                    CommonNavigateUpButton {
                        onNavigateUp()
                    }
                },
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(
                        state = verticalScrollState,
                    )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.small)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CommonChip("추천해요")
                        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                        Text("강아지", style = MaterialTheme.customTypography().body3RegularSingle)
                    }
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))

                    Text("Head Title", style = MaterialTheme.customTypography().title3SemiBoldMulti)
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_bottom_nav_mypage_unselected),
                            contentDescription = "ic_profile",
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxSmall))
                        Text(
                            "Nickname",
                            style = MaterialTheme.customTypography().body3RegularSingle
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            "2일전", style = MaterialTheme.customTypography().body2RegularSingle.copy(
                                color = GrayColor.Gray300
                            )
                        )
                    }

                }

                //line
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LanPetDimensions.Spacing.medium)
                        .size(1.dp)
                        .background(GrayColor.Gray50)
                )

                Text(
                    loremIpsum(),
                    style = MaterialTheme.customTypography().body2RegularMulti,
                    modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.small)
                )

                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                Image(
                    painter = painterResource(id = R.drawable.img_dummy),
                    contentDescription = "Profile Image",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )

                //line
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LanPetDimensions.Spacing.medium)
                        .size(4.dp)
                        .background(GrayColor.Gray50)
                )
                FreeBoardCommentSection(
                    listOf(
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
                            subComments = listOf(
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
                                    subComments = emptyList()
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
                                    subComments = emptyList()
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
                                    subComments = emptyList()
                                ),
                                FreeBoardComment(
                                    id = 1,
                                    content = loremIpsum().slice(0..100),
                                    writer = "writer",
                                    writerImage = null,
                                    createdAt = "2021-01-01T00:00:00Z",
                                    updatedAt = "2021-01-01",
                                    freeBoardId = 1,
                                    likeCount = 0,
                                    commentCount = null,
                                )
                            ),
                            ),
                        FreeBoardComment(
                            id = 1,
                            content = loremIpsum().slice(0..100),
                            writer = "writer",
                            writerImage = null,
                            createdAt = "2021-01-01T00:00:00Z",
                            updatedAt = "2021-01-01",
                            freeBoardId = 1,
                            likeCount = 0,
                            commentCount = null,
                        ),
                        FreeBoardComment(
                            id = 1,
                            content = loremIpsum().slice(0..100),
                            writer = "writer",
                            writerImage = null,
                            createdAt = "2021-01-01T00:00:00Z",
                            updatedAt = "2021-01-01",
                            freeBoardId = 1,
                            likeCount = 0,
                            commentCount = null,
                        )
                    )
                )
                //line
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = LanPetDimensions.Spacing.medium)
                        .size(4.dp)
                        .background(GrayColor.Gray50)
                )
                Spacer(modifier = Modifier.weight(1f))
                CommentInputSection()
                Spacer(modifier = Modifier.navigationBarsPadding())
            }

        }
    }
}

//TODO: Comment section UI
@Composable
fun FreeBoardCommentSection(
    comments: List<FreeBoardComment> = emptyList()
) {
    Text(
        "댓글 ${comments.size}",
        style = MaterialTheme.customTypography().body2RegularMulti,
        modifier = Modifier.padding(
            horizontal = LanPetDimensions.Spacing.small,
            vertical = LanPetDimensions.Spacing.small
        )
    )
    if (comments.isEmpty()) {
        Text(
            stringResource(R.string.body_freeboard_no_comment),
            style = MaterialTheme.customTypography().body2RegularSingle.copy(
                color = GrayColor.Gray400
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        Column {
            comments.forEach { comment ->
                FreeBoardCommentItem(freeBoardComment = comment)
            }
        }
    }
}

@Composable
fun CommentInputSection() {
    var input by rememberSaveable() { mutableStateOf("") }
    var showEmojiPicker by remember { mutableStateOf(false) }

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(1.dp)
                .background(GrayColor.Gray50)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LanPetDimensions.Spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = input,
                maxLines = 1,
                onValueChange = {
                    input = it
                },
                placeholder = { Text("답글을 입력해주세요") },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = LanPetDimensions.Spacing.small)
                    .clip(shape = CircleShape),
                textStyle = MaterialTheme.customTypography().body2RegularSingle.copy(
                    color = GrayColor.Gray500
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = WhiteColor.MEDIUM,
                    unfocusedContainerColor = WhiteColor.MEDIUM,
                    unfocusedPlaceholderColor = GrayColor.Gray400,
                    focusedPlaceholderColor = GrayColor.Gray400,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = GrayColor.Gray400,
                ),
                trailingIcon = {
                    Image(
                        imageVector = MyIconPack.My,
                        contentDescription = "ic_my",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(color = GrayColor.Gray400)
                    )
                },
            )
            IconButton(
                onClick = {}
            ) {
                Image(
                    imageVector = MyIconPack.Send,
                    contentDescription = "ic_send",
                    colorFilter = ColorFilter.tint(color = GrayColor.Gray400),
                )
            }

//            AnimatedVisibility(
//                visible = showEmojiPicker,
//                enter = slideInVertically(initialOffsetY = { it }),
//                exit = slideOutVertically(targetOffsetY = { it })
//            ) {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(240.dp),
//                    shadowElevation = 8.dp
//                ) {
//                    EmojiPicker(
//                        onEmojiSelected = { emoji ->
//                            input += emoji
//                            showEmojiPicker = false
//                        },
//                        onDismiss = { showEmojiPicker = false }
//                    )
//                }
//            }
        }
    }
}

@Composable
private fun EmojiPicker(
    onEmojiSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val emojis = listOf("😀", "😍", "🥰", "😎", "🤔", "😊", "🎉", "👍", "❤️", "🌟")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LanPetDimensions.Spacing.small)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
            verticalArrangement = Arrangement.spacedBy(LanPetDimensions.Spacing.small)
        ) {
            items(emojis) { emoji ->
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onEmojiSelected(emoji) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        emoji,
                        style = MaterialTheme.customTypography().body1RegularSingle
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewCommentInputSection() {
    LanPetAppTheme {
        CommentInputSection()
    }
}


@Preview(heightDp = 2000)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 2000)
@Composable
fun FreeBoardDetailPreview() {

    val freeBoardComment1 = FreeBoardComment(
        id = 1,
        content = loremIpsum().slice(0..100),
        writer = "writer",
        writerImage = null,
        createdAt = "2021-01-01T00:00:00Z",
        updatedAt = "2021-01-01",
        freeBoardId = 1,
        likeCount = 1,
        commentCount = 3,
        subComments = listOf(
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
                subComments = emptyList()
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
                subComments = emptyList()
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
                subComments = emptyList()
            )
        )
    )

    val freeBoardComment2 = FreeBoardComment(
        id = 1,
        content = loremIpsum().slice(0..100),
        writer = "writer",
        writerImage = null,
        createdAt = "2021-01-01T00:00:00Z",
        updatedAt = "2021-01-01",
        freeBoardId = 1,
        likeCount = 1,
        commentCount = null,
        subComments = emptyList()
    )

    LanPetAppTheme {
        FreeBoardDetailScreen(
            postId = 1,
            onNavigateUp = {},
        )
    }
}