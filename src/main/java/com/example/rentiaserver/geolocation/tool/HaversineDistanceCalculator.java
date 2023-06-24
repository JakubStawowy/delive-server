package com.example.rentiaserver.geolocation.tool;

import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.geolocation.util.GeoConstants;
import com.example.rentiaserver.geolocation.util.LocationValidator;

public class HaversineDistanceCalculator {

    public static double getDistance(LocationTo initialLocation, LocationTo finalLocation) {

        if (initialLocation == null || finalLocation == null) {
            return 0;
        }

        LocationValidator.validateLocation(initialLocation);
        LocationValidator.validateLocation(finalLocation);

        double lat1 = Math.toRadians(initialLocation.getLatitude());
        double lon1 = Math.toRadians(initialLocation.getLongitude());
        double lat2 = Math.toRadians(finalLocation.getLatitude());
        double lon2 = Math.toRadians(finalLocation.getLongitude());

        double latDistance = lat2 - lat1;
        double lonDistance = lon2 - lon1;
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        return GeoConstants.EARTH_RADIUS_KM * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
