package com.pushit.api.security.domain.model

data class User(
    val email: String,
    val password: String,
    var roles : List<String>
)
