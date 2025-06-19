package com.narsha.ui.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narsha.ui.loading.LoadingDots
import com.narsha.ui.theme.color.ColorTheme

@Composable
fun SecondButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    buttonIcon: Painter? = null,
) {
    var pressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 300f),
        label = "clickScale"
    )

    val overlayColor by animateColorAsState(
        targetValue = if (pressed) Color.Black.copy(alpha = 0.2f) else Color.Transparent,
        animationSpec = tween(durationMillis = 100),
        label = "clickOverlayColor"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = ColorTheme.colors.grayBorder,
                shape = RoundedCornerShape(8.dp)
            )
            .background(overlayColor, RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Press -> pressed = true
                            PointerEventType.Release,
                            PointerEventType.Exit -> pressed = false

                            else -> Unit
                        }
                    }
                }
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 18.dp)
            .height(50.dp)
    ) {
        buttonIcon?.let {
            Image(
                painter = buttonIcon,
                contentDescription = null,
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .width(20.dp)
                    .height(16.dp)
            )
        }

        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = ColorTheme.colors.black,
            modifier = Modifier
                .align(alignment = Alignment.Center)
        )
    }
}