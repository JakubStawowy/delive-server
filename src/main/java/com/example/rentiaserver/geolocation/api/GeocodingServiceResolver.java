package com.example.rentiaserver.geolocation.api;

import com.example.rentiaserver.base.exception.UnsupportedArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GeocodingServiceResolver {

    private final Map<LocationType, IGeocodingService> geocodingServicesMap = new HashMap<>();

    @Autowired
    public GeocodingServiceResolver(
            List<IGeocodingService> geocodingServices,
            @Value("${geocoding.mode.emergency}") String emergencyGeocodingMode) {

        geocodingServices.stream()
                .filter(service -> Boolean.valueOf(emergencyGeocodingMode).equals(service.isEmergencyService()))
                .forEach(service -> geocodingServicesMap.put(service.getAcceptedLocationType(), service));
    }

    public IGeocodingService resolveGeocodingService(LocationType locationType) throws UnsupportedArgumentException {
        return Optional.ofNullable(geocodingServicesMap.get(locationType))
                .orElseThrow(() -> new UnsupportedArgumentException(locationType.name()));
    }
}
