package com.narsha.buildlab_android.feature.screen.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.narsha.buildlab_android.feature.screen.auth.login.model.LoginUiState
import com.narsha.ui.component.bar.BackTopBar
import com.narsha.ui.component.button.MainButton
import com.narsha.ui.component.textfield.MainTextField
import com.narsha.ui.theme.color.ColorTheme

@Composable
fun LoginScreen(
    popBackStack: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(uiState.value.loginUiState) {
        when (uiState.value.loginUiState) {
            is LoginUiState.Success -> {
                navigateToHome()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                title = "로그인",
                popBackStack = popBackStack,
                modifier = Modifier.systemBarsPadding()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ColorTheme.colors.bg)
                .clickable(indication = null, interactionSource = null) {
                    focusManager.clearFocus()
                }
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                MainTextField(
                    value = email,
                    onValueChange = { email = it },
                    hint = "이메일을 입력하세요",
                    error = false,
                    title = "이메일",
                    imeAction = ImeAction.Next,
                    onImeAction = {
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                )
                Spacer(Modifier.height(12.dp))
                MainTextField(
                    value = password,
                    onValueChange = { password = it },
                    hint = "비밀번호를 입력하세요",
                    error = false,
                    title = "비밀번호",
                    imeAction = ImeAction.Done,
                    onImeAction = {
                        viewModel.login(email, password)
                        focusManager.clearFocus()
                    },
                )
            }

            MainButton(
                onClick = {
                    viewModel.login(email, password)
                },
                text = "로그인",
                enable = email.isNotEmpty() && password.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(bottom = 16.dp),
                loading = uiState.value.loginUiState == LoginUiState.Loading
            )
        }
    }

}