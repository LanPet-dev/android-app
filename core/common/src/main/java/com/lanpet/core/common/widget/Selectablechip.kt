package com.lanpet.core.common.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.crop
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme

@Composable
fun SelectableChip(
    isSelected: Boolean,
    title: String,
    onSelectValueChanged: (Boolean) -> Unit,
) {
    Box(
        Modifier
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .background(
                color = if (isSelected) MaterialTheme.customColorScheme.selectedContainer else MaterialTheme.customColorScheme.unSelectedContainer,
                shape =
                RoundedCornerShape(
                    LanPetDimensions.Corner.small,
                ),
            )
            .border(
                border =
                BorderStroke(
                    1.dp,
                    if (isSelected) MaterialTheme.customColorScheme.selectedContainer else GrayColor.LIGHT,
                ),
                shape =
                RoundedCornerShape(
                    LanPetDimensions.Corner.small,
                ),
            )
            .clip(
                RoundedCornerShape(
                    LanPetDimensions.Corner.small,
                ),
            )
            .clickable(
                onClick = {
                    onSelectValueChanged(!isSelected)
                },
            ),
        Alignment.Center,
    ) {
        Text(
            text = title,
            style =
            MaterialTheme.typography.labelLarge.copy(
                color =
                if (isSelected) {
                    MaterialTheme.customColorScheme.selectedText
                } else {
                    MaterialTheme.customColorScheme.unSelectedText
                },
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
        )
    }
}

@PreviewLightDark
@Composable
fun PreviewSelectableChip() {
    Column {
        SelectableChip(true, "title") { it }
        SelectableChip(false, "title") { it }
    }
}
