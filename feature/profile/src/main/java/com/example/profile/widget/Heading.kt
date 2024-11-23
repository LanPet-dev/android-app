package com.example.profile.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.GrayColor
import com.example.designsystem.theme.LanPetAppTheme
import com.example.designsystem.theme.customColorScheme

@Composable
fun Heading(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        title,
        style =
            MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.customColorScheme.heading,
                fontWeight = FontWeight.Bold,
            ),
        modifier = modifier,
    )
}

@Composable
fun HeadingHint(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        title,
        style =
            MaterialTheme.typography.titleSmall.copy(
                color =
                    GrayColor.LIGHT_MEDIUM,
            ),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHeading() {
    LanPetAppTheme {
        Column {
            Heading(title = "This is Heading")
            Heading(title = "이것은 헤딩입니다.")
            HeadingHint(title = "This is Heading hint.")
            HeadingHint(title = "이것은 헤딩 힌트입니다.")
        }
    }
}
