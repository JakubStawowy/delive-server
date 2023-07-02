package com.example.rentiaserver.geolocation.tool;

import com.example.rentiaserver.geolocation.api.LocationType;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HaversineDistanceCalculatorTest {

    @Test
    public void testGetDistance() {

        // Given
        LocationTo initialLocation = LocationTo.getBuilder()
                .setLatitude(52.520008)
                .setLongitude(13.404954)
                .build();

        LocationTo finalLocation = LocationTo.getBuilder()
                .setLatitude(51.5074)
                .setLongitude(-0.1278)
                .build();

        double expectedDistance = 931.5663334126956;

        // When
        double actualDistance = HaversineDistanceCalculator.getDistance(initialLocation, finalLocation);

        // Then
        assertEquals(expectedDistance, actualDistance, 0.001);
    }
}
