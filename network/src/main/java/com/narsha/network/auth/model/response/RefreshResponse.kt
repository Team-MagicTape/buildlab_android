package com.narsha.network.auth.model.response

data class RefreshResponse (
    val accessToken: String,
    val refreshToken: String
)