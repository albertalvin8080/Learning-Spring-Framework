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
public class WebSocketListener {

    private final SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectedListener(
            SessionDisconnectEvent event
    ) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username == null) return;

        var chatMessage = ChatMessage
                .builder()
                .sender(username)
                .messageType(MessageType.LEAVE)
                .build();

        log.info("User disconnected: {}", username);
        messageSendingOperations.convertAndSend("/topic/public", chatMessage);
    }
}
