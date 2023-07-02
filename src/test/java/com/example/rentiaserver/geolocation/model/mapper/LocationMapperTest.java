package com.example.rentiaserver.geolocation.model.mapper;

import com.example.rentiaserver.geolocation.model.po.LocationPo;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LocationMapperTest {

    @Test
    void testMapLocationPoToTo() {

        // Given
        LocationPo location = new LocationPo();
        location.setId(1L);
        location.setCreatedAt(new Date());
        location.setAddress("test_address");
        location.setLatitude(37.12345);
        location.setLongitude(-122.98765);

        // When
        LocationTo mappedLocation = LocationMapper.mapLocationPoToTo(location);

        // Then
        assertTrue(isCorrectlyMapped(mappedLocation, location));
    }

    private boolean isCorrectlyMapped(LocationTo mappedLocation, LocationPo location) {
        return mappedLocation.getId().equals(location.getId())
                && mappedLocation.getCreatedAt().equals(location.getCreatedAt())
                && mappedLocation.getAddress().equals(location.getAddress())
                && mappedLocation.getLongitude().equals(location.getLongitude())
                && mappedLocation.getLatitude().equals(location.getLatitude());
    }
}