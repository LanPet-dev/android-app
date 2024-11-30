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

public val MyIconPack.Heart: ImageVector
    get() {
        if (_heart != null) {
            return _heart!!
        }
        _heart = Builder(name = "Heart", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(20.84f, 4.61f)
                curveTo(20.3292f, 4.099f, 19.7228f, 3.6936f, 19.0554f, 3.4171f)
                curveTo(18.3879f, 3.1405f, 17.6725f, 2.9982f, 16.95f, 2.9982f)
                curveTo(16.2275f, 2.9982f, 15.5121f, 3.1405f, 14.8446f, 3.4171f)
                curveTo(14.1772f, 3.6936f, 13.5708f, 4.099f, 13.06f, 4.61f)
                lineTo(12.0f, 5.67f)
                lineTo(10.94f, 4.61f)
                curveTo(9.9083f, 3.5783f, 8.509f, 2.9987f, 7.05f, 2.9987f)
                curveTo(5.591f, 2.9987f, 4.1917f, 3.5783f, 3.16f, 4.61f)
                curveTo(2.1283f, 5.6417f, 1.5487f, 7.041f, 1.5487f, 8.5f)
                curveTo(1.5487f, 9.959f, 2.1283f, 11.3583f, 3.16f, 12.39f)
                lineTo(4.22f, 13.45f)
                lineTo(12.0f, 21.23f)
                lineTo(19.78f, 13.45f)
                lineTo(20.84f, 12.39f)
                curveTo(21.351f, 11.8792f, 21.7563f, 11.2728f, 22.0329f, 10.6053f)
                curveTo(22.3095f, 9.9379f, 22.4518f, 9.2225f, 22.4518f, 8.5f)
                curveTo(22.4518f, 7.7775f, 22.3095f, 7.0621f, 22.0329f, 6.3946f)
                curveTo(21.7563f, 5.7272f, 21.351f, 5.1207f, 20.84f, 4.61f)
                close()
            }
        }
        .build()
        return _heart!!
    }

private var _heart: ImageVector? = null
