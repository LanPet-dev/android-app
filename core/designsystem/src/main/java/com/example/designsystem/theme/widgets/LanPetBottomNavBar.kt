package com.example.designsystem.theme.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.LanPetAppTheme

@Composable
fun LanPetBottomNavBar(
    bottomNavItemList: List<BottomNavItem>,
    selectedBottomNavItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .safeDrawingPadding()
            .sizeIn(minHeight = 64.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        bottomNavItemList.forEach { bottomNavItem ->
            LanPetBottomNavItem(
                isSelected = bottomNavItem == selectedBottomNavItem,
                bottomNavItem = bottomNavItem,
                onClick = {
                    onItemSelected(bottomNavItem)
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun LanPetBottomNavBarPreview() {
    LanPetAppTheme {
        LanPetBottomNavBar(
            bottomNavItemList = listOf(
                BottomNavItem.Wiki,
                BottomNavItem.Free,
                BottomNavItem.MyPage,
            ),
            selectedBottomNavItem = BottomNavItem.Wiki,
            onItemSelected = {}
        )
    }
}
