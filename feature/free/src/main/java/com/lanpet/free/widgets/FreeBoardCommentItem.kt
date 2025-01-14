package com.lanpet.free.widgets

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.Like
import com.lanpet.core.designsystem.R
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.Profile
import com.lanpet.domain.model.free.FreeBoardComment

@Composable
fun FreeBoardCommentItem(
    freeBoardComment: FreeBoardComment,
    modifier: Modifier = Modifier,
    isSubComment: Boolean = false,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Row(
                modifier = Modifier.padding(LanPetDimensions.Spacing.small),
            ) {
                if (isSubComment) {
                    Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xLarge))
                }
                if (freeBoardComment.profile.profileImage != null) {
                    AsyncImage(
                        freeBoardComment.profile.profileImage,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clip(shape = CircleShape),
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.img_dummy),
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clip(shape = CircleShape),
                    )
                }
                Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xSmall))
                Column(
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        freeBoardComment.profile.nickname,
                        style = MaterialTheme.customTypography().body3RegularSingle,
                    )
                    Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xxxSmall))
//                    Text(
//                        createdAtPostString(freeBoardComment.createdAt),
//                        style = MaterialTheme.customTypography().sub1MediumSingle.copy(color = GrayColor.Gray300),
//                    )
//                    Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xxSmall))
                    Text(
                        freeBoardComment.comment.toString(),
                        style = MaterialTheme.customTypography().body2RegularMulti,
                    )
                    Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xSmall))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
//                        Image(
//                            imageVector = MyIconPack.Like,
//                            contentDescription = "ic_like",
//                            modifier = Modifier.size(12.dp),
//                            colorFilter = ColorFilter.tint(color = GrayColor.Gray400),
//                        )
//                        Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xxxSmall))
//                        Text(
//                            "좋아요",
//                            style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray400),
//                        )
//                        Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xxSmall))
//                        Text(
//                            freeBoardComment.likeCount.toString(),
//                            style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray400),
//                        )
//
//                        Spacer(modifier = Modifier.size(8.dp))
//                        if (freeBoardComment.commentCount != null) {
//                            Image(
//                                imageVector = MyIconPack.Message,
//                                contentDescription = "ic_message",
//                                modifier = Modifier.size(12.dp),
//                                colorFilter = ColorFilter.tint(color = GrayColor.Gray400),
//                            )
//                            Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xxxSmall))
//                            Text(
//                                "답글",
//                                style =
//                                    MaterialTheme.customTypography().body3RegularSingle.copy(
//                                        color = GrayColor.Gray400,
//                                    ),
//                            )
//
//                            Spacer(modifier = Modifier.size(LanPetDimensions.Spacing.xxSmall))
//                            Text(
//                                freeBoardComment.commentCount.toString(),
//                                style =
//                                    MaterialTheme.customTypography().body3RegularSingle.copy(
//                                        color = GrayColor.Gray400,
//                                    ),
//                            )
//                        }
                    }
                }
            }

//            if (freeBoardComment.commentCount != null && freeBoardComment.subComments.isNotEmpty()) {
//                Column {
//                    freeBoardComment.subComments.map { comment ->
//                        FreeBoardCommentItem(
//                            freeBoardComment = comment,
//                            isSubComment = true,
//                        )
//                    }
//                }
//            }
        }
    }
}

@Preview(heightDp = 1400)
@Preview(heightDp = 1400, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FreeBoardCommentItemPreview() {
    LanPetAppTheme {
        Column {
           FreeBoardCommentItem(
               FreeBoardComment(
                   id = "1",
                   profile =
                   Profile(
                       nickname = "닉네임",
                       profileImage = null,
                   ),
                   comment = "This is comment",
               )
           )
        }
    }
}
