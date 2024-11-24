package com.example.designsystem.theme.widgets

import LanPetBottomNavItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.LanPetAppTheme

@Composable
fun LanPetBottomNavBar(
    bottomNavItemList: List<BottomNavItem>,
    selectedBottomNavItem: BottomNavItem,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 64.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        bottomNavItemList.forEach { bottomNavItem ->
            LanPetBottomNavItem(
                isSelected = bottomNavItem == selectedBottomNavItem,
                bottomNavItem = bottomNavItem,
                onClick = { /* TODO: Implement onClick */ }
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
        )
    }
}
