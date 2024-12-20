package com.lanpet.profile.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customColorScheme

@Composable
fun Heading(
    title: String,
    modifier: Modifier = Modifier,
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
    title: String,
    modifier: Modifier = Modifier,
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
private fun PreviewHeading() {
    LanPetAppTheme {
        Column {
            Heading(title = "This is Heading")
            Heading(title = "이것은 헤딩입니다.")
            HeadingHint(title = "This is Heading hint.")
            HeadingHint(title = "이것은 헤딩 힌트입니다.")
        }
    }
}
