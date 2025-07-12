package com.narsha.buildlab_android.feature.screen.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.narsha.ui.R
import com.narsha.ui.theme.color.ColorTheme
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToStart: () -> Unit
) {
    var loading by remember { mutableStateOf(false) }
    val strokeCap = StrokeCap

    LaunchedEffect(Unit) {
        delay(1000)
        loading = true
        delay(2000)
        loading = false
        navigateToStart()
    }

    val animatedSize by animateDpAsState(
        targetValue = if (loading) 60.dp else 80.dp,
        animationSpec = tween(durationMillis = 500),
    )

    val loadingSize by animateDpAsState(
        targetValue = if (loading) 100.dp else 60.dp,
        animationSpec = tween(durationMillis = 500),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
        .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.buildlabicon),
                contentDescription = null,
                modifier = Modifier
                    .size(animatedSize)
                    .zIndex(9f),
            )
            if (loading) {
                CircularProgressIndicator(
                    color = ColorTheme.colors.primary.normal,
                    strokeWidth = 8.dp,
                    modifier = Modifier
                        .size(loadingSize),
                    strokeCap = strokeCap.Round,
                )
            }
        }
    }
}