package com.pushit.api.webSocket.presentation.rest.v1

import com.pushit.api.posit.domain.model.Posit
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller


@Controller
class WebSocketController {

    @MessageMapping("/newPost")
    @SendTo("/topic/posits")
    fun sendPost(post: Posit): Posit {
        return post
    }
}
