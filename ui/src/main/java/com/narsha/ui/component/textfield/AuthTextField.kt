package com.narsha.ui.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narsha.ui.loading.CircleProgress
import com.narsha.ui.theme.color.ColorTheme
import kotlinx.coroutines.delay


@Composable
fun AuthTextField(
    value: String, onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String,
    error: Boolean,
    verify: () -> Unit,
    showTimer: Boolean,
    done: Boolean,
    isLoading: Boolean,
    enable: Boolean,
    isTitle: Boolean = false,
    title: String = "",
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: (() -> Unit)? = null,
    expand: (Boolean) -> Unit = {},
) {
    val isEnabled = enable && !isLoading
    var isFocused by remember { mutableStateOf(false) }
    var timer by remember { mutableIntStateOf(300) }

    val textColor by animateColorAsState(
        targetValue = if (isEnabled) ColorTheme.colors.primary.normal else ColorTheme.colors.primary.normal.copy(alpha = 0.5f),
        animationSpec = tween(durationMillis = 50)
    )

    val titleColor by animateColorAsState(
        targetValue = if (isFocused) ColorTheme.colors.primary.normal else ColorTheme.colors.black,
        animationSpec = tween(durationMillis = 50)
    )

    val animatedColor by animateColorAsState(
        targetValue = if (error) {
            ColorTheme.colors.error
        } else {
            if (isFocused) {
                ColorTheme.colors.primary.normal
            } else {
                ColorTheme.colors.gray.normal
            }
        },
        animationSpec = tween(durationMillis = 50)
    )

    LaunchedEffect(showTimer) {
        if (showTimer) {
            for (i in 300 downTo 1) {
                delay(1000)
                timer -= 1
            }
        }
    }

    Column {
        if (isTitle) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = titleColor
            )
            Spacer(Modifier.height(4.dp))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color = animatedColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                        expand(isFocused)
                    },
                cursorBrush = SolidColor(ColorTheme.colors.primary.normal),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = if (done) ColorTheme.colors.gray.normal else ColorTheme.colors.black
                ),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = ColorTheme.colors.gray.normal,
                            fontSize = 14.sp,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                },
                singleLine = true,
                readOnly = done,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(
                    onAny = {
                        onImeAction?.invoke()
                    }
                ),
            )
            if (isLoading) {
                CircleProgress(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                )
            } else {
                Text(
                    text = if (done) "완료" else if (showTimer) timer.toString() else "인증",
                    fontSize = 14.sp,
                    color = textColor,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd)
                        .then(
                            if (isEnabled) Modifier.pointerInput(Unit) {
                                awaitPointerEventScope {}
                            } else Modifier
                        )
                        .clickable(
                            enabled = isEnabled,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = verify
                        )
                )
            }
        }
    }
}
