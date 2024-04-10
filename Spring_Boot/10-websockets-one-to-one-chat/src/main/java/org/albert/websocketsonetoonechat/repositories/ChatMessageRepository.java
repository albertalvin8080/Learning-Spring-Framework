package org.albert.websocketsonetoonechat.repositories;

import org.albert.websocketsonetoonechat.model.chatmessage.ChatMessage;
import org.albert.websocketsonetoonechat.model.chatroom.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String>
{
    
}
