package com.narsha.buildlab_android.feature.screen.auth.signup.model


data class SignUpState (
    val signUpState: SignUpUiState = SignUpUiState.Idle,
    val verifyUiState: VerifyUiState = VerifyUiState.Idle,
    val emailUiState: EmailUiState = EmailUiState.Idle,
    val codes: List<String> = List(6) { "" }
)

sealed interface SignUpUiState {
    data object Idle : SignUpUiState
    data object Loading : SignUpUiState
    data object Success : SignUpUiState
    data class Error(val error: String) : SignUpUiState
}

sealed interface VerifyUiState {
    data object Idle : VerifyUiState
    data object Loading : VerifyUiState
    data class Success(
        val registrationToken: String
    ) : VerifyUiState

    data class Error(val error: String) : VerifyUiState
}

sealed interface EmailUiState {
    data object Idle : EmailUiState
    data object Loading : EmailUiState
    data object Success : EmailUiState
    data class Error(val error: String) : EmailUiState
}