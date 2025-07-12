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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.layout.onSizeChanged
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
    val keyboardController = LocalSoftwareKeyboardController.current
    
    // 키보드 상태를 감지하기 위한 상태
    var isKeyboardVisible by remember { mutableStateOf(false) }
    var screenHeight by remember { mutableStateOf(0) }
    var currentHeight by remember { mutableStateOf(0) }
    
    // 포커스 상태를 감지하여 키보드 표시 여부 추정
    val isFocused = remember { mutableStateOf(false) }
    
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
                .onSizeChanged { size ->
                    if (screenHeight == 0) {
                        screenHeight = size.height
                    }
                    currentHeight = size.height
                    // 키보드가 올라왔는지 감지 (화면 높이의 15% 이상 줄어들었을 때)
                    isKeyboardVisible = screenHeight > 0 && (screenHeight - currentHeight) > (screenHeight * 0.15)
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    focusManager.clearFocus()
                    isFocused.value = false
                    isKeyboardVisible = false
                }
                .padding(paddingValues)
                .padding(
                    horizontal = if (isKeyboardVisible) 0.dp else 32.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (isKeyboardVisible) 20.dp else 46.dp)
            ) {
                MainTextField(
                    value = uiState.emailInput,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    hint = "이메일을 입력하세요",
                    title = "이메일",
                    imeAction = ImeAction.Next,
                    onImeAction = { focusManager.moveFocus(FocusDirection.Down) },
                    onFocusChanged = { focused ->
                        isFocused.value = focused
                    }
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
                        isFocused.value = false
                        isKeyboardVisible = false
                    },
                    onFocusChanged = { focused ->
                        isFocused.value = focused
                    }
                )
            }
            
            // 키보드가 보이지 않을 때만 하단 버튼들 표시
            if (!isKeyboardVisible) {
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
                            isFocused.value = false
                            isKeyboardVisible = false
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
}