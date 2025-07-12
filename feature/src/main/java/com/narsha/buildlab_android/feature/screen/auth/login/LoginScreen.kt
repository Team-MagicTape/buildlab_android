package com.narsha.buildlab_android.feature.screen.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.narsha.buildlab_android.feature.screen.auth.login.model.LoginSideEffect
import com.narsha.ui.component.bar.BackTopBar
import com.narsha.ui.component.button.CircleButton
import com.narsha.ui.component.button.MainButton
import com.narsha.ui.component.textfield.MainTextField
import com.narsha.ui.theme.color.ColorTheme
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun LoginScreen(
    popBackStack: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.collectAsState()
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is LoginSideEffect.NavigateToHome -> {
                    navigateToHome()
                }
                is LoginSideEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                title = "로그인",
                popBackStack = popBackStack,
                modifier = Modifier.systemBarsPadding()
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorTheme.colors.background)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus()
                }
                .padding(paddingValues)
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 46.dp)
            ) {
                MainTextField(
                    value = uiState.emailInput,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    hint = "이메일을 입력하세요",
                    title = "이메일",
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
                )
                Spacer(Modifier.height(12.dp))

                MainTextField(
                    value = uiState.passwordInput,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    hint = "비밀번호를 입력하세요",
                    title = "비밀번호",
                    isPassword = true,
                    imeAction = ImeAction.Done,
                    onImeAction = {
                        viewModel.onLoginButtonClicked()
                        focusManager.clearFocus()
                    }
                )
            }
            Column(
                modifier = Modifier
                    .align(
                        Alignment.BottomCenter
                    ),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    CircleButton(
                        isChecked = uiState.autoLogin,
                        onClick = { viewModel.onToggleAutoLogin() },
                        text = "자동 로그인"
                    )
                    Spacer(Modifier.width(10.dp))
                    CircleButton(
                        isChecked = uiState.saveId,
                        onClick = { viewModel.onToggleSaveId() },
                        text = "아이디 저장"
                    )
                }
                Spacer(Modifier.height(14.dp))
                MainButton(
                    onClick = {
                        viewModel.onLoginButtonClicked()
                        focusManager.clearFocus()
                    },
                    text = "로그인",
                    enable = uiState.emailInput.isNotEmpty() && uiState.passwordInput.isNotEmpty(),
                    loading = uiState.isLoading,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .imePadding()
                        .padding(bottom = 28.dp)
                )
            }
        }
    }
}