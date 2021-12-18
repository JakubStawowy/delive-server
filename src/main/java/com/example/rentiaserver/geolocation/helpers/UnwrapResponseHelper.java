package com.example.rentiaserver.geolocation.helpers;

import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UnwrapResponseHelper {
    public static List<String> unwrapPositionStackResponse(JSONArray response) {
        List<String> result = new ArrayList<>();
        response.forEach(object -> result.add(unwrapPositionStackSingleResponse((JSONObject) object)));
        return result;
    }

    public static List<String> unwrapMapquestResponse(JSONArray response) {
        if (response.isEmpty()) {
            return new ArrayList<>(0);
        }
        List<String> result = new ArrayList<>();
        JSONObject firstElement = (JSONObject) response.get(0);
        JSONArray locations = (JSONArray) firstElement.get("locations");
        locations.forEach(object -> result.add(unwrapMapquestSingleResponse((JSONObject) object)));
        return result;
    }

    public static String unwrapPositionStackSingleResponse(JSONObject jsonObject) {
        return jsonObject.get("name") + ", " + jsonObject.get("locality") + ", " + jsonObject.get("country");
    }

    public static String unwrapMapquestSingleResponse(JSONObject jsonObject) {
        return jsonObject.get("street") + ", " + jsonObject.get("adminArea5") +", " + jsonObject.get("adminArea1");
    }

    public static LocationTo convertPositionStackSingleResponse(JSONObject jsonObject) {
        return LocationTo.Builder.getBuilder().setAddress(unwrapPositionStackSingleResponse(jsonObject))
                .setLatitude(Double.valueOf(String.valueOf(jsonObject.get("latitude"))))
                .setLongitude(Double.valueOf(String.valueOf(jsonObject.get("longitude"))))
                .build();
    }

    public static LocationTo convertMapquestSingleResponse(JSONObject jsonObject) {
        return LocationTo.Builder.getBuilder().setAddress(unwrapMapquestSingleResponse(jsonObject))
                .setLatitude(Double.valueOf(String.valueOf(((JSONObject) jsonObject.get("latLng")).get("lat"))))
                .setLongitude(Double.valueOf(String.valueOf(((JSONObject) jsonObject.get("latLng")).get("lng"))))
                .build();
    }
}
