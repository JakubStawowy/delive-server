package com.example.rentiaserver.geolocation.service;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.geolocation.api.IGeolocationService;
import com.example.rentiaserver.geolocation.model.to.GeolocationTo;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import com.example.rentiaserver.geolocation.tool.HaversineDistanceCalculator;
import com.example.rentiaserver.geolocation.tool.MapGeolocationHelper;
import com.example.rentiaserver.geolocation.util.GeoConstants;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeolocationService implements IGeolocationService {

    private final OrderService orderService;

    @Autowired
    public GeolocationService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public boolean isClientInRequiredDestinationArea(Long orderId, double clientLatitude, double clientLongitude)
            throws EntityNotFoundException {

        double distance = getDistanceFromOrderDestination(orderId, clientLatitude, clientLongitude);
        return distance < GeoConstants.MAX_CONTRACTOR_FROM_DESTINATION_DISTANCE_KM;
    }

    @Override
    public double getClientDistanceFromOrderDestination(Long orderId, double clientLatitude, double clientLongitude)
            throws EntityNotFoundException {

        return getDistanceFromOrderDestination(orderId, clientLatitude, clientLongitude);
    }

    @Override
    public GeolocationTo getMapProperties(
            double fromLatitude,
            double toLatitude,
            double fromLongitude,
            double toLongitude,
            double mapWidth,
            double mapHeight) {

        LocationTo locationFrom = LocationTo.getBuilder()
                .setLatitude(fromLatitude)
                .setLongitude(fromLongitude)
                .build();

        LocationTo locationTo = LocationTo.getBuilder()
                .setLatitude(toLatitude)
                .setLongitude(toLongitude)
                .build();

        double latitudeMidpoint = MapGeolocationHelper.calculateMidpoint(fromLatitude, toLatitude);
        double longitudeMidpoint = MapGeolocationHelper.calculateMidpoint(fromLongitude, toLongitude);
        long zoomLevel = MapGeolocationHelper.calculateZoomLevel(locationFrom, locationTo);

        return GeolocationTo.builder()
                .latitude(latitudeMidpoint)
                .longitude(longitudeMidpoint)
                .zoomLevel(zoomLevel)
                .build();
    }

    private double getDistanceFromOrderDestination(Long orderId, double clientLatitude, double clientLongitude)
            throws EntityNotFoundException {

        LocationTo currentClientLocalization = LocationTo.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();

        OrderTo order = orderService.getOrderToById(orderId);

        return HaversineDistanceCalculator.getDistance(order.getDestinationTo(), currentClientLocalization);
    }
}
