package com.narsha.network.core.remote

import android.util.Log
import com.narsha.datastore.tokenManager.TokenManager
import com.narsha.network.core.remote.RetrofitClient.authService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ResponseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val statusCode = response.code

        when (statusCode) {
            400 -> {
                Log.e("API 오류", "잘못된 요청입니다.")
            }

            401, 402 -> {

                val refToken = TokenManager.getRefToken()

                if (refToken.isNullOrEmpty()) {
                    return response
                }

                runBlocking {
                    try {
                        TokenManager.clearToken()
                        try {
                            val tokenResponse = authService.refresh(refToken)

                            TokenManager.saveAccToken(
                                tokenResponse.accessToken
                            )

                            TokenManager.saveRefToken(
                                tokenResponse.refreshToken
                            )
                            tokenResponse.accessToken
                        } catch (_: Exception) {
                            null
                        }
                    } catch (_: Exception) {
                        null
                    }
                }

                return response
            }

            403 -> {
                Log.e("인터셉터", "권한이 없습니다.")
            }
        }
        return response
    }
}
