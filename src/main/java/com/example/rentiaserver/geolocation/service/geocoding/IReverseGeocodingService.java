package com.example.rentiaserver.geolocation.service.geocoding;

import com.example.rentiaserver.geolocation.api.IGeocodingService;
import com.example.rentiaserver.geolocation.api.LocationType;

interface IReverseGeocodingService extends IGeocodingService {

    default LocationType getAcceptedLocationType() {
        return LocationType.COORDINATES;
    }
}
