package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.Search: ImageVector
    get() {
        if (search != null) {
            return search!!
        }
        search =
            Builder(
                name = "Search",
                defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp,
                viewportWidth = 24.0f,
                viewportHeight = 24.0f,
            ).apply {
                path(
                    fill = SolidColor(Color(0x00000000)),
                    stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(11.0f, 19.0f)
                    curveTo(15.4183f, 19.0f, 19.0f, 15.4183f, 19.0f, 11.0f)
                    curveTo(19.0f, 6.5817f, 15.4183f, 3.0f, 11.0f, 3.0f)
                    curveTo(6.5817f, 3.0f, 3.0f, 6.5817f, 3.0f, 11.0f)
                    curveTo(3.0f, 15.4183f, 6.5817f, 19.0f, 11.0f, 19.0f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)),
                    stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(21.0f, 21.0f)
                    lineTo(16.65f, 16.65f)
                }
            }.build()
        return search!!
    }

private var search: ImageVector? = null
