package com.narsha.buildlab_android.feature.screen.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.narsha.ui.R
import com.narsha.ui.theme.color.ColorTheme
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToStart: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    var expend by remember { mutableStateOf(true) }
    val animatedHeight by animateDpAsState(
        targetValue = if (expend) 60.dp else 32.dp,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedWidth by animateDpAsState(
        targetValue = if (expend) 50.dp else 27.dp,
        animationSpec = tween(durationMillis = 300)
    )
    val animatedRowWidth by animateDpAsState(
        targetValue = if (expend) 50.dp else 200.dp,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(true) {
        delay(500)
        expend = false
        visible = true
        delay(800)
        visible = false
        navigateToStart()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = ColorTheme.colors.bg)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.Center)
                .width(animatedRowWidth)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(animatedHeight)
                    .width(animatedWidth)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.buildlabicon),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 800)
                )
            ) {
                Row {
                    Spacer(modifier = Modifier.width(18.dp))
                    Image(
                        painter = painterResource(id = R.drawable.build),
                        contentDescription = null,
                        modifier = Modifier
                            .height(26.dp),
                        colorFilter = ColorFilter.tint(color = ColorTheme.colors.logoColor)
                    )
                    Spacer(Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.lab),
                        contentDescription = null,
                        modifier = Modifier
                            .height(26.dp)
                    )
                }
            }
        }
    }
}
