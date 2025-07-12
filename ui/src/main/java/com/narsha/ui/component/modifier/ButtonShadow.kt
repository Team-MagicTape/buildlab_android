package com.narsha.ui.component.modifier

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.narsha.ui.theme.color.ColorTheme

fun Modifier.buttonShadow(
    color: Color,
    shape: Shape
) = this.dropShadow(
    shape = shape,
    offsetY = 20.dp,
    color = color.copy(alpha = 0f),
    blur = 6.dp,
    offsetX = 0.dp,
    spread = 0.dp
)
    .dropShadow(
        shape = shape,
        offsetY = 13.dp,
        color = color.copy(alpha = 0.03f),
        blur = 5.dp,
        offsetX = 0.dp,
        spread = 0.dp
    )
    .dropShadow(
        shape = shape,
        offsetY = 7.dp,
        color = color.copy(alpha = 0.1f),
        blur = 4.dp,
        offsetX = 0.dp,
        spread = 0.dp
    )
    .dropShadow(
        shape = shape,
        offsetY = 3.dp,
        color = color.copy(alpha = 0.17f),
        blur = 3.dp,
        offsetX = 0.dp,
        spread = 0.dp
    )
    .dropShadow(
        shape = shape,
        offsetY = 1.dp,
        color = color.copy(alpha = 0.2f),
        blur = 2.dp,
        offsetX = 0.dp,
        spread = 0.dp
    )