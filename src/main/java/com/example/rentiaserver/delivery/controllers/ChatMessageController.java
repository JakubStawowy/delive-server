package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.delivery.to.ChatMessage;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMessageController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatMessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

//    @MessageMapping("/send")
//    @SendTo("/topic")
//    public ChatMessage get(@Payload ChatMessage chatMessage/*, SimpMessageHeaderAccessor accessor*/) {
//        System.out.println(JsonWebTokenHelper.getRequesterId(chatMessage.getToken()));
//        System.out.println("Wysłana wiadomosc: " + chatMessage.getValue());
//        return new ChatMessage(chatMessage.getValue());
//    }
    @MessageMapping("/send")
    public void get(@Payload ChatMessage chatMessage/*, SimpMessageHeaderAccessor accessor*/) {
        System.out.println("Wysłana wiadomosc: " + chatMessage.getValue());
        simpMessagingTemplate.convertAndSend( "/topic/" + chatMessage.getUser(),
                new ChatMessage(chatMessage.getValue()));

        // TODO NOT WORKING IDK WHY
//        simpMessagingTemplate.setUserDestinationPrefix("/user/");
//        simpMessagingTemplate.convertAndSendToUser(chatMessage.getUser(), "/topic",
//                new ChatMessage(chatMessage.getValue()));
    }

}
