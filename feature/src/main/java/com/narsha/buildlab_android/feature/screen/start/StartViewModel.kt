package com.narsha.buildlab_android.feature.screen.start

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.narsha.buildlab_android.BuildLabApplication
import com.narsha.network.auth.model.request.SignupRequest
import com.narsha.network.auth.model.request.VerifyEmailRequest
import com.narsha.network.core.NetworkErrorHandler
import com.narsha.network.core.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<StartUiState>(StartUiState.Idle)
    val uiState: StateFlow<StartUiState> = _uiState.asStateFlow()

    private val _isFailState = mutableStateOf(false)
    val isFailState: State<Boolean> = _isFailState

    fun login(
        activityResult: ActivityResult,
        onSuccess: () -> Unit
    ) {
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
                .getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) onSuccess()
                }
        } catch (e: Exception) {
            Log.d("GoogleLogin", "StartScreen: 오류 ${e.message}")
            _isFailState.value = true
        }
    }

    fun googleLogin(code: String) {
        viewModelScope.launch {
            _uiState.value = StartUiState.Loading
            try {
                RetrofitClient.authService.googleLogin(code)
                _uiState.value = StartUiState.Success
            } catch (e: Exception) {
                val error = NetworkErrorHandler.handle(BuildLabApplication.getContext(), e)
                _uiState.value = StartUiState.Error(error ?: "알 수 없는 오류가 발생했습니다")
            }
        }
    }
}


sealed interface StartUiState {
    data object Idle : StartUiState
    data object Loading : StartUiState
    data object Success : StartUiState
    data class Error(val error: String) : StartUiState
}