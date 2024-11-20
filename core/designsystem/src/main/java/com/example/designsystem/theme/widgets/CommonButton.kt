package com.example.designsystem.theme.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.LanPetAppTheme
import com.example.designsystem.theme.LanPetDimensions
import com.example.designsystem.theme.customColorScheme

@Composable
fun CommonButton(modifier: Modifier = Modifier, title: String, onClick: (() -> Unit)?) {
    Button(
        shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
        onClick = {
            onClick?.invoke()
        },
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.customColorScheme.buttonBackground
        ),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .sizeIn(minHeight = 54.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@PreviewLightDark
@Composable()
fun PreviewCommonButton() {
    LanPetAppTheme {
        CommonButton(
            title = "This is title",
            modifier = Modifier,
            onClick = {}
        )
    }
}