package com.example.rentiaserver.geolocation.service.geocoding;

public enum GeocodingServiceLayer {

    FIRST("first"),

    SECOND("second");

    private final String code;

    GeocodingServiceLayer(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
