package com.example.rentiaserver.order.controller;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.base.exception.LocationNotFoundException;
import com.example.rentiaserver.base.exception.UnsupportedArgumentException;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.order.service.OrderService;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.mockito.Mockito.verify;

class OrderSaveControllerTest {

    @Mock
    private OrderService orderService;

    private OrderSaveController orderSaveController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderSaveController = new OrderSaveController(orderService);
    }

    @Test
    void shouldCallSaveOrder()
            throws UnsupportedArgumentException,
            EntityNotFoundException,
            ParseException,
            IOException,
            InterruptedException,
            LocationNotFoundException {

        // Given
        HttpServletRequest testHttpServletRequest = new MockHttpServletRequest();
        OrderTo testOrder = new OrderTo();
        testOrder.setAuthorId(1L);

        // When
        orderSaveController.saveOrder(testOrder, testHttpServletRequest);

        // Then
        verify(orderService).saveOrder(testOrder);
    }
}