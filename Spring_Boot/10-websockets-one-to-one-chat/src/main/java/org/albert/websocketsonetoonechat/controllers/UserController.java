package org.albert.websocketsonetoonechat.controllers;

import lombok.RequiredArgsConstructor;
import org.albert.websocketsonetoonechat.model.user.User;
import org.albert.websocketsonetoonechat.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User addUser(
            @Payload User user
    ) {
        return userService.save(user);
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public User disconnectUser(
            @Payload User user
    ) {
        return userService.disconnect(user);
    }

    @GetMapping("/online-users")
    public ResponseEntity<List<User>> onlineUsers() {
        return ResponseEntity.ok(userService.findOnlineUsers());
    }
}
