package org.albert.websocketsonetoonechat.controllers;

import lombok.RequiredArgsConstructor;
import org.albert.websocketsonetoonechat.model.chatmessage.ChatMessage;
import org.albert.websocketsonetoonechat.services.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processChatMessage(
            @Payload ChatMessage chatMessage
    ) {
        final ChatMessage savedMessage =
                chatMessageService.save(chatMessage);

        // {recipientId}/queue/messages
        /* the complete path would be "user/{recipientId}/queue/messages"
         * because of the setUserDestinationPrefix("user") */
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                savedMessage
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findAllChatMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId
    ) {
        return ResponseEntity.ok(chatMessageService
                .findAllChatMessages(senderId, recipientId)
        );
    }
}
