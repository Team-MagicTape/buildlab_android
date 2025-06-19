package com.narsha.network.auth.api

import com.narsha.network.BuildLabUrl
import com.narsha.network.auth.model.request.EmailRequest
import com.narsha.network.auth.model.request.LoginRequest
import com.narsha.network.auth.model.request.SignupRequest
import com.narsha.network.auth.model.request.VerifyEmailRequest
import com.narsha.network.auth.model.response.LoginResponse
import com.narsha.network.auth.model.response.RefreshResponse
import com.narsha.network.auth.model.response.SendEmailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST(BuildLabUrl.Auth.SEND)
    suspend fun sendEmail(@Body email: EmailRequest)

    @POST(BuildLabUrl.Auth.VERIFY)
    suspend fun verifyEmail(@Body request: VerifyEmailRequest): SendEmailResponse

    @POST(BuildLabUrl.Auth.SIGNUP)
    suspend fun signup(@Body request: SignupRequest)

    @POST(BuildLabUrl.Auth.REFRESH)
    suspend fun refresh(@Body refreshToken: String): RefreshResponse

    @POST(BuildLabUrl.Auth.LOGIN)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(BuildLabUrl.Auth.GOOGLE)
    suspend fun googleLogin(@Body code: String): LoginResponse
}
