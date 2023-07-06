package com.example.rentiaserver.geolocation.util;

import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocationValidatorTest {

    @Test
    public void shouldNotThrowWhenCorrectCoordinates() {

        // Given
        LocationTo location = LocationTo.getBuilder()
                .setLatitude(52.520008)
                .setLongitude(13.404954)
                .build();

        // When + Then
        Assertions.assertDoesNotThrow(() -> LocationValidator.validateLocation(location));
    }

    @Test
    public void shouldThrowWhenInvalidLatitude() {

        // Given
        LocationTo location = LocationTo.getBuilder()
                .setLatitude(-100.0)
                .setLongitude(13.404954)
                .build();

        // When + Then
        assertThrows(IllegalArgumentException.class, () -> LocationValidator.validateLocation(location));
    }

    @Test
    public void shouldThrowWhenInvalidLongitude() {

        // Given
        LocationTo location = LocationTo.getBuilder()
                .setLatitude(52.520008)
                .setLongitude(200.0)
                .build();

        // When + Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> LocationValidator.validateLocation(location));
    }
}
