package com.narsha.buildlab_android.feature.screen.auth.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.narsha.buildlab_android.feature.screen.auth.login.LoginScreen
import com.narsha.buildlab_android.feature.screen.auth.login.navigation.LOGIN_ROUTE
import com.narsha.buildlab_android.feature.screen.auth.signup.SignUpScreen
import com.narsha.buildlab_android.feature.screen.splash.navigation.SPLASH_ROUTE

const val SIGNUP_ROUTE = "signup"

fun NavController.navigateToSignUp() = this.navigate(SIGNUP_ROUTE)


fun NavGraphBuilder.signUpScreen(
//    navigateToHome: () -> Unit,
    popBackStack: () -> Unit
) {
    composable(
        route = SIGNUP_ROUTE,
    ) {
        SignUpScreen (
//            navigateToHome = navigateToHome,
            popBackStack = popBackStack
        )
    }
}