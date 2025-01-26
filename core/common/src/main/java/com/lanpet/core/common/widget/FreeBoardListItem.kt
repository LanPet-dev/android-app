package com.lanpet.core.common.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.createdAtPostString
import com.lanpet.core.common.myiconpack.Like
import com.lanpet.core.common.myiconpack.Message
import com.lanpet.core.designsystem.R
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.core.manager.CoilManager
import com.lanpet.core.manager.LocalCoilManager
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.domain.model.free.FreeBoardItem
import com.lanpet.domain.model.free.FreeBoardStat
import com.lanpet.domain.model.free.FreeBoardText

@Composable
fun FreeBoardListItem(
    freeBoardPostItem: FreeBoardItem,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = LocalCoilManager.current.memoryCacheImageLoader,
    onClick: () -> Unit = {},
) {
    Surface {
        Column(
            modifier =
                Modifier
                    .clickable {
                        onClick()
                    }.fillMaxWidth()
                    .padding(vertical = LanPetDimensions.Spacing.small),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xSmall),
            ) {
                CommonChip(freeBoardPostItem.category.title)
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxSmall))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                Text(
                    freeBoardPostItem.petType.value,
                    style = MaterialTheme.customTypography().body3RegularSingle,
                )
            }
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Row(
                Modifier.padding(horizontal = LanPetDimensions.Spacing.small),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        freeBoardPostItem.text.title,
                        style = MaterialTheme.customTypography().body2RegularSingle,
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                    Text(
                        freeBoardPostItem.text.content,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.customTypography().body3RegularMulti,
                    )
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
                if (freeBoardPostItem.resources?.isNotEmpty() == true) {
                    AsyncImage(
                        model = freeBoardPostItem.resources!!.first().url,
                        contentDescription = "post_image",
                        contentScale = ContentScale.Crop,
                        imageLoader = imageLoader,
                        modifier =
                            Modifier
                                .clip(
                                    shape = RoundedCornerShape(LanPetDimensions.Corner.xxSmall),
                                ).size(66.dp),
                        error = painterResource(id = R.drawable.img_animals),
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.img_animals),
                        contentDescription = "ic_animals",
                        modifier = Modifier.size(66.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Row(
                Modifier.padding(horizontal = LanPetDimensions.Spacing.small),
            ) {
                Text(
                    // TODO
                    createdAtPostString(freeBoardPostItem.created),
                    style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray300),
                    modifier = Modifier.weight(1f),
                )
                Row {
                    Image(
                        imageVector = MyIconPack.Like,
                        contentDescription = "ic_like",
                        modifier = Modifier.size(12.dp),
                        colorFilter = ColorFilter.tint(GrayColor.Gray400),
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxxSmall))
                    Text(
                        (freeBoardPostItem.stat?.likeCount ?: 0).toString(),
                        style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray400),
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                    Image(
                        imageVector = MyIconPack.Message,
                        contentDescription = "ic_message",
                        modifier = Modifier.size(12.dp),
                        colorFilter = ColorFilter.tint(GrayColor.Gray400),
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxxSmall))
                    Text(
                        (freeBoardPostItem.stat?.commentCount ?: 0).toString(),
                        style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray400),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun FreeBoardListItemPreview() {
    val context = LocalContext.current

    CompositionLocalProvider(LocalCoilManager provides CoilManager(context)) {
        LanPetAppTheme {
            Column {
                FreeBoardListItem(
                    freeBoardPostItem =
                        FreeBoardItem(
                            id = "1",
                            category = FreeBoardCategoryType.CURIOUS,
                            petType = PetCategory.PARROT,
                            text =
                                FreeBoardText(
                                    title = "title",
                                    content = "content",
                                ),
                            stat =
                                FreeBoardStat(
                                    likeCount = 1,
                                    commentCount = 1,
                                ),
                            resources = emptyList(),
                            created = "2021-10-27T17:13:40.000+00:00",
                        ),
                )
            }
        }
    }
}
