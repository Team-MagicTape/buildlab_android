package com.narsha.network.auth.model.request

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val registrationToken: String
)