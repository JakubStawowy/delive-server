package com.example.rentiaserver.maps.services;

import com.example.rentiaserver.maps.to.LocationTo;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    public double getDistance(LocationTo initialLocation, LocationTo finalLocation) {

        final int earthRadius = 6371;

        double lat1 = initialLocation.getLatitude();
        double lon1 = initialLocation.getLongitude();
        double lat2 = finalLocation.getLatitude();
        double lon2 = finalLocation.getLongitude();

        double latDistance = toRad(lat2-lat1);
        double lonDistance = toRad(lon2-lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return earthRadius * c;
    }

    private double toRad(Double value) {
        return value * Math.PI / 180;
    }
}