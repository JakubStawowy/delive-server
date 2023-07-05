package com.example.rentiaserver.order.controller;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class OrderLoadControllerTest {

    @Mock
    private OrderService orderService;

    private OrderLoadController orderLoadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderLoadController = new OrderLoadController(orderService);
    }

    @Test
    void shouldCallGetAllOrders() {

        // Given + When
        orderLoadController.getAllOrders();

        // Then
        verify(orderService).getAllOrderTos();
    }

    @Test
    void shouldCallGetOrderById() throws EntityNotFoundException {

        // Given
        long testOrderId = 1L;

        // When
        orderLoadController.getOrderById(testOrderId);

        // Then
        verify(orderService).getOrderToById(testOrderId);
    }

    @Test
    void shouldCallQueryOrderTos() {

        // Given
        String initialAddress = "";
        String finalAddress = "";
        String minimalSalary = "";
        String maxWeight = "";
        Boolean requireTransportWithClient = Boolean.TRUE;
        Boolean sortBySalary = Boolean.TRUE;
        Boolean sortByWeight = Boolean.TRUE;

        // When
        orderLoadController.getFilteredOrders(
                initialAddress,
                finalAddress,
                minimalSalary,
                maxWeight,
                requireTransportWithClient,
                sortBySalary,
                sortByWeight);

        // Then
        verify(orderService).queryOrderTos(
                initialAddress,
                finalAddress,
                minimalSalary,
                maxWeight,
                requireTransportWithClient,
                sortBySalary,
                sortByWeight);
    }
}