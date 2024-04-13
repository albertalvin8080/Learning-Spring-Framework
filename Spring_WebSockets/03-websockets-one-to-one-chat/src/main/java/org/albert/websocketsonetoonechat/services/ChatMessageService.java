package org.albert.websocketsonetoonechat.services;

import lombok.RequiredArgsConstructor;
import org.albert.websocketsonetoonechat.model.chatmessage.ChatMessage;
import org.albert.websocketsonetoonechat.repositories.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository repository;

    public ChatMessage save(ChatMessage chatMessage) {
        final Optional<String> chatRoomId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true);

        chatMessage.setChatId(chatRoomId.orElseThrow());
        return repository.save(chatMessage);
    }

    public List<ChatMessage> findAllChatMessages(String senderId, String recipientId) {
        final Optional<String> chatRoomId =
                chatRoomService.getChatRoomId(senderId, recipientId, false);

        return chatRoomId
                .map(repository::findAllByChatId)
                .orElse(new ArrayList<>());
    }

    public String findLastMessage(String senderId, String recipientId) {
        final List<ChatMessage> lastMessage = repository
                .findLastMessage(senderId, recipientId);

        return lastMessage.isEmpty() ? "NO MESSAGES" : lastMessage.get(0).getContent();
    }
}
