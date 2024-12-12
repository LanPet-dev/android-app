package com.lanpet.core.common.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.ArrowLeft
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customColorScheme

@Composable
fun CommonNavigateUpButton(onNavigateUp: () -> Unit) =
    CommonIconButtonBox(
        content = {
            Image(
                imageVector = MyIconPack.ArrowLeft,
                contentDescription = "ic_arrow_left",
                modifier = Modifier.size(28.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.customColorScheme.defaultIconColor),
            )
        },
        onClick = { onNavigateUp() }
    )

@PreviewLightDark
@Composable
fun CommonNavigateUpButtonPreview() {
    LanPetAppTheme {
        CommonNavigateUpButton {}
    }
}
