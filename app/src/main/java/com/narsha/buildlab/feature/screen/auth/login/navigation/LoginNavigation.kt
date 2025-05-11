package com.narsha.buildlab.feature.screen.auth.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.narsha.buildlab.feature.screen.auth.login.LoginScreen

const val LOGIN_ROUTE = "login"

fun NavController.navigateToLogin() {
    this.navigate(LOGIN_ROUTE) {
//        popUpTo(SPLASH_ROUTE) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavGraphBuilder.loginScreen() {
    composable(
        route = LOGIN_ROUTE,
    ) {
        LoginScreen()
    }
}