package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.delivery.to.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatMessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/send")
    public void get(@Payload ChatMessage chatMessage) {
        simpMessagingTemplate.convertAndSend( "/topic/" + chatMessage.getUser(),
                new ChatMessage(chatMessage.getValue(), chatMessage.getToken()));
    }

}
