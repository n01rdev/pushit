package com.pushit.api.security.domain.service.user

import com.pushit.api.security.application.response.AuthResponse // TODO: Remove this dependency for having domain layer implementation mapper
import com.pushit.api.security.domain.model.User

fun interface ICreateUserService {
    fun register(user: User): AuthResponse
}