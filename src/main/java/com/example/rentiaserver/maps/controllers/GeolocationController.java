package com.example.rentiaserver.maps.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.maps.api.IGeolocationService;
import com.example.rentiaserver.maps.services.DistanceCalculator;
import com.example.rentiaserver.maps.to.GeolocationTo;
import com.example.rentiaserver.maps.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = GeolocationController.BASE_ENDPOINT)
public class GeolocationController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/maps";

    IGeolocationService geolocationService;
    DistanceCalculator distanceService;

    @Autowired
    public GeolocationController(IGeolocationService geolocationService, DistanceCalculator distanceService) {
        this.geolocationService = geolocationService;
        this.distanceService = distanceService;
    }

    @GetMapping("/middle")
    public GeolocationTo getMapProperties(
            @RequestParam double fromLatitude, @RequestParam double toLatitude,
            @RequestParam double fromLongitude, @RequestParam double toLongitude,
            @RequestParam double mapWidth, @RequestParam double mapHeight) {
//        long zoom0 = geolocationService.getZoomLevel(fromLongitude, toLongitude, mapWidth);
//        long zoom1 = geolocationService.getZoomLevel(fromLatitude, toLatitude, mapWidth);

        double distance = distanceService.getDistance(
                new LocationTo(null, null, fromLatitude, fromLongitude, null),
                new LocationTo(null, null, toLatitude, toLongitude, null)
        );
        double x = 40075.014 / distance;
        long finalZoom = Math.round(Math.log(x));
//        long finalZoom = Math.abs(fromLongitude - toLongitude) > Math.abs(fromLatitude - toLatitude)
//                ? zoom0 : zoom1;
        
        return new GeolocationTo(
                geolocationService.getHalfwayPoint(fromLatitude, toLatitude),
                geolocationService.getHalfwayPoint(fromLongitude, toLongitude),
                finalZoom);
    }
}
