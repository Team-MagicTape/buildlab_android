package com.narsha.network.auth.model.request

data class LoginRequest (
    val email: String,
    val password: String
)