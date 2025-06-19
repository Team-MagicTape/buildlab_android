package com.narsha.network.auth.model.request

data class VerifyEmailRequest(
    val email: String,
    val code: String
)