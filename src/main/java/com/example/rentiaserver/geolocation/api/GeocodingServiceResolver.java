package com.example.rentiaserver.geolocation.api;

import com.example.rentiaserver.base.exception.UnsupportedArgumentException;
import com.example.rentiaserver.geolocation.service.geocoding.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.rentiaserver.base.ApplicationConstants.Properties.PROPERTY_GEOCODING_SERVICE_LAYER;

@Component
public class GeocodingServiceResolver {

    private final Map<LocationType, IGeocodingService> geocodingServicesMap = new HashMap<>();

    @Autowired
    public GeocodingServiceResolver(
            List<IGeocodingService> geocodingServices,
            @Value(PROPERTY_GEOCODING_SERVICE_LAYER) String geocodingServiceLayer) {

        geocodingServices.stream()
                .filter(service -> filterService(service, geocodingServiceLayer))
                .forEach(service -> geocodingServicesMap.put(service.getAcceptedLocationType(), service));
    }

    private boolean filterService(
            IGeocodingService geocodingService,
            String layerCode) {

        GeocodingService annotation = Optional.ofNullable(geocodingService)
                .map(Object::getClass)
                .map(clazz -> clazz.getAnnotation(GeocodingService.class))
                .orElseThrow(() -> new IllegalStateException("SuperClass of type "
                        + IGeocodingService.class.getName()
                        + " must be annotated with "
                        + GeocodingService.class.getName()));

        return annotation.value()
                .getCode()
                .equals(layerCode);
    }

    public IGeocodingService resolveGeocodingService(LocationType locationType) throws UnsupportedArgumentException {
        return Optional.ofNullable(geocodingServicesMap.get(locationType))
                .orElseThrow(() -> new UnsupportedArgumentException(locationType.name()));
    }
}
