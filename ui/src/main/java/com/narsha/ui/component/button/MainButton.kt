package com.narsha.ui.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narsha.ui.component.modifier.buttonShadow
import com.narsha.ui.component.modifier.dropShadow
import com.narsha.ui.loading.LoadingDots
import com.narsha.ui.theme.color.ColorTheme

@Composable
fun MainButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enable: Boolean,
    loading: Boolean,
) {
    val isEnabled = enable && !loading
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        label = "scale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isEnabled) {
            if (isPressed) ColorTheme.colors.primary.active else ColorTheme.colors.primary.normal
        } else {
            ColorTheme.colors.primary.normal.copy(alpha = 0.5f)
        },
        animationSpec = tween(durationMillis = 100),
        label = "backgroundColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isEnabled) Color.White else ColorTheme.colors.white.copy(alpha = 0.7f),
        label = "textColor"
    )

    val shadowColor by animateColorAsState(
        targetValue = if (isEnabled) ColorTheme.colors.shadow.primary else ColorTheme.colors.background,
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .scale(scale)
            .buttonShadow(
                color = shadowColor,
                shape = RoundedCornerShape(100.dp),
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable(
                enabled = isEnabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            LoadingDots()
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = textColor
            )
        }
    }
}