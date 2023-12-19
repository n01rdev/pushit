package com.pushit.api.webSocket.application.middleware

import com.pushit.api.security.application.service.token.JwtService
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class JwtChannelInterceptor(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) : ChannelInterceptor {
    companion object {
        private const val AUTHORIZATION_PREFIX = "Bearer "
    }

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
        if (StompCommand.CONNECT == accessor?.command) {
            val authHeader = accessor.getFirstNativeHeader("Authorization")
            if (authHeader != null && authHeader.startsWith(AUTHORIZATION_PREFIX)) {
                val token = authHeader.substring(AUTHORIZATION_PREFIX.length)
                val userEmail = jwtService.extractUserEmail(token)
                val userDetails = userDetailsService.loadUserByUsername(userEmail)
                if (jwtService.isTokenValid(token, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    accessor.user = authToken
                }
            }
        }
        return message
    }
}