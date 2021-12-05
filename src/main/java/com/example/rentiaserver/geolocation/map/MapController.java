package com.example.rentiaserver.geolocation.map;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.geolocation.to.GeolocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
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
            @RequestParam double fromLatitude, @RequestParam double toLatitude,
            @RequestParam double fromLongitude, @RequestParam double toLongitude,
            @RequestParam double mapWidth, @RequestParam double mapHeight) {
        
        return new GeolocationTo(
                geolocationService.getHalfwayPoint(fromLatitude, toLatitude),
                geolocationService.getHalfwayPoint(fromLongitude, toLongitude),
                geolocationService.getZoomLevel(
                        fromLongitude, fromLatitude,
                        toLongitude, toLatitude, mapWidth));
    }
}
