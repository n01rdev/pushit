package com.pushit.api.security.domain.service.token

import io.jsonwebtoken.Claims //TODO: Remove dependencu making domain agnostic & Implementing Mapper | No time for implementing in demo
import org.springframework.security.core.userdetails.UserDetails

interface IJwtService {
    fun generateToken(userDetails: UserDetails): String
    fun extractUserEmail(token: String): String
    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean
    fun isTokenExpired(token: String): Boolean
}