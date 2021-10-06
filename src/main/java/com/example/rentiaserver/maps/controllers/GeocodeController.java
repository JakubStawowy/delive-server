package com.example.rentiaserver.maps.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.maps.services.PositionStackReverseGeocodeService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = GeocodeController.BASE_ENDPOINT)
public class GeocodeController {
    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/geocode";

    private final PositionStackReverseGeocodeService geocoderService;

    @Autowired
    public GeocodeController(PositionStackReverseGeocodeService geocoderService) {
        this.geocoderService = geocoderService;
    }

    @GetMapping("/reverse")
    public JSONObject getReverseGeocode(@RequestParam Double longitude, @RequestParam Double latitude) throws IOException, InterruptedException, ParseException {
        return geocoderService.getAddressFromCoordinates(longitude, latitude);
    }
}
