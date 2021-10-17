package com.example.rentiaserver.maps.to;

import com.example.rentiaserver.data.api.BaseEntityTo;

public class LocationTo extends BaseEntityTo {

    private final Double latitude;
    private final Double longitude;
    private final String address;
    private final String locality;
    private final String country;

    public LocationTo(Long id, String createdAt, Double latitude, Double longitude, String address, String locality, String country) {
        super(id, createdAt);
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locality = locality;
        this.country = country;
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

    public String getLocality() {
        return locality;
    }

    public String getCountry() {
        return country;
    }
}
