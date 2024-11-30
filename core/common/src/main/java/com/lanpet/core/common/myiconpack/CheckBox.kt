package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.CheckBox: ImageVector
    get() {
        if (_checkBox != null) {
            return _checkBox!!
        }
        _checkBox = Builder(name = "Check box", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFB5B9C4)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(4.0f, 0.5f)
                lineTo(20.0f, 0.5f)
                arcTo(3.5f, 3.5f, 0.0f, false, true, 23.5f, 4.0f)
                lineTo(23.5f, 20.0f)
                arcTo(3.5f, 3.5f, 0.0f, false, true, 20.0f, 23.5f)
                lineTo(4.0f, 23.5f)
                arcTo(3.5f, 3.5f, 0.0f, false, true, 0.5f, 20.0f)
                lineTo(0.5f, 4.0f)
                arcTo(3.5f, 3.5f, 0.0f, false, true, 4.0f, 0.5f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(18.6666f, 7.0f)
                lineTo(9.5f, 16.1667f)
                lineTo(5.3333f, 12.0f)
            }
        }
        .build()
        return _checkBox!!
    }

private var _checkBox: ImageVector? = null
