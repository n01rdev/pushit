package com.pushit.api.webSocket.application.middleware

import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.DefaultCsrfToken
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor

class CsrfTokenHandshakeInterceptor(private val csrfTokenRepository: CsrfTokenRepository) : HandshakeInterceptor {
    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>): Boolean {

        val httpRequest = (request as ServletServerHttpRequest).servletRequest
        var token = httpRequest.getAttribute(CsrfToken::class.java.name) as CsrfToken?

        if (token == null) {
            token = csrfTokenRepository.generateToken(httpRequest)
            httpRequest.setAttribute(CsrfToken::class.java.name, token)
        }

        if (token != null) {
            token = DefaultCsrfToken(token.headerName, token.parameterName, token.token)
        }

        token?.let {
            attributes[CsrfToken::class.java.name] = it
        }

        return true
    }

    override fun afterHandshake(request: ServerHttpRequest, response: ServerHttpResponse, wsHandler: WebSocketHandler, exception: Exception?) {}
}