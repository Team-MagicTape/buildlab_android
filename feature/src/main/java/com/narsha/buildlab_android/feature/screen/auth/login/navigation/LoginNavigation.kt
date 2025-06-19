package com.narsha.buildlab_android.feature.screen.auth.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.narsha.buildlab_android.feature.screen.auth.login.LoginScreen
import com.narsha.buildlab_android.feature.screen.splash.navigation.SPLASH_ROUTE

const val LOGIN_ROUTE = "login"

fun NavController.navigateToLogin() = this.navigate(LOGIN_ROUTE)


fun NavGraphBuilder.loginScreen(
    navigateToHome: () -> Unit,
    popBackStack: () -> Unit
) {
    composable(
        route = LOGIN_ROUTE,
    ) {
        LoginScreen(
            navigateToHome = navigateToHome,
            popBackStack = popBackStack
        )
    }
}