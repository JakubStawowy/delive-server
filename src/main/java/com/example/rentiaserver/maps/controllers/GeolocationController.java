package com.example.rentiaserver.maps.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.maps.api.IGeolocationService;
import com.example.rentiaserver.maps.to.GeolocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = GeolocationController.BASE_ENDPOINT)
public class GeolocationController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/maps";

    IGeolocationService geolocationService;

    @Autowired
    public GeolocationController(IGeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GetMapping("/middle")
    public GeolocationTo getMapProperties(
            @RequestParam double fromLatitude, @RequestParam double toLatitude,
            @RequestParam double fromLongitude, @RequestParam double toLongitude,
            @RequestParam int mapWidth) {
        return new GeolocationTo(
                geolocationService.getHalfwayPoint(fromLatitude, toLatitude),
                geolocationService.getHalfwayPoint(fromLongitude, toLongitude),
                geolocationService.getZoomLevel(fromLongitude, toLongitude, mapWidth));
    }
}
