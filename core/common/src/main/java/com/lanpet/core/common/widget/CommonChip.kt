package com.lanpet.core.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun CommonChip(
    title: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            Modifier
                .clip(
                    shape = CircleShape,
                ).background(color = GrayColor.Gray100)
                .padding(horizontal = 10.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            title,
            style = MaterialTheme.customTypography().body3RegularSingle.copy(color = GrayColor.Gray500),
        )
    }
}

@Composable
@PreviewLightDark
private fun CommonChipPreview() {
    LanPetAppTheme {
        CommonChip("Chip")
    }
}
