package com.pushit.api.webSocket.application.middleware

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class NoCsrfChannelInterceptor : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        // No-op
        return message
    }
}