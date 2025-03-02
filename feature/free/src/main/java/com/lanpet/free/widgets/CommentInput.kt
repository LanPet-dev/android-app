package com.lanpet.free.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.Send
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.free.R

@Composable
fun CommentInput(
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
                    colorFilter = ColorFilter.tint(color = if (input.isEmpty()) GrayColor.Gray400 else PrimaryColor.PRIMARY),
                )
            }
        }
    }
}
