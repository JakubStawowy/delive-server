package com.example.rentiaserver.geolocation.util;

import com.example.rentiaserver.geolocation.model.to.LocationTo;

public class LocationValidator {

    public static void validateLocation(LocationTo location) {
        validateLatitude(location.getLatitude());
        validateLongitude(location.getLongitude());
    }

    private static void validateLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude value: " + latitude);
        }
    }

    private static void validateLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid longitude value: " + longitude);
        }
    }
}
