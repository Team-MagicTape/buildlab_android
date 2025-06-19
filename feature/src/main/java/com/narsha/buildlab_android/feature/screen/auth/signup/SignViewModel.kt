package com.narsha.buildlab_android.feature.screen.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narsha.buildlab_android.BuildLabApplication
import com.narsha.buildlab_android.feature.screen.auth.signup.model.EmailUiState
import com.narsha.buildlab_android.feature.screen.auth.signup.model.SignUpState
import com.narsha.buildlab_android.feature.screen.auth.signup.model.SignUpUiState
import com.narsha.buildlab_android.feature.screen.auth.signup.model.VerifyUiState
import com.narsha.network.auth.model.request.EmailRequest
import com.narsha.network.auth.model.request.SignupRequest
import com.narsha.network.auth.model.request.VerifyEmailRequest
import com.narsha.network.core.NetworkErrorHandler
import com.narsha.network.core.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    val buttonLevel: StateFlow<Int> = state.map { state ->
        when {
            state.verifyUiState is VerifyUiState.Success -> 2
            state.emailUiState is EmailUiState.Success -> 1
            else -> 0
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun emailRequest(email: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(emailUiState = EmailUiState.Loading)
            }
            try {
                RetrofitClient.authService.sendEmail(EmailRequest(email))
                _state.update {
                    it.copy(emailUiState = EmailUiState.Success)
                }
            } catch (e: Exception) {
                val error = NetworkErrorHandler.handle(BuildLabApplication.getContext(), e)
                _state.update {
                    it.copy(emailUiState = EmailUiState.Error(error ?: "알 수 없는 오류가 발생했습니다"))
                }
            }
        }
    }

    fun verifyEmail(code: String, email: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(verifyUiState = VerifyUiState.Loading)
            }
            try {
                val response = RetrofitClient.authService.verifyEmail(
                    VerifyEmailRequest(email = email, code = code)
                )
                _state.update {
                    it.copy(verifyUiState = VerifyUiState.Success(response.registrationToken))
                }
            } catch (e: Exception) {
                val error = NetworkErrorHandler.handle(BuildLabApplication.getContext(), e)
                _state.update {
                    it.copy(verifyUiState = VerifyUiState.Error(error ?: "알 수 없는 오류가 발생했습니다"))
                }
            }
        }
    }

    fun signUp(username: String, email: String, password: String, registrationToken: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(signUpState = SignUpUiState.Loading)
            }
            try {
                RetrofitClient.authService.signup(
                    SignupRequest(username, email, password, registrationToken)
                )
                _state.update {
                    it.copy(signUpState = SignUpUiState.Success)
                }
            } catch (e: Exception) {
                val error = NetworkErrorHandler.handle(BuildLabApplication.getContext(), e)
                _state.update {
                    it.copy(signUpState = SignUpUiState.Error(error ?: "알 수 없는 오류가 발생했습니다"))
                }
            }
        }
    }

    fun updateCode(index: Int, value: String) {
        if (value.length > 6) return

        if (value.length == 6) {
            pasteCode(value)
        } else if (value.length <= 1) {
            _state.update { state ->
                state.copy(
                    codes = state.codes.toMutableList().apply {
                        this[index] = value
                    }
                )
            }
        }
    }

    private fun pasteCode(code: String) {
        if (code.length != 6) return
        _state.update {
            it.copy(codes = code.map { it.toString() })
        }
    }
}



