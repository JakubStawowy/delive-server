package com.example.rentiaserver.geolocation.map;

import com.example.rentiaserver.geolocation.distance.IDistanceCalculator;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final class GeolocationService implements IGeolocationService {

    private final IDistanceCalculator distanceCalculator;

    @Autowired
    public GeolocationService(IDistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public double getHalfwayPoint(final double firstCoordinate, final double secondCoordinate) {
        return (firstCoordinate + secondCoordinate) / 2;
    }

    @Override
    public long getZoomLevel(final double fromLongitude, final double toLongitude, final double mapWidth) {
        double angle = toLongitude - fromLongitude < 0 ? toLongitude - fromLongitude + 360 : toLongitude - fromLongitude;
        return Math.round(Math.log(mapWidth * 360 / angle / 256 / Math.log(2)));
    }

    @Override
    public long getZoomLevel(final double fromLongitude, final double fromLatitude,
                             final double toLongitude, final double toLatitude, final double mapWidth) {
        LocationTo initialLocation = LocationTo.Builder.getBuilder()
                .setLatitude(fromLatitude)
                .setLongitude(fromLongitude)
                .build();
        LocationTo finalLocation = LocationTo.Builder.getBuilder()
                .setLatitude(toLatitude)
                .setLongitude(toLongitude)
                .build();

        final double distance = distanceCalculator.getDistance(initialLocation, finalLocation);
        final double x = 40075.014 / distance;
        return Math.round(Math.log(x));
    }
}
