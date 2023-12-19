package com.pushit.api.webSocket.config

import com.pushit.api.webSocket.application.middleware.CsrfTokenHandshakeInterceptor
import com.pushit.api.webSocket.application.middleware.JwtChannelInterceptor
import com.pushit.api.webSocket.application.middleware.NoCsrfChannelInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val jwtChannelInterceptor: JwtChannelInterceptor,
    private val noCsrfChannelInterceptor: NoCsrfChannelInterceptor
) : WebSocketMessageBrokerConfigurer {

    private val csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse() //TODO: Implement Autowired

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/topic")
        config.setApplicationDestinationPrefixes("/app")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/websocket")
            .setAllowedOrigins("*")
            .addInterceptors(CsrfTokenHandshakeInterceptor(
                csrfTokenRepository
            ))

        registry.addEndpoint("/websocket")
            .setAllowedOrigins("*")
            .addInterceptors(CsrfTokenHandshakeInterceptor(
                csrfTokenRepository
            ))
            .withSockJS()
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(jwtChannelInterceptor, noCsrfChannelInterceptor)
    }
}