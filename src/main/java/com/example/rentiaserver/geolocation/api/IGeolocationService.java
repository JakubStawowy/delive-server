package com.example.rentiaserver.geolocation.api;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.geolocation.model.to.GeolocationTo;

public interface IGeolocationService {

    boolean isClientInRequiredDestinationArea(Long orderId, double clientLatitude, double clientLongitude)
            throws EntityNotFoundException;

    double getClientDistanceFromOrderDestination(Long orderId, double clientLatitude, double clientLongitude)
            throws EntityNotFoundException;

    GeolocationTo getMapProperties(
            double fromLatitude,
            double toLatitude,
            double fromLongitude,
            double toLongitude,
            double mapWidth,
            double mapHeight);
}
