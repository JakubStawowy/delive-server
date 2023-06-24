package com.example.rentiaserver.geolocation.tool;

import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.geolocation.util.GeoConstants;

public class MapGeolocationHelper {

    public static double calculateMidpoint(final double firstCoordinate, final double secondCoordinate) {
        return (firstCoordinate + secondCoordinate) / 2;
    }

    public static long calculateZoomLevel(LocationTo locationFrom, LocationTo locationTo) {

        if (locationFrom == null || locationTo == null) {
            return 0;
        }

        final double distance = HaversineDistanceCalculator.getDistance(locationFrom, locationTo);
        final double x = GeoConstants.EARTH_CIRCUMFERENCE_KM / distance;

        return Math.round(Math.log(x));
    }
}
