package com.lanpet.core.common.widget

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.Like
import com.lanpet.core.common.myiconpack.Message
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun FreeBoardListItem() {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LanPetDimensions.Spacing.small)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = LanPetDimensions.Spacing.xSmall)
            ) {
                CommonChip("추천해요")
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                Text("강아지", style = MaterialTheme.customTypography().body3RegularSingle)
            }
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Row(
                Modifier.padding(horizontal = LanPetDimensions.Spacing.small)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Title", style = MaterialTheme.customTypography().body2RegularSingle)
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                    Text(
                        "haweoifhioawehfioawheiofhiaowehfiohaweiofhioawheifahwoefhawehfohawioefhoawheiofhioawefihiawoehfioawheiofhawiefhiohwaefoi",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.customTypography().body3RegularMulti.copy(
                            color = GrayColor.Gray500
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
                Image(
                    painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_dummy),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(LanPetDimensions.Corner.xxSmall)
                        )
                        .size(66.dp)
                )
            }
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Row(
                Modifier.padding(horizontal = LanPetDimensions.Spacing.small)
            ) {
                Text(
                    "2일전",
                    style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray300),
                    modifier = Modifier.weight(1f)
                )
                Row {
                    Image(
                        imageVector = MyIconPack.Like,
                        contentDescription = "ic_like",
                        modifier = Modifier.size(12.dp),
                        colorFilter = ColorFilter.tint(GrayColor.Gray400)
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxxSmall))
                    Text(
                        "10",
                        style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray400)
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                    Image(
                        imageVector = MyIconPack.Message,
                        contentDescription = "ic_message",
                        modifier = Modifier.size(12.dp),
                        colorFilter = ColorFilter.tint(GrayColor.Gray400)
                    )
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxxxSmall))
                    Text(
                        "10",
                        style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray400)
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun FreeBoardListItemPreview() {
    LanPetAppTheme {
        FreeBoardListItem()
    }
}