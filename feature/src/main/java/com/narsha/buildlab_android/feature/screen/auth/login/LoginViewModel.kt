package com.narsha.buildlab_android.feature.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narsha.buildlab_android.BuildLabApplication
import com.narsha.buildlab_android.feature.screen.auth.login.model.LoginState
import com.narsha.buildlab_android.feature.screen.auth.login.model.LoginUiState
import com.narsha.network.auth.model.request.LoginRequest
import com.narsha.network.core.NetworkErrorHandler
import com.narsha.network.core.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val state = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(loginUiState = LoginUiState.Loading)
            }
            try {
                val response = RetrofitClient.authService.login(LoginRequest(email, password))
                _uiState.update {
                    it.copy(loginUiState = LoginUiState.Success)
                }
            } catch (e: Exception) {
                val error = NetworkErrorHandler.handle(BuildLabApplication.getContext(), e)
                _uiState.update {
                    it.copy(loginUiState = LoginUiState.Error(error?: "알 수 없는 오류가 발생했습니다"))
                }
            }
        }
    }
}

