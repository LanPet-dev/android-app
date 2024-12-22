package com.lanpet.core.common.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun CommonHeading(
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
fun CommonHeadingHint(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        title,
        style =
            MaterialTheme.typography.titleSmall.copy(
                color =
                    GrayColor.Gray500,
            ),
    )
}

@Composable
fun CommonSubHeading1(
    title: String,
    modifier: Modifier = Modifier,
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
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        title,
        modifier = modifier,
        style =
            MaterialTheme.customTypography().title3SemiBoldSingle,
    )
}

@Composable
fun CommonHint(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        title,
        style =
            MaterialTheme.customTypography().body3RegularSingle.copy(
                color =
                    GrayColor.Gray400,
            ),
        modifier = modifier,
    )
}

@Composable
fun CommonCenterAlignedAppBarTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        title,
        style =
            MaterialTheme.customTypography().title3SemiBoldMulti,
        modifier = modifier,
    )
}

@Composable
@PreviewLightDark
private fun CommonHeadingPreview() {
    LanPetAppTheme {
        Surface {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                CommonHeading(title = "Heading")
                Spacer(modifier = Modifier.height(8.dp))
                CommonHeadingHint(title = "Heading Hint")
                Spacer(modifier = Modifier.height(8.dp))
                CommonSubHeading1(title = "Sub Heading 1")
                Spacer(modifier = Modifier.height(8.dp))
                CommonAppBarTitle(title = "App Bar Title")
                Spacer(modifier = Modifier.height(8.dp))
                CommonCenterAlignedAppBarTitle(title = "Center Aligned App Bar Title")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
