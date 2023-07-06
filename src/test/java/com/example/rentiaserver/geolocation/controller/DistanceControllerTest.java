package com.example.rentiaserver.geolocation.controller;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.geolocation.service.GeolocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class DistanceControllerTest {

    private static final long TEST_ORDER_ID = 1L;

    private static final double TEST_CLIENT_LATITUDE = 37.12345;

    private static final double TEST_CLIENT_LONGITUDE = -122.98765;

    @Mock
    private GeolocationService geolocationService;

    private DistanceController distanceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        distanceController = new DistanceController(geolocationService);
    }

    @Test
    void shouldCallIsClientInRequiredDestinationArea() throws EntityNotFoundException {

        // Given + When
        distanceController.isClientInRequiredDestinationArea(
                TEST_ORDER_ID,
                TEST_CLIENT_LATITUDE,
                TEST_CLIENT_LONGITUDE);

        // Then
        verify(geolocationService).isClientInRequiredDestinationArea(
                TEST_ORDER_ID,
                TEST_CLIENT_LATITUDE,
                TEST_CLIENT_LONGITUDE);
    }

    @Test
    void shouldCallGetClientDistanceFromOrderDestination() throws EntityNotFoundException {

        // Given + When
        distanceController.getClientDistanceFromOrderDestination(
                TEST_ORDER_ID,
                TEST_CLIENT_LATITUDE,
                TEST_CLIENT_LONGITUDE);

        // Then
        verify(geolocationService).getClientDistanceFromOrderDestination(
                TEST_ORDER_ID,
                TEST_CLIENT_LATITUDE,
                TEST_CLIENT_LONGITUDE);
    }
}