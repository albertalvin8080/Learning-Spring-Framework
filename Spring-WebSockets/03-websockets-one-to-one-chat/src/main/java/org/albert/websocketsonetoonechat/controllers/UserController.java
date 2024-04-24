package org.albert.websocketsonetoonechat.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.albert.websocketsonetoonechat.model.user.Status;
import org.albert.websocketsonetoonechat.model.user.User;
import org.albert.websocketsonetoonechat.services.MyUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MyUserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic/public")
    public User addUser(
            @Payload User user,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        log.info("User connected: {}", user.getNickName());
//        headerAccessor.getSessionAttributes().put("username", user.getNickName());
        return userService.save(user, Status.ONLINE);
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic/public")
    public User disconnectUser(
            @Payload User user
    ) {
        log.info("User disconnected: {}", user.getNickName());
        return userService.disconnect(user);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/last-message/{senderId}/{recipientId}")
    public ResponseEntity<String> lastMessage(
            @PathVariable String senderId,
            @PathVariable String recipientId
    ) {
        return ResponseEntity.ok(userService.findLastMessage(senderId, recipientId));
    }

//    @ResponseBody
//    @GetMapping("/save")
//    public void save() {
//        userService.save(User.builder().nickName("Pedro Bial").status(Status.ONLINE).build());
//    }
}
