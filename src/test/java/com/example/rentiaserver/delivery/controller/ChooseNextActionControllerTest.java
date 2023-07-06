package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.delivery.api.DeliveryState;
import com.example.rentiaserver.delivery.service.DeliveryService;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;


class ChooseNextActionControllerTest {

    @Mock
    private DeliveryService deliveryService;

    private ChooseNextActionController chooseNextActionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chooseNextActionController = new ChooseNextActionController(deliveryService);
    }

    @Test
    void shouldCallDeliveryService() throws Exception {
        // Given
        DeliveryState deliveryState = DeliveryState.STARTED;
        Long orderAuthorId = 1L;
        Long delivererId = 2L;
        HttpServletRequest request = new MockHttpServletRequest();


        try (MockedStatic<AuthenticationHelper> authenticationMock = mockStatic(AuthenticationHelper.class)) {

            authenticationMock.when(() -> AuthenticationHelper.getUserId(request))
                    .thenReturn(orderAuthorId);

            // When
            chooseNextActionController.getNextActionNames(
                    deliveryState,
                    orderAuthorId,
                    delivererId,
                    request);

            // Then
            verify(deliveryService).getNextActions(
                    deliveryState,
                    orderAuthorId,
                    delivererId,
                    orderAuthorId);
        }

    }


}