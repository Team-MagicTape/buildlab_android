package com.narsha.ui.component.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narsha.ui.theme.color.ColorTheme


@Composable
fun CodeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    onFocusChanged: ((Boolean) -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    
    val animatedColor by animateColorAsState(
        targetValue = if (value.isNotEmpty()) {
            ColorTheme.colors.primary.normal
        } else {
            ColorTheme.colors.gray.normal
        },
        animationSpec = tween(durationMillis = 50)
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
                onFocusChanged?.invoke(focusState.isFocused)
            }
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = animatedColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 0.dp),
        readOnly = readOnly,
        textStyle = TextStyle(
            color = ColorTheme.colors.black,
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    ) { innerTextField ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            innerTextField()
        }
    }
}
