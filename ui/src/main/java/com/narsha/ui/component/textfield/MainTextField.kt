package com.narsha.ui.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narsha.ui.theme.color.ColorTheme

@Composable
fun MainTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String,
    title: String,
    error: Boolean = false, // 에러 상태는 필요 시 외부에서 제어
    imeAction: ImeAction = ImeAction.Default,
    onImeAction: () -> Unit = {},
    isPassword: Boolean = false, // 비밀번호 필드 여부
    isTitle: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = when {
            error -> ColorTheme.colors.error
            isFocused -> ColorTheme.colors.primary.normal
            else -> ColorTheme.colors.gray.normal
        },
        animationSpec = tween(durationMillis = 150),
        label = "borderColor"
    )

    val titleColor by animateColorAsState(
        targetValue = if (isFocused) ColorTheme.colors.primary.normal else ColorTheme.colors.black,
        animationSpec = tween(durationMillis = 150),
        label = "titleColor"
    )

    Column(modifier = modifier) {
        if (isTitle) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = titleColor
            )
            Spacer(Modifier.height(4.dp))
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 14.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = ColorTheme.colors.black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onAny = { onImeAction() }
            ),
            cursorBrush = SolidColor(ColorTheme.colors.primary.normal),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                color = ColorTheme.colors.gray.normal,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                }
            },
            singleLine = true
        )
    }
}