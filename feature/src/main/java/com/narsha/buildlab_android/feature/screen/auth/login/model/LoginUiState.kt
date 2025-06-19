package com.narsha.buildlab_android.feature.screen.auth.login.model


data class LoginState (
    val loginUiState: LoginUiState = LoginUiState.Idle
)

sealed interface LoginUiState {
    data object Idle: LoginUiState
    data object Loading: LoginUiState
    data class Error(
        val error: String
    ): LoginUiState
    data object Success: LoginUiState
}