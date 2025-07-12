package com.narsha.buildlab_android.feature.screen.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.narsha.buildlab_android.BuildLabApplication
import com.narsha.buildlab_android.feature.screen.auth.login.model.LoginSideEffect
import com.narsha.buildlab_android.feature.screen.auth.login.model.LoginState
import com.narsha.network.auth.model.request.LoginRequest
import com.narsha.network.core.NetworkErrorHandler
import com.narsha.network.core.remote.RetrofitClient
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class LoginViewModel : ViewModel(), ContainerHost<LoginState, LoginSideEffect> {

    override val container = container<LoginState, LoginSideEffect>(LoginState())

    fun onEmailChanged(email: String) = intent {
        reduce {
            state.copy(emailInput = email)
        }
    }

    fun onPasswordChanged(password: String) = intent {
        reduce {
            state.copy(passwordInput = password)
        }
    }

    fun onToggleAutoLogin() = intent {
        reduce {
            state.copy(autoLogin = !state.autoLogin)
        }
    }

    fun onToggleSaveId() = intent {
        reduce {
            state.copy(saveId = !state.saveId)
        }
    }

    fun onLoginButtonClicked() = intent {
        reduce { state.copy(isLoading = true) }

        try {
            RetrofitClient.authService.login(
                LoginRequest(state.emailInput, state.passwordInput)
            )

            reduce { state.copy(isLoading = false) }
            postSideEffect(LoginSideEffect.NavigateToHome)

        } catch (e: Exception) {
            val error = NetworkErrorHandler.handle(BuildLabApplication.getContext(), e)
            val errorMessage = error ?: "알 수 없는 오류가 발생했습니다."
            Log.d("login error", "onLoginButtonClicked: $errorMessage")

            reduce {
                state.copy(
                    isLoading = false,
                    passwordInput = "",
                    emailInput = if (state.saveId) state.emailInput else ""
                )
            }
            postSideEffect(LoginSideEffect.ShowSnackbar(errorMessage))
        }
    }
}