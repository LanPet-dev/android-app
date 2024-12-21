package com.lanpet.core.common.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme

class SelectableChip {
    companion object {
        @Composable
        fun Circle(
            isSelected: Boolean,
            title: String,
            modifier: Modifier = Modifier,
            shape: Shape = CircleShape,
            onSelectedValueChange: (Boolean) -> Unit = {},
        ) {
            SelectableChip(
                isSelected = isSelected,
                title = title,
                modifier = modifier,
                shape = shape,
                onSelectedValueChange = onSelectedValueChange,
            )
        }

        @Composable
        fun Rounded(
            isSelected: Boolean,
            title: String,
            modifier: Modifier = Modifier,
            shape: Shape =
                RoundedCornerShape(
                    LanPetDimensions.Corner.xSmall,
                ),
            onSelectedValueChange: (Boolean) -> Unit = {},
        ) {
            SelectableChip(
                isSelected = isSelected,
                title = title,
                modifier = modifier,
                shape = shape,
                onSelectedValueChange = onSelectedValueChange,
            )
        }

        @Composable
        fun Normal(
            isSelected: Boolean,
            title: String,
            modifier: Modifier = Modifier,
            onSelectedValueChange: (Boolean) -> Unit = {},
        ) {
            SelectableChip(
                isSelected = isSelected,
                title = title,
                modifier = modifier,
                onSelectedValueChange = onSelectedValueChange,
            )
        }
    }
}

@Composable
fun SelectableChip(
    isSelected: Boolean,
    title: String,
    modifier: Modifier = Modifier,
    shape: Shape =
        RoundedCornerShape(
            LanPetDimensions.Corner.small,
        ),
    onSelectedValueChange: (Boolean) -> Unit = {},
) {
    Box(
        modifier
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .background(
                color =
                    if (isSelected) {
                        MaterialTheme.customColorScheme.selectedContainer
                    } else {
                        MaterialTheme.customColorScheme.unSelectedContainer
                    },
                shape = shape,
            ).border(
                border =
                    BorderStroke(
                        1.dp,
                        if (isSelected) {
                            MaterialTheme.customColorScheme.selectedContainer
                        } else {
                            GrayColor.LIGHT
                        },
                    ),
                shape = shape,
            ).clip(
                shape,
            ).clickable(
                onClick = {
                    onSelectedValueChange(!isSelected)
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
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewSelectableChip() {
    Column {
        SelectableChip(true, "title") { it }
        SelectableChip(false, "title") { it }
        SelectableChip.Circle(true, "title") { it }
        SelectableChip.Circle(false, "title") { it }
        SelectableChip.Rounded(true, "title") { it }
        SelectableChip.Rounded(false, "title") { it }
        SelectableChip.Normal(true, "title") { it }
        SelectableChip.Normal(false, "title") { it }
    }
}
