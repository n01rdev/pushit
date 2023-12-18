package com.pushit.api.security.application.response

data class AuthResponse(
    val token: String,
    val uuid: String,
)
