package com.narsha.buildlab_android

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.narsha.buildlab_android.feature.screen.auth.login.navigation.loginScreen
import com.narsha.buildlab_android.feature.screen.auth.login.navigation.navigateToLogin
import com.narsha.buildlab_android.feature.screen.auth.signup.navigation.navigateToSignUp
import com.narsha.buildlab_android.feature.screen.auth.signup.navigation.signUpScreen
import com.narsha.buildlab_android.feature.screen.home.navigation.homeScreen
import com.narsha.buildlab_android.feature.screen.home.navigation.navigateToHome
import com.narsha.buildlab_android.feature.screen.splash.navigation.SPLASH_ROUTE
import com.narsha.buildlab_android.feature.screen.splash.navigation.splashScreen
import com.narsha.buildlab_android.feature.screen.start.navigation.navigateToStart
import com.narsha.buildlab_android.feature.screen.start.navigation.startScreen
import com.narsha.ui.theme.color.AppTheme


@Composable
fun App(navHostController: NavHostController = rememberNavController()) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    AppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
//                bottomBar = {
//
//                }
            ) { innerPadding ->
                NavHost(
                    navController = navHostController,
                    startDestination = SPLASH_ROUTE,
                    modifier = Modifier.padding(innerPadding),
                    enterTransition = { getEnterTransition(initialState, targetState) },
                    exitTransition = { getExitTransition(initialState, targetState) },
                    popEnterTransition = { getPopEnterTransition(initialState, targetState) },
                    popExitTransition = { getPopExitTransition(initialState, targetState) },
                ) {
                    loginScreen(
                        popBackStack = navHostController::popBackStack,
                        navigateToHome = navHostController::navigateToHome,
                    )
                    splashScreen(navigateToStart = navHostController::navigateToStart)
                    startScreen(
                        navigateToLogin = navHostController::navigateToLogin,
                        navigateToSignUp = navHostController::navigateToSignUp
                    )
                    signUpScreen(popBackStack = navHostController::popBackStack)
                    homeScreen()
                }
            }
        }
    }
}

enum class TransitionDirection {
    LEFT, RIGHT, UP, DOWN, CUSTOM
}

private val transitionMap = mapOf(
    "start" to mapOf(
        "login" to TransitionDirection.RIGHT,
        "signup" to TransitionDirection.RIGHT
    ),
    "login" to mapOf(
        "start" to TransitionDirection.LEFT,
    ),
    "signup" to mapOf(
        "start" to TransitionDirection.LEFT,
    ),
    "signMove" to mapOf(
        "home" to TransitionDirection.RIGHT,
        "profile" to TransitionDirection.RIGHT,
        "move" to TransitionDirection.CUSTOM
    )
)

private fun getTransitionDirection(from: String?, to: String?): TransitionDirection? {
    return transitionMap[from]?.get(to)
}

fun getEnterTransition(initial: NavBackStackEntry, target: NavBackStackEntry): EnterTransition {
    val from = initial.destination.route
    val to = target.destination.route

    return when {
        getTransitionDirection(from, to) == TransitionDirection.LEFT -> slideInHorizontally { -it } + fadeIn()
        getTransitionDirection(from, to) == TransitionDirection.RIGHT -> slideInHorizontally { it } + fadeIn()
        getTransitionDirection(from, to) == TransitionDirection.UP -> slideInVertically { -it } + fadeIn()
        getTransitionDirection(from, to) == TransitionDirection.DOWN -> slideInVertically { it } + fadeIn()
        else -> EnterTransition.None
    }
}

fun getExitTransition(initial: NavBackStackEntry, target: NavBackStackEntry): ExitTransition {
    val from = initial.destination.route
    val to = target.destination.route

    return when {
        getTransitionDirection(from, to) == TransitionDirection.LEFT -> slideOutHorizontally { it } + fadeOut()
        getTransitionDirection(from, to) == TransitionDirection.RIGHT -> slideOutHorizontally { -it } + fadeOut()
        getTransitionDirection(from, to) == TransitionDirection.UP -> slideOutVertically { -it } + fadeOut()
        getTransitionDirection(from, to) == TransitionDirection.DOWN -> slideOutVertically { it } + fadeOut()
        else -> ExitTransition.None
    }
}



fun getPopEnterTransition(initial: NavBackStackEntry, target: NavBackStackEntry): EnterTransition {
    return getEnterTransition(initial, target)
}

fun getPopExitTransition(initial: NavBackStackEntry, target: NavBackStackEntry): ExitTransition {
    return getExitTransition(initial, target)
}

