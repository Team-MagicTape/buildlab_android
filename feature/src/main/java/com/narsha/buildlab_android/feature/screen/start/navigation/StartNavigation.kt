package com.narsha.buildlab_android.feature.screen.start.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.narsha.buildlab_android.feature.screen.start.StartScreen

const val START_ROUTE = "start"

fun NavController.navigateToStart() = this.navigate(START_ROUTE)

fun NavGraphBuilder.startScreen(
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    composable(
        route = START_ROUTE,
    ) {
        StartScreen(
            navigateToLogin = navigateToLogin,
            navigateToSignUp = navigateToSignUp
        )
    }
}