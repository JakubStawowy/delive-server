package com.example.rentiaserver.geolocation.tool;

import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapGeolocationHelperTest {

    @Test
    public void testCalculateMidpoint() {

        // Given
        double firstCoordinate = 10.0;
        double secondCoordinate = 20.0;

        double expectedMidpoint = 15.0;

        // When
        double actualMidpoint = MapGeolocationHelper.calculateMidpoint(firstCoordinate, secondCoordinate);

        // Then
        assertEquals(expectedMidpoint, actualMidpoint);
    }

    @Test
    public void testCalculateZoomLevel() {

        // Given
        LocationTo locationFrom = LocationTo.getBuilder()
                .setLatitude(52.520008)
                .setLongitude(13.404954)
                .build();

        // When
        LocationTo locationTo = LocationTo.getBuilder()
                .setLatitude(51.5074)
                .setLongitude(-0.1278)
                .build();

        long expectedZoomLevel = 4;
        long actualZoomLevel = MapGeolocationHelper.calculateZoomLevel(locationFrom, locationTo);

        // Then
        assertEquals(expectedZoomLevel, actualZoomLevel);
    }
}
