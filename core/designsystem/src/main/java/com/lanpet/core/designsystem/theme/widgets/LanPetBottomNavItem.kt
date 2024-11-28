package com.lanpet.core.designsystem.theme.widgets

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.R
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.customColorScheme

enum class BottomNavItem(val title: String, val selectedIcon: Int, val unselectedIcon: Int) {
    Wiki(
        title = "반려백과",
        selectedIcon = R.drawable.ic_bottom_nav_wiki_selected,
        unselectedIcon = R.drawable.ic_bottom_nav_wiki_unselected
    ),
    Free(
        title = "사랑방",
        selectedIcon = R.drawable.ic_bottom_nav_free_selected,
        unselectedIcon = R.drawable.ic_bottom_nav_free_unselected
    ),
    MyPage(
        title = "마이페이지",
        selectedIcon = R.drawable.ic_bottom_nav_mypage_selected,
        unselectedIcon = R.drawable.ic_bottom_nav_mypage_unselected
    )
}

@Composable
fun LanPetBottomNavItem(
    isSelected: Boolean,
    bottomNavItem: BottomNavItem,
    onClick: (BottomNavItem) -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.16f else 1f,
        // reference: https://developer.android.com/develop/ui/compose/animation/customize?hl=ko
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale animation"
    )

    Column(
        modifier = Modifier
            .sizeIn(minHeight = 64.dp, minWidth = 64.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(CircleShape)
            .clickable(onClick = { onClick(bottomNavItem) }),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = if (isSelected) bottomNavItem.selectedIcon else bottomNavItem.unselectedIcon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = bottomNavItem.title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.customColorScheme.selectedText
            else MaterialTheme.customColorScheme.unSelectedText
        )
    }
}

@Composable
@PreviewLightDark
fun LanPetBottomNavItemPreview() {
    LanPetAppTheme {
        Column {
            Column {
                LanPetBottomNavItem(
                    isSelected = true,
                    bottomNavItem = BottomNavItem.Wiki,
                    onClick = {}
                )
                LanPetBottomNavItem(
                    isSelected = false,
                    bottomNavItem = BottomNavItem.Wiki,
                    onClick = {}
                )
            }
            Column {
                LanPetBottomNavItem(
                    isSelected = true,
                    bottomNavItem = BottomNavItem.Free,
                    onClick = {}
                )
                LanPetBottomNavItem(
                    isSelected = false,
                    bottomNavItem = BottomNavItem.Free,
                    onClick = {}
                )
            }
            Column {
                LanPetBottomNavItem(
                    isSelected = true,
                    bottomNavItem = BottomNavItem.MyPage,
                    onClick = {}
                )
                LanPetBottomNavItem(
                    isSelected = false,
                    bottomNavItem = BottomNavItem.MyPage,
                    onClick = {}
                )
            }
        }
    }
}