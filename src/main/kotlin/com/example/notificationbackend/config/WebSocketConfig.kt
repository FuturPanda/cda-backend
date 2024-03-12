package com.example.notificationbackend.config

import io.r2dbc.spi.Result
import org.jboss.logging.Messages
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.socket.*
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.w3c.dom.Text

@Configuration
@EnableWebSocket
class WSConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(EventNotificationHandler(), "/api/v1/events/websocket").withSockJS()
    }
}

@Component
class EventNotificationHandler : TextWebSocketHandler(){
    private var sessionList : List<WebSocketSession> = mutableListOf()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        println(session)
    }

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        println(message)
        println("Handling")
        if (session !in sessionList)
            sessionList.addLast(session)
        broadcast(message)
    }

    private fun emit(session: WebSocketSession, message: TextMessage) {
        session.sendMessage(message)
    }

    private fun broadcast(message : TextMessage) = sessionList.forEach { emit (it, message) }
}

