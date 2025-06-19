package com.narsha.buildlab_android.feature.screen.start

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.narsha.buildlab_android.BuildLabApplication
import com.narsha.ui.R
import com.narsha.ui.component.button.MainButton
import com.narsha.ui.component.button.SecondButton
import com.narsha.ui.theme.color.ColorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit,
    viewModel: SignViewModel = viewModel()
) {
    val context = BuildLabApplication.getContext()
    val uiState by viewModel.uiState.collectAsState()

    var show by remember { mutableStateOf(false) }
    val bottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        Log.d("GoogleLogin", "StartScreen: 1번")
        viewModel.login(activityResult = it) {
            Log.d("GoogleLogin", "StartScreen: 성공")
            Toast.makeText(context, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorTheme.colors.bg)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
            ) {
                Image(
                    painter = painterResource(R.drawable.buildlabicon),
                    contentDescription = null,
                    modifier = Modifier.width(60.dp)
                )
                Spacer(Modifier.height(38.dp))
                Text(
                    text = "직접 만든 AI를 경험해 보세요",
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600),
                    color = ColorTheme.colors.black
                )
                Spacer(Modifier.height(200.dp))
            }
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 12.dp)
                    .align(alignment = Alignment.BottomCenter)
            ) {
                MainButton(
                    enable = uiState !is StartUiState.Loading,
                    text = if (uiState is StartUiState.Loading) "로딩 중..." else "로그인",
                    onClick = {
                        show = true
                    },
                    loading = false
                )
                Spacer(Modifier.height(12.dp))
                SecondButton(
                    text = "회원가입",
                    onClick = {
                        navigateToSignUp()
                    }
                )
                Spacer(Modifier.height(22.dp))
            }
        }
        if (show) {
            ModalBottomSheet(
                onDismissRequest = { show = false },
                sheetState = bottomSheet,
                containerColor = ColorTheme.colors.bg
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .navigationBarsPadding()
                ) {
                    Text("로그인 방법 선택", fontSize = 16.sp, color = ColorTheme.colors.textGray)
                    Spacer(Modifier.height(20.dp))
                    Row {
                        Image(
                            painter = painterResource(R.drawable.googlelogin),
                            contentDescription = null,
                            modifier = Modifier
                                .size(46.dp)
                                .clickable(enabled = uiState !is StartUiState.Loading) {
                                    val googleSignInOptions = GoogleSignInOptions
                                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestIdToken("220144788316-gkhhkvggtoaehlj2i2pg54t5lvchdmer.apps.googleusercontent.com")
                                        .build()

                                    val googleSignInClient =
                                        GoogleSignIn.getClient(context, googleSignInOptions)
                                    launcher.launch(googleSignInClient.signInIntent)
                                }
                        )
                        Spacer(Modifier.width(26.dp))
                        Image(
                            painter = painterResource(R.drawable.githublogin),
                            contentDescription = null,
                            modifier = Modifier.size(46.dp)
                        )
                    }
                    Spacer(Modifier.height(20.dp))
                    SecondButton(
                        onClick = {
                            navigateToLogin()
                        },
                        text = "이메일로 로그인",
                        buttonIcon = painterResource(R.drawable.emailicon)
                    )
                    Spacer(Modifier.height(18.dp))
                    Row {
                        Text(
                            text = "계정이 기억나지 않나요?",
                            color = ColorTheme.colors.placeHolder,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "계정 찾기",
                            color = ColorTheme.colors.liteMain,
                            fontSize = 14.sp,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                    Spacer(Modifier.height(36.dp))
                }
            }
        }
    }
}
