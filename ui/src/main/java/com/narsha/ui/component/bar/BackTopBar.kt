package com.narsha.ui.component.bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narsha.ui.R
import com.narsha.ui.theme.color.ColorTheme

@Composable
fun BackTopBar(
    title: String,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 32.dp,
            )
            .padding(
                top = 20.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = ColorTheme.colors.black),
            modifier = Modifier
                .width(12.dp)
                .clickable {
                    popBackStack()
                }
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 20.sp,
            color = ColorTheme.colors.black
        )
    }
}