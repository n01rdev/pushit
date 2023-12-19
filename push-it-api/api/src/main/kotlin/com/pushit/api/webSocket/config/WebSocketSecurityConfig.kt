package com.pushit.api.webSocket.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager

@Configuration
@EnableWebSocketSecurity
class WebSocketSecurityConfig  {

    // https://github.com/jhipster/generator-jhipster/issues/20404 - No way to disable CSRF for Websocket

    @Bean
    fun messageAuthorizationManager(messages: MessageMatcherDelegatingAuthorizationManager.Builder): AuthorizationManager<Message<*>> {
        messages
            .simpDestMatchers("/topic/**").denyAll()
            .simpSubscribeDestMatchers("/topic/**").denyAll()
            .anyMessage().authenticated()
        return messages.build()
    }
}