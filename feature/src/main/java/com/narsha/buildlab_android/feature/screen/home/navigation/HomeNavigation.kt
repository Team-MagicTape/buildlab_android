package com.narsha.buildlab_android.feature.screen.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.narsha.buildlab_android.feature.screen.home.HomeScreen
import com.narsha.buildlab_android.feature.screen.splash.SplashScreen

const val HOME_ROUTE = "home"

fun NavGraphBuilder.homeScreen() {
    composable(
        route = HOME_ROUTE,
    ) {
        HomeScreen()
    }
}

fun NavController.navigateToHome() = this.navigate(HOME_ROUTE)