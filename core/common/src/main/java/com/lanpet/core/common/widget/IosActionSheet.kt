package com.lanpet.core.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.WhiteColor

@Composable
fun IOSActionSheet(
    cancelButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White),
        ) {
            content()
        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White),
        ) {
            cancelButton()
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors =
        ButtonColors(
            contentColor = GrayColor.Gray950,
            containerColor = WhiteColor.White,
            disabledContainerColor = GrayColor.Gray950,
            disabledContentColor = WhiteColor.White,
        ),
    onClick: () -> Unit = {},
) {
    Surface {
        TextButton(
            shape = RoundedCornerShape(0.dp),
            onClick = onClick,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(64.dp),
            colors = buttonColors,
        ) {
            Text(text, fontSize = 20.sp)
        }
    }
}

@Composable
@PreviewLightDark
private fun IOSActionSheetPreview() {
    LanPetAppTheme {
        IOSActionSheet(
            content = {
                Column {
                    ActionButton(text = "사진 촬영", onClick = { /* */ })
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ActionButton(text = "사진 앨범", onClick = { /* */ })
                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    ActionButton(
                        text = "버튼",
                        buttonColors =
                            ButtonColors(
                                contentColor = Color.Red,
                                containerColor = WhiteColor.White,
                                disabledContainerColor = GrayColor.Gray950,
                                disabledContentColor = WhiteColor.White,
                            ),
                        onClick = { /* */ },
                    )
                }
            },
            cancelButton = {
                ActionButton(text = "닫기", onClick = { /* */ })
            },
        )
    }
}
