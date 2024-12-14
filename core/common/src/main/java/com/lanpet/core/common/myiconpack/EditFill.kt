package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.EditFill: ImageVector
    get() {
        if (editFill != null) {
            return editFill!!
        }
        editFill =
            Builder(
                name = "EditFill",
                defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp,
                viewportWidth = 24.0f,
                viewportHeight = 24.0f,
            ).apply {
                group {
                    path(
                        fill = SolidColor(Color(0xFF25262B)),
                        stroke = null,
                        strokeLineWidth = 0.0f,
                        strokeLineCap = Butt,
                        strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f,
                        pathFillType = NonZero,
                    ) {
                        moveTo(12.0f, 12.0f)
                        moveToRelative(-12.0f, 0.0f)
                        arcToRelative(12.0f, 12.0f, 0.0f, true, true, 24.0f, 0.0f)
                        arcToRelative(12.0f, 12.0f, 0.0f, true, true, -24.0f, 0.0f)
                    }
                    path(
                        fill = SolidColor(Color(0xFFffffff)),
                        stroke = null,
                        strokeLineWidth = 0.0f,
                        strokeLineCap = Butt,
                        strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd,
                    ) {
                        moveTo(5.9469f, 18.8033f)
                        curveTo(5.5327f, 18.8033f, 5.1969f, 18.4675f, 5.1969f, 18.0533f)
                        lineTo(5.1969f, 14.8713f)
                        curveTo(5.1969f, 14.6724f, 5.2759f, 14.4816f, 5.4166f, 14.341f)
                        lineTo(14.4322f, 5.3254f)
                        curveTo(14.7251f, 5.0325f, 15.1999f, 5.0325f, 15.4928f, 5.3254f)
                        lineTo(18.6748f, 8.5074f)
                        curveTo(18.9677f, 8.8003f, 18.9677f, 9.2751f, 18.6748f, 9.568f)
                        lineTo(9.6592f, 18.5836f)
                        curveTo(9.5186f, 18.7243f, 9.3278f, 18.8033f, 9.1289f, 18.8033f)
                        horizontalLineTo(5.9469f)
                        close()
                        moveTo(8.8182f, 17.3033f)
                        lineTo(17.0838f, 9.0377f)
                        lineTo(14.9625f, 6.9164f)
                        lineTo(6.6969f, 15.182f)
                        verticalLineTo(17.3033f)
                        horizontalLineTo(8.8182f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color(0xFFffffff)),
                        stroke = null,
                        strokeLineWidth = 0.0f,
                        strokeLineCap = Butt,
                        strokeLineJoin = Miter,
                        strokeLineMiter = 4.0f,
                        pathFillType = EvenOdd,
                    ) {
                        moveTo(12.8412f, 7.977f)
                        lineTo(16.0232f, 11.159f)
                        lineTo(14.9625f, 12.2197f)
                        lineTo(11.7805f, 9.0377f)
                        lineTo(12.8412f, 7.977f)
                        close()
                    }
                }
            }.build()
        return editFill!!
    }

private var editFill: ImageVector? = null
