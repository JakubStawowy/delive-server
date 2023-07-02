package com.example.rentiaserver.geolocation.controller;

import com.example.rentiaserver.geolocation.service.GeolocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class MapControllerTest {

    @Mock
    private GeolocationService geolocationService;

    private MapController mapController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapController = new MapController(geolocationService);
    }

    @Test
    void shouldCallGetMapProperties() {

        // Given
        double fromLatitude = 37.12345;
        double toLatitude = 37.12345;
        double fromLongitude = -122.98765;
        double toLongitude = -122.98765;
        double mapWidth = 10;
        double mapHeight = 10;

        // When
        mapController.getMapProperties(
                fromLatitude,
                toLatitude,
                fromLongitude,
                toLongitude,
                mapWidth,
                mapHeight);

        // Then
        verify(geolocationService).getMapProperties(
                fromLatitude,
                toLatitude,
                fromLongitude,
                toLongitude,
                mapWidth,
                mapHeight);
    }
}