//package org.albert.websocketsonetoonechat.listeners;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.albert.websocketsonetoonechat.model.user.Status;
//import org.albert.websocketsonetoonechat.model.user.User;
//import org.albert.websocketsonetoonechat.services.MyUserService;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class WebSocketListener {
//
//    private final MyUserService userService;
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @EventListener
//    public void handlerUserDisconnectEvent(
//            SessionDisconnectEvent event
//    ) {
//        StompHeaderAccessor headerAccessor =
//                StompHeaderAccessor.wrap(event.getMessage());
//        final String username = (String) headerAccessor.getSessionAttributes().get("username");
//
//        if(username == null) return;
//
//        final User user = User.builder()
//                .nickName(username)
//                .build();
//        userService.save(user, Status.OFFLINE);
//
//        log.info("User disconnected: {}", username);
//        messagingTemplate.convertAndSend("/user/topic/public", user);
//    }
//}
