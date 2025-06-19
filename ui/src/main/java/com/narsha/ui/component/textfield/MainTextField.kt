package com.narsha.ui.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narsha.ui.theme.color.ColorTheme

@Composable
fun MainTextField(
    value: String, onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String,
    error: Boolean,
    title: String,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: (() -> Unit)? = null,
    isTitle: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    val animatedColor by animateColorAsState(
        targetValue = if (error) {
            ColorTheme.colors.error
        } else {
            if (isFocused) {
                ColorTheme.colors.liteMain
            } else {
                ColorTheme.colors.placeHolder
            }
        },
        animationSpec = tween(durationMillis = 50)
    )
    val titleColor by animateColorAsState(
        targetValue = if (isFocused) ColorTheme.colors.liteMain else ColorTheme.colors.black,
        animationSpec = tween(durationMillis = 50)
    )

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
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
                    .padding(horizontal = 14.dp),
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = ColorTheme.colors.black
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(
                    onAny = {
                        onImeAction?.invoke()
                    }
                ),
                cursorBrush = SolidColor(ColorTheme.colors.liteMain),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = ColorTheme.colors.placeHolder,
                            fontSize = 14.sp,
                            modifier = modifier.align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                },
                singleLine = true
            )
        }
    }
}
