package com.narsha.buildlab_android.feature.screen.start

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
            .background(color = ColorTheme.colors.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.buildlab_text),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "직접 만든 AI를 경험해 보세요",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorTheme.colors.black,
                )
                Spacer(modifier = Modifier.height(105.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .align(alignment = Alignment.BottomCenter),
            ) {
                MainButton(
                    onClick = {
                        show = !show
                    },
                    text = "로그인",
                    enable = true,
                    loading = false
                )
                Spacer(modifier = Modifier.height(14.dp))
                SecondButton(
                    onClick = {
                        navigateToSignUp()
                    },
                    text = "회원가입"
                )
            }
        }
        if (show) {
            ModalBottomSheet(
                onDismissRequest = { show = false },
                sheetState = bottomSheet,
                containerColor = ColorTheme.colors.background
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .navigationBarsPadding()
                ) {
                    Text("로그인 방법 선택", fontSize = 16.sp, color = ColorTheme.colors.gray.dark)
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
                            color = ColorTheme.colors.gray.dark,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "계정 찾기",
                            color = ColorTheme.colors.primary.normal,
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
