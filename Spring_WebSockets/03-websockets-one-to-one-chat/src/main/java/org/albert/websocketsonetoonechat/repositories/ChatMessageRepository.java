package org.albert.websocketsonetoonechat.repositories;

import org.albert.websocketsonetoonechat.model.chatmessage.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    //    @Query("db.chatMessage.find({chatId: ?0})") // this query is finding ALL messages, not just the chatId message
    List<ChatMessage> findAllByChatId(String chatId);

    // this query doesn't work for some reason
    @Query(
            value = """
            { $or: [{ 'senderId':?0, 'recipientId':?1 }, { 'senderId':?1, 'recipientId':?0 }] }
            """,
            sort = "{ 'instant' : -1 }"
    )
    List<ChatMessage> findLastMessage(String senderId, String recipientId);

//    default ChatMessage findLastMessage(String senderId, String recipientId) {
//        final List<ChatMessage> listSenderId = findLastSenderIdMessage(senderId);
//
//        final ChatMessage lastSenderIdMessage = (ChatMessage) listSenderId.get(0);
//        final ChatMessage lastRecipientIdMessage = findLastRecipientIdMessage(recipientId);
//
//        final int i = lastSenderIdMessage.getInstant().compareTo(lastRecipientIdMessage.getInstant());
//        return i < 0 ? lastRecipientIdMessage : lastSenderIdMessage;
//    }
//
//    @Query(
//            value = "{ 'senderId' : ?0 }",
//            sort = "{ 'instant' : -1 }"
//    )
//    List<ChatMessage> findLastSenderIdMessage(String senderId);
//
//    @Query(
//            value = "{ 'senderId' : ?0 }",
//            sort = "{ 'instant' : -1 }"
//    )
//    List<ChatMessage> findLastRecipientIdMessage(String recipientId) ;
}
