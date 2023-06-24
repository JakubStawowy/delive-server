package com.example.rentiaserver.geolocation.map;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.geolocation.to.GeolocationTo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.geolocation.tool.MapGeolocationHelper;

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

    @GetMapping("/middle")
    public GeolocationTo getMapProperties(
            @RequestParam double fromLatitude, @RequestParam double toLatitude,
            @RequestParam double fromLongitude, @RequestParam double toLongitude,
            @RequestParam double mapWidth, @RequestParam double mapHeight) {

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

        return new GeolocationTo(latitudeMidpoint, longitudeMidpoint, zoomLevel);
    }
}
