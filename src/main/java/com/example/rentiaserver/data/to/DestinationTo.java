package com.example.rentiaserver.data.to;

import java.io.Serializable;

public class DestinationTo implements Serializable {

    private final Double latitude;
    private final Double longitude;
    private final String address;

    public DestinationTo(Double latitude, Double longitude, String address) {
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
