package com.pushit.api.security.application.service.token

import com.pushit.api.security.domain.service.token.IJwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService: IJwtService {

    private val SECRET_KEY: String = "5ea78e08a6aa8bc2c5dda32c3be471f4e4525ec06523b9b8ea8fda8691d1e9d3" // TODO: Move to secure place | No time for implementing in demo

    override fun generateToken(userDetails: UserDetails): String {
        return generateToken(hashMapOf(), userDetails)
    }

    fun generateToken(
        extraClaims : Map<String, Any>,
        userDetails: UserDetails
    ): String {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 3600 * 1000)) // 1 Hour
            .signWith(signInKey(), SignatureAlgorithm.HS256) // TODO: Migrate to a new non-deprecated version | No time for implementing in demo
            .compact()
    }

    override fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val userEmail = extractUserEmail(token)
        return userEmail == userDetails.username && !isTokenExpired(token)
    }

    override fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    override fun extractUserEmail(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    override fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return  Jwts // TODO: Migrate to a new non-deprecated version | No time for implementing in demo
            .parser()
            .setSigningKey(signInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun signInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}