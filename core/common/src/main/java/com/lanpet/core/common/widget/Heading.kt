package com.lanpet.core.common.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography


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

@Composable
fun CommonSubHeading1(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        title,
        style =
        MaterialTheme.customTypography().body1SemiBoldSingle,
        modifier = modifier,
    )
}

@Composable
fun CommonAppBarTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        title,
        modifier = modifier,
    )
}