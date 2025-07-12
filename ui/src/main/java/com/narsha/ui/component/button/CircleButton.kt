package com.narsha.ui.component.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narsha.ui.theme.color.ColorTheme
import com.narsha.ui.R

@Composable
fun CircleButton(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = modifier
                .size(20.dp)
                .background(
                    shape = CircleShape,
                    color = if (isChecked) ColorTheme.colors.primary.normal else ColorTheme.colors.background
                )
                .border(if (isChecked) 0.dp else 1.dp, ColorTheme.colors.gray.normal, CircleShape)
        ) {
            if (isChecked) {
                Image(
                    painter = painterResource(R.drawable.checkicon),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            fontSize = 12.sp,
        )
    }
}