package org.albert.websocketsonetoonechat.services;

import lombok.RequiredArgsConstructor;
import org.albert.websocketsonetoonechat.model.chatroom.ChatRoom;
import org.albert.websocketsonetoonechat.repositories.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(
            String senderId,
            String recipientId,
            boolean createChatRoomIfNotExists
    ) {
        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (!createChatRoomIfNotExists)
                        return Optional.empty();

                    var chatId = createChatRoom(senderId, recipientId);
                    return Optional.of(chatId);
                });
    }

    private String createChatRoom(String senderId, String recipientId) {
        // DO NOT invert this. The chats from both sides must have the same ids.
        final String chatId = String.format("%s_%s", recipientId, senderId);

        final ChatRoom cr1 = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        final ChatRoom cr2 = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        return chatId;
    }
}
