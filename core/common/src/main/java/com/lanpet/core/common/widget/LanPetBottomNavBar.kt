package com.lanpet.core.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customColorScheme

@Composable
fun LanPetBottomNavBar(
    bottomNavItemList: List<BottomNavItem>,
    selectedBottomNavItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit,
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(
                    color = MaterialTheme.customColorScheme.spacerLine
                ),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
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
}

@Composable
fun rememberBottomNavItem() = remember {
    mutableStateOf(BottomNavItem.Initial)
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
