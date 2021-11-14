package com.example.rentiaserver.maps.to;

import com.example.rentiaserver.data.api.BaseEntityTo;

public class LocationTo extends BaseEntityTo {

    private final Double latitude;
    private final Double longitude;
    private final String address;

    public LocationTo(Long id, String createdAt, Double latitude, Double longitude, String address) {
        super(id, createdAt);
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
}
