package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.delivery.service.DeliveryService;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mockStatic;

class DeliveryLoadControllerTest {

    private static final HttpServletRequest TEST_HTTP_SERVLET_REQUEST = new MockHttpServletRequest();

    private static final long TEST_USER_ID = 1L;

    @Mock
    private DeliveryService deliveryService;

    private DeliveryLoadController deliveryLoadController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deliveryLoadController = new DeliveryLoadController(deliveryService);
    }

    @Test
    public void testGetDelivererDeliveries() {

        // Given
        try (MockedStatic<AuthenticationHelper> authenticationMock = mockStatic(AuthenticationHelper.class)) {

            authenticationMock.when(() -> AuthenticationHelper.getUserId(TEST_HTTP_SERVLET_REQUEST))
                    .thenReturn(TEST_USER_ID);

            // When
            deliveryLoadController.getDelivererDeliveries(TEST_HTTP_SERVLET_REQUEST);

            // Then
            verify(deliveryService).getAllDelivererDeliveryTos(TEST_USER_ID);
        }
    }

    @Test
    public void testGetPrincipalDeliveries() {

        // Given
        try (MockedStatic<AuthenticationHelper> authenticationMock = mockStatic(AuthenticationHelper.class)) {

            authenticationMock.when(() -> AuthenticationHelper.getUserId(TEST_HTTP_SERVLET_REQUEST))
                    .thenReturn(TEST_USER_ID);

            // When
            deliveryLoadController.getPrincipalDeliveries(TEST_HTTP_SERVLET_REQUEST);

            // Then
            verify(deliveryService).getAllPrincipalDeliveryTos(TEST_USER_ID);
        }
    }
}
