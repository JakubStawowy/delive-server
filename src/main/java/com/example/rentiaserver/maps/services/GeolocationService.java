package com.example.rentiaserver.maps.services;

import com.example.rentiaserver.maps.api.IGeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeolocationService implements IGeolocationService {


    @Autowired
    public GeolocationService(DistanceCalculator distanceService) {
        this.distanceService = distanceService;
    }

    @Override
    public double getHalfwayPoint(double firstCoordinate, double secondCoordinate) {
        return (firstCoordinate + secondCoordinate) / 2;
    }

    @Override
    public long getZoomLevel(double fromLongitude, double toLongitude, double mapWidth) {
        double angle = toLongitude - fromLongitude < 0 ? toLongitude - fromLongitude + 360 : toLongitude - fromLongitude;
        return Math.round(Math.log(mapWidth * 360 / angle / 256 / Math.log(2)));
    }

    @Override
    public long getZoomLevel(double distance, double mapWidth) {
        return Math.round(Math.log(mapWidth * 360 / distance / 256 / Math.log(2)));
    }
}
