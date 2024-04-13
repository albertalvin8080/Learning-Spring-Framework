package org.albert.websocketsonetoonechat.services;

import lombok.RequiredArgsConstructor;
import org.albert.websocketsonetoonechat.model.user.Status;
import org.albert.websocketsonetoonechat.model.user.User;
import org.albert.websocketsonetoonechat.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final UserRepository userRepository;
    private final ChatMessageService chatMessageService;

    public User save(User user, Status status) {
        user.setStatus(status);
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public User disconnect(User user) {
        user.setStatus(Status.OFFLINE);
        return userRepository.save(user);
    }

    public String findLastMessage(String senderId, String recipientId) {
        return chatMessageService.findLastMessage(senderId, recipientId);
    }
}
