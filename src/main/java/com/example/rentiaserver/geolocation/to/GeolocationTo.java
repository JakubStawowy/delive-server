package com.example.rentiaserver.geolocation.to;

import java.io.Serializable;

public class GeolocationTo implements Serializable {

    private final double latitude;
    private final double longitude;
    private final long zoomLevel;

    public GeolocationTo(double latitude, double longitude, long zoomLevel) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoomLevel = zoomLevel;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public long getZoomLevel() {
        return zoomLevel;
    }
}
