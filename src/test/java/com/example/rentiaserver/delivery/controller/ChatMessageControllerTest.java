package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.delivery.model.to.ChatMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.verify;

class ChatMessageControllerTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    private ChatMessageController chatMessageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chatMessageController = new ChatMessageController(simpMessagingTemplate);
    }

    @Test
    void get_ShouldSendChatMessageToTopic() {
        // Given
        ChatMessage chatMessage = ChatMessage.builder()
                .user("user1")
                .value("Hello, world!")
                .build();

        String expectedDestination = "/topic/" + chatMessage.getUser();

        // When
        chatMessageController.get(chatMessage);

        // Then
        verify(simpMessagingTemplate).convertAndSend(expectedDestination, chatMessage);
    }
}
