package com.narsha.network.auth.model.response


data class LoginResponse (
    val accessToken: String,
    val refreshToken: String
)