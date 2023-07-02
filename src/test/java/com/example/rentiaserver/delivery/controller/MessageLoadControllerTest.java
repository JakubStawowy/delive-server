package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.delivery.service.DeliveryService;
import com.example.rentiaserver.delivery.service.MessageService;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

class MessageLoadControllerTest {

    private static final HttpServletRequest TEST_HTTP_SERVLET_REQUEST = new MockHttpServletRequest();

    private static final long TEST_USER_ID = 1L;

    @Mock
    private MessageService messageService;

    private MessageLoadController messageLoadController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        messageLoadController = new MessageLoadController(messageService);
    }

    @Test
    public void shouldCallGetUserReceivedMessages() {

        // Given
        try (MockedStatic<AuthenticationHelper> authenticationMock = mockStatic(AuthenticationHelper.class)) {

            authenticationMock.when(() -> AuthenticationHelper.getUserId(TEST_HTTP_SERVLET_REQUEST))
                    .thenReturn(TEST_USER_ID);

            // When
            messageLoadController.getReceivedMessages(TEST_HTTP_SERVLET_REQUEST);

            // Then
            verify(messageService).getUserReceivedMessages(TEST_USER_ID);
        }
    }

    @Test
    public void shouldCallGetUserSentMessages() {

        // Given
        try (MockedStatic<AuthenticationHelper> authenticationMock = mockStatic(AuthenticationHelper.class)) {

            authenticationMock.when(() -> AuthenticationHelper.getUserId(TEST_HTTP_SERVLET_REQUEST))
                    .thenReturn(TEST_USER_ID);

            // When
            messageLoadController.getSentMessages(TEST_HTTP_SERVLET_REQUEST);

            // Then
            verify(messageService).getUserSentMessages(TEST_USER_ID);
        }
    }
}
