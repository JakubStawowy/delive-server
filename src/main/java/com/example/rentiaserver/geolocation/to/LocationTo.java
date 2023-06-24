package com.example.rentiaserver.geolocation.to;

import com.example.rentiaserver.data.api.BaseEntityTo;
import com.example.rentiaserver.geolocation.api.LocationType;

public class LocationTo extends BaseEntityTo {

    private final Double latitude;
    private final Double longitude;
    private final String address;

    private LocationType locationType;

    public LocationTo(
            Long id,
            String createdAt,
            Double latitude,
            Double longitude,
            String address,
            LocationType locationType) {

        super(id, createdAt);
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locationType = locationType;
    }

    public void setLocationType() {
        if (address != null && longitude != null && latitude != null) {
            locationType = LocationType.FULL;
        } else if (address != null) {
            locationType = LocationType.ADDRESS;
        } else if (longitude != null && latitude != null) {
            locationType = LocationType.COORDINATES;
        }
    }

    public LocationType getLocationType() {
        return locationType;
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

    public static InitialBuilder getBuilder() {
        return new Builder();
    }

    public interface InitialBuilder {
        Builder setAddress(String address);
        CoordinateBuilder setLatitude(Double latitude);
    }

    public interface CoordinateBuilder {
        Builder setLongitude(Double longitude);
    }

    public static class Builder implements InitialBuilder, CoordinateBuilder {

        private Long id;
        private String createdAt;
        private String address;
        private Double latitude;
        private Double longitude;

        private LocationType locationType;

        private Builder() {};

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setLocationType(LocationType locationType) {
            this.locationType = locationType;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return setLocationType(LocationType.ADDRESS);
        }

        public CoordinateBuilder setLatitude(Double latitude) {
            this.latitude = latitude;
            return setLocationType(LocationType.COORDINATES);
        }

        public Builder setLongitude(Double longitude) {
            this.longitude = longitude;
            return setLocationType(LocationType.COORDINATES);
        }

        public LocationTo build() {
            if (address != null && longitude != null && latitude != null) {
                setLocationType(LocationType.FULL);
            }

            return new LocationTo(
                    id,
                    createdAt,
                    latitude,
                    longitude,
                    address,
                    locationType);
        }
    }
}
