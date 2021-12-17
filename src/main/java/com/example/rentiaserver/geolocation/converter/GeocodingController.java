package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = GeocodingController.BASE_ENDPOINT)
public class GeocodingController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/geocoding";

    private final GeocodingService geocodingService;

    @Autowired
    public GeocodingController(ForwardGeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    // Na wypadek awarii positionstack
//    private final NewForwardGeocodingService geocodingService;
//
//    @Autowired
//    public GeocodingController(NewForwardGeocodingService geocodingService) {
//        this.geocodingService = geocodingService;
//    }

    @GetMapping("/list")
    public List<String> getProposedAddresses(@RequestParam String address) throws InterruptedException, ParseException, IOException {
        if (address == null || address.length() < 3) {
            return Collections.emptyList();
        }
        LocationTo locationTo = LocationTo.Builder.getBuilder()
                .setAddress(address)
                .build();
        List<String> result = new ArrayList<>();
        JSONArray resultJSONArray = geocodingService.getLocationsData(locationTo);
        if (resultJSONArray != null) {
            resultJSONArray.forEach(object -> result.add(mapJSONObjectToString((JSONObject) object)));
            // Na wypadek awarii positionstack
//            JSONObject locationsArray = (JSONObject) resultJSONArray.get(0);
//            JSONArray provLocation = (JSONArray) locationsArray.get("locations");
//            for (Object res: provLocation) {
//                result.add(
//                        ((JSONObject) res).get("street") + ", " +
//                        ((JSONObject) res).get("adminArea5") +", " +
//                        ((JSONObject) res).get("adminArea1")
//                );
//            }
        }
        return result.subList(0, result.size() <= 5 ? result.size() : 5);
    }

    private String mapJSONObjectToString(JSONObject jsonObject) {
        return jsonObject.get("name") + ", " + jsonObject.get("locality") + ", " + jsonObject.get("country");
//        return (JSONObject) jsonObject.get(0);
    }
}
