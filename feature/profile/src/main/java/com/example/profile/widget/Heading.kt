package com.example.profile.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.BlackColor

@Composable
fun Heading(modifier: Modifier = Modifier, title: String) {
    Text(title, style = MaterialTheme.typography.titleLarge.copy(color = BlackColor.MEDIUM))
}

@Preview(showBackground = true)
@Composable
fun PreviewHeading() {
    Column {
        Heading(title = "This is Heading")
        Heading(title = "이것은 헤딩입니다.")
    }
}