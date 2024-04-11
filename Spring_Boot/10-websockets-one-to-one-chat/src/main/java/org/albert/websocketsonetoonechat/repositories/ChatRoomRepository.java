package org.albert.websocketsonetoonechat.repositories;

import org.albert.websocketsonetoonechat.model.chatroom.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String>
{
//    @Query("db.chatRoom.find({senderId: ?0, recipientId: ?1})")
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
