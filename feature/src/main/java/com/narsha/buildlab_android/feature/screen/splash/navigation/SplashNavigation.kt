package com.narsha.buildlab_android.feature.screen.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.narsha.buildlab_android.feature.screen.splash.SplashScreen

const val SPLASH_ROUTE = "splash"

fun NavGraphBuilder.splashScreen(
    navigateToStart: () -> Unit,
) {
    composable(
        route = SPLASH_ROUTE,
    ) {
        SplashScreen(
            navigateToStart = navigateToStart
        )
    }
}