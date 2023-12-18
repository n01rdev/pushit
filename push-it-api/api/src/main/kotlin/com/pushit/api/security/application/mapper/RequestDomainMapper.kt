package com.pushit.api.security.application.mapper

import com.pushit.api.security.application.request.AuthRequest
import com.pushit.api.security.domain.model.User
import org.springframework.stereotype.Component

@Component
class RequestDomainMapper {
    fun toDomain(request: AuthRequest): User {
        return User(
            email = request.email,
            password = request.password,
            roles = mutableListOf("USER") //TODO: Fix for not being hardcoded
        )
    }
}