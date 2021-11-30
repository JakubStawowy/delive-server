package com.example.rentiaserver.geolocation.distance;

import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.stereotype.Component;

@Component
final class DistanceCalculator implements IDistanceCalculator {

    @Override
    public double getDistance(LocationTo initialLocation, LocationTo finalLocation) {

        final int earthRadius = 6371;

        double lat1 = initialLocation.getLatitude();
        double lon1 = initialLocation.getLongitude();
        double lat2 = finalLocation.getLatitude();
        double lon2 = finalLocation.getLongitude();

        double latDistance = convertToRadius(lat2-lat1);
        double lonDistance = convertToRadius(lon2-lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(convertToRadius(lat1)) * Math.cos(convertToRadius(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return earthRadius * c;
    }

    private double convertToRadius(final double value) {
        return value * Math.PI / 180;
    }
}