package com.example.rentiaserver.geolocation.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.geolocation.api.IGeolocationService;
import com.example.rentiaserver.geolocation.service.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = DistanceController.BASE_ENDPOINT)
public class DistanceController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/distance";

    private final IGeolocationService geolocationService;

    @Autowired
    public DistanceController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GetMapping("/isInArea")
    public boolean isClientInRequiredDestinationArea(
            @RequestParam Long orderId,
            @RequestParam double clientLatitude,
            @RequestParam double clientLongitude) throws EntityNotFoundException {

        return geolocationService.isClientInRequiredDestinationArea(orderId, clientLatitude, clientLongitude);
    }

    @GetMapping("/get")
    public double getClientDistanceFromOrderDestination(
            @RequestParam Long orderId,
            @RequestParam double clientLatitude,
            @RequestParam double clientLongitude) throws EntityNotFoundException {

        return geolocationService.getClientDistanceFromOrderDestination(orderId, clientLatitude, clientLongitude);
    }

}
