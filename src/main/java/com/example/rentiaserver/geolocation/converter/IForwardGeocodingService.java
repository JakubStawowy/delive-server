package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.api.IGeocodingService;
import com.example.rentiaserver.geolocation.api.LocationType;

interface IForwardGeocodingService extends IGeocodingService {

    default LocationType getAcceptedLocationType() {
        return LocationType.ADDRESS;
    }
}
