package com.example.designsystem.theme.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.BlackColor
import com.example.designsystem.theme.WhiteColor
import com.example.designsystem.theme.cornerRadiusLight
import com.example.designsystem.theme.marginLight
import com.example.designsystem.theme.marginMedium

@Composable
fun CommonButton(modifier: Modifier = Modifier, onClick: (() -> Unit)?, title: String) {
    Button(
        shape = RoundedCornerShape(cornerRadiusLight),
        onClick = {
            onClick?.invoke()
        },
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = BlackColor.MEDIUM
        ),
        modifier = modifier.fillMaxWidth().wrapContentHeight()
            .padding(vertical = marginLight, horizontal = marginMedium)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium.copy(color = WhiteColor.LIGHT)
        )
    }
}

@Preview
@Composable()
fun PreviewCommonButton() {
    CommonButton(
        title = "This is title",
        modifier = Modifier,
        onClick = {}
    )
}