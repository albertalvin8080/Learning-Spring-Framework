package org.albert.websocketsonetoonechat.repositories;

import org.albert.websocketsonetoonechat.model.chatmessage.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String>
{
    @Query("db.chatmessage.find({chatId: ?0})")
    List<ChatMessage> findAllByChatId(String chatId);
}
