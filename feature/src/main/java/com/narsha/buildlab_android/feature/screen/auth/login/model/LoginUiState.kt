package com.narsha.buildlab_android.feature.screen.auth.login.model

data class LoginState (
    val emailInput: String = "",
    val passwordInput: String = "",
    val isLoading: Boolean = false,
    val saveId: Boolean = false,
    val autoLogin: Boolean = true,
)

sealed class LoginSideEffect {
    object NavigateToHome : LoginSideEffect()
    data class ShowSnackbar(val message: String) : LoginSideEffect()
}