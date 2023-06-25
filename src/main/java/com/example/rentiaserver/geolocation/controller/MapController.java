package com.example.rentiaserver.geolocation.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.geolocation.api.IGeolocationService;
import com.example.rentiaserver.geolocation.model.to.GeolocationTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = MapController.BASE_ENDPOINT)
public final class MapController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/maps";

    private final IGeolocationService geolocationService;

    @Autowired
    public MapController(IGeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GetMapping("/middle")
    public GeolocationTo getMapProperties(
            @RequestParam double fromLatitude,
            @RequestParam double toLatitude,
            @RequestParam double fromLongitude,
            @RequestParam double toLongitude,
            @RequestParam double mapWidth,
            @RequestParam double mapHeight) {

        return geolocationService.getMapProperties(
                fromLatitude,
                toLatitude,
                fromLongitude,
                toLongitude,
                mapWidth,
                mapHeight);
    }
}
