package org.albert.websocketsrealtimechatapp.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.albert.websocketsrealtimechatapp.model.ChatMessage;
import org.albert.websocketsrealtimechatapp.model.MessageType;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handlerWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        final String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username == null) return;

        log.info("User disconnected: {}", username);
        var chatMessage = ChatMessage
                .builder()
                .sender(username)
                .messageType(MessageType.LEAVE)
                .build();

        messageTemplate.convertAndSend("/topic/public", chatMessage);
    }
}
