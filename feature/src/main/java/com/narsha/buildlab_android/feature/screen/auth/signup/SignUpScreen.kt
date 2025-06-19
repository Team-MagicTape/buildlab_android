package com.narsha.buildlab_android.feature.screen.auth.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.narsha.buildlab_android.feature.screen.auth.signup.model.EmailUiState
import com.narsha.buildlab_android.feature.screen.auth.signup.model.SignUpUiState
import com.narsha.buildlab_android.feature.screen.auth.signup.model.VerifyUiState
import com.narsha.ui.component.bar.BackTopBar
import com.narsha.ui.component.button.MainButton
import com.narsha.ui.component.textfield.AuthTextField
import com.narsha.ui.component.textfield.CodeTextField
import com.narsha.ui.component.textfield.MainTextField
import com.narsha.ui.theme.color.ColorTheme

@Composable
fun SignUpScreen(
    popBackStack: () -> Unit,
    viewModel: SignViewModel = viewModel(),
) {
    val focusManager = LocalFocusManager.current

    val uiState = viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    var done by remember { mutableStateOf(false) }
    val buttonLevel by viewModel.buttonLevel.collectAsState()

    LaunchedEffect(uiState.value.verifyUiState) {
        when (uiState.value.verifyUiState) {
            is VerifyUiState.Success -> {
                done = true
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                title = "회원가입",
                popBackStack = popBackStack,
                modifier = Modifier.systemBarsPadding()
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorTheme.colors.bg)
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
                    .padding(top = 60.dp)
            ) {
                val slideAnimation = remember {
                    slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    )
                }

                AnimatedVisibility(
                    visible = buttonLevel == 2,
                    enter = slideAnimation,
                    exit = slideOutVertically(
                        targetOffsetY = { -it }
                    )
                ) {
                    Column {
                        MainTextField(
                            value = username,
                            onValueChange = { username = it },
                            hint = "아이디",
                            error = when (uiState.value.signUpState) {
                                is SignUpUiState.Error -> true
                                else -> false
                            },
                            title = "아이디",
                            imeAction = ImeAction.Next,
                            onImeAction = {
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                            isTitle = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        MainTextField(
                            value = password,
                            onValueChange = { password = it },
                            hint = "비밀번호",
                            error = when (uiState.value.signUpState) {
                                is SignUpUiState.Error -> true
                                else -> false
                            },
                            title = "비밀번호",
                            imeAction = ImeAction.Done,
                            onImeAction = {
                                focusManager.clearFocus()
                            },
                            isTitle = true
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                AuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    hint = "이메일",
                    error = uiState.value.emailUiState is EmailUiState.Error,
                    verify = { viewModel.emailRequest(email) },
                    showTimer = uiState.value.emailUiState == EmailUiState.Success,
                    done = done,
                    isLoading = uiState.value.emailUiState == EmailUiState.Loading,
                    enable = email.isNotEmpty(),
                    title = "이메일",
                    isTitle = true,
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.value.codes.forEachIndexed { index, code ->
                        val currentValue = code

                        AnimatedVisibility(
                            visible = buttonLevel == 1,
                            enter = slideInVertically(
                                initialOffsetY = { fullHeight -> -fullHeight },
                                animationSpec = tween(
                                    durationMillis = 300,
                                    delayMillis = 30 + index * 10,
                                    easing = LinearOutSlowInEasing
                                )
                            ),
                            modifier = Modifier.weight(1F)
                        ) {
                            CodeTextField(
                                value = currentValue,
                                onValueChange = { newValue ->
                                    viewModel.updateCode(index, newValue)
                                    if (newValue.length == 1) {
                                        focusManager.moveFocus(FocusDirection.Right)
                                    }
                                    else if (newValue.isEmpty() && currentValue.isNotEmpty()) {
                                        focusManager.moveFocus(FocusDirection.Left)
                                    }
                                }
                            )
                        }
                    }

                }

//                MainTextField(
//                    value = authCode,
//                    onValueChange = { authCode = it },
//                    hint = "인증코드",
//                    error = verifyState is VerifyUiState.Error,
//                    title = "",
//                    isTitle = false,
//                )
//                Spacer(Modifier.height(8.dp))
//                MainTextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    hint = "비밀번호",
//                    error = verifyState is VerifyUiState.Error,
//                    title = "",
//                    isTitle = false,
//                )
//                Spacer(Modifier.height(8.dp))
//                MainTextField(
//                    value = username,
//                    onValueChange = { username = it },
//                    hint = "아이디",
//                    error = verifyState is VerifyUiState.Error,
//                    title = "",
//                    isTitle = false,
//                )
            }

            MainButton(
                onClick = {
                    when (buttonLevel) {
                        0 -> viewModel.emailRequest(email = email)
                        1 -> viewModel.verifyEmail(code = uiState.value.codes.joinToString(""), email = email)
                        2 -> viewModel.signUp(
                            username = username,
                            email = email,
                            password = password,
                            registrationToken = (uiState.value.verifyUiState as VerifyUiState.Success).registrationToken
                        )
                    }
                },
                text = when (buttonLevel) {
                    0 -> "인증하기"
                    1 -> "인증완료"
                    2 -> "회원가입"
                    else -> "다음"
                },
                enable = email.isNotEmpty(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(bottom = 16.dp),
                loading = uiState.value.emailUiState == EmailUiState.Loading
            )
        }
    }
}
