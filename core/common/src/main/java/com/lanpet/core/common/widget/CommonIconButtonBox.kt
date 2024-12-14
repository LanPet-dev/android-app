package com.lanpet.core.common.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.crop

@Composable
fun CommonIconButtonBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier =
            Modifier.crop(48.dp) {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
