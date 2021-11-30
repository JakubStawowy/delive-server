package com.example.rentiaserver.geolocation.to;

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

    public static class Builder {

        private Long id;
        private String createdAt;
        private String address;
        private Double latitude;
        private Double longitude;

        private Builder() {};

        public static Builder getBuilder() {
            return new Builder();
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public LocationTo build() {
            return new LocationTo(id, createdAt, latitude, longitude, address);
        }
    }
}
