package com.example.rentiaserver.order.controller;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class OrderDeleteControllerTest {

    @Mock
    private OrderService orderService;

    private OrderDeleteController orderDeleteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderDeleteController = new OrderDeleteController(orderService);
    }

    @Test
    void shouldCallArchiveById() throws EntityNotFoundException {

        // Given
        long testOrderId = 1L;

        // When
        orderDeleteController.deleteOrder(testOrderId);

        // Then
        verify(orderService).archiveById(testOrderId);
    }
}