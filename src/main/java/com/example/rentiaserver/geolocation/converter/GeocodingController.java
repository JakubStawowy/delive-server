package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.geolocation.helpers.UnwrapResponseHelper;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = GeocodingController.BASE_ENDPOINT)
@PropertySource("classpath:application.properties")
public class GeocodingController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/geocoding";

    private final IGeocodingService geocodingService;
    private final String emergencyGeocodingMode;

    @Autowired
    public GeocodingController(ForwardGeocodingService geocodingService, ForwardEmergencyGeocodingService emergencyGeocodingService,
                               @Value("${geocoding.mode.emergency}") String emergencyGeocodingMode) {

        this.emergencyGeocodingMode = emergencyGeocodingMode;
        if (Boolean.TRUE.equals(Boolean.valueOf(emergencyGeocodingMode))) {
            this.geocodingService = emergencyGeocodingService;
        } else {
            this.geocodingService = geocodingService;
        }
    }

    @GetMapping("/list")
    public List<String> getProposedAddresses(@RequestParam String address) throws InterruptedException, ParseException, IOException {
        if (address == null || address.length() < 3) {
            return new ArrayList<>(0);
        }
        LocationTo locationTo = LocationTo.Builder.getBuilder()
                .setAddress(address)
                .build();

        if (Boolean.TRUE.equals(Boolean.valueOf(emergencyGeocodingMode))) {
            JSONArray array = geocodingService.getLocationsData(locationTo);
            return UnwrapResponseHelper.unwrapMapquestResponse(array);
        }
        try {
            return UnwrapResponseHelper.unwrapPositionStackResponse(geocodingService.getLocationsData(locationTo));
        } catch (Exception ignored) {
            return new ArrayList<>(0);
        }
    }
}
