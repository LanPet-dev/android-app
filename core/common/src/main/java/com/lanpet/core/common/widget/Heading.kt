package com.lanpet.core.common.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.customColorScheme

@Composable
fun CommonHeading(
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
fun CommonHeadingHint(
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
