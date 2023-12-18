package com.pushit.api.security.application.request

data class AuthRequest(
    val email: String,
    val password: String,
    val roles: List<String> = listOf()
)