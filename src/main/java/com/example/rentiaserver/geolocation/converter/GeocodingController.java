package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.util.UnsupportedArgumentException;
import com.example.rentiaserver.geolocation.api.GeocodingServiceResolver;
import com.example.rentiaserver.geolocation.api.IGeocodingService;
import com.example.rentiaserver.geolocation.api.LocationType;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = GeocodingController.BASE_ENDPOINT)
class GeocodingController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/geocoding";

    private static final int MIN_REQUEST_ADDRESS_LENGTH = 3;

    private final GeocodingServiceResolver geocodingServiceResolver;

    @Autowired
    public GeocodingController(GeocodingServiceResolver geocodingServiceResolver) {
        this.geocodingServiceResolver = geocodingServiceResolver;
    }

    @GetMapping("/list")
    public List<String> getProposedAddresses(@RequestParam String address)
            throws InterruptedException, ParseException, IOException, UnsupportedArgumentException {
        
        if (address == null || address.length() < MIN_REQUEST_ADDRESS_LENGTH) {
            return Collections.emptyList();
        }

        LocationTo locationTo = LocationTo.getBuilder()
                .setAddress(address)
                .setLocationType(LocationType.ADDRESS)
                .build();

        IGeocodingService geocodingService =
                geocodingServiceResolver.resolveGeocodingService(locationTo.getLocationType());

        return geocodingService.getConvertedLocations(locationTo)
                .stream()
                .map(LocationTo::getAddress)
                .collect(Collectors.toList());
    }
}
