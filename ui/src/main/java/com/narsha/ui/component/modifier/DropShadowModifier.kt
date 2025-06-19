package com.narsha.ui.component.modifier

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dropShadow(
    blur: Dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    color: Color,
    spread: Dp = 0.dp
): Modifier = this.drawBehind {
    val paint = Paint()
    val frameworkPaint = paint.asFrameworkPaint()
    val blurPx = blur.toPx()
    val offsetXPx = offsetX.toPx()
    val offsetYPx = offsetY.toPx()
    val spreadPx = spread.toPx()

    if (blurPx > 0f) {
        frameworkPaint.maskFilter = BlurMaskFilter(blurPx, BlurMaskFilter.Blur.NORMAL)
    }

    frameworkPaint.color = color.toArgb()

    val left = -spreadPx + offsetXPx
    val top = -spreadPx + offsetYPx
    val right = size.width + spreadPx + offsetXPx
    val bottom = size.height + spreadPx + offsetYPx

    drawIntoCanvas { canvas ->
        canvas.drawRect(
            left = left,
            top = top,
            right = right,
            bottom = bottom,
            paint = paint
        )
    }
}


