package com.example.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

/**
 * Corner radius
 */

val cornerRadiusLight = 8.dp
val cornerRadiusNormal = 16.dp
val cornerRadiusHard = 24.dp

/**
 * margins
 */

val marginLight = 8.dp
val marginMedium = 16.dp
val marginHard = 24.dp

/**
 * Base horizontal margin
 * Typically, used for setting screen base horizontal margin
 */
fun MaterialTheme.baseHorizontalMargin() = LanPetSpacing.small

/**
 * Lan pet spacing
 */
object LanPetSpacing {
    val xxSmall = 4.dp
    val xSmall = 8.dp
    val small = 12.dp
    val medium = 16.dp
    val large = 24.dp
    val xLarge = 32.dp
    val xxLarge = 40.dp
}
