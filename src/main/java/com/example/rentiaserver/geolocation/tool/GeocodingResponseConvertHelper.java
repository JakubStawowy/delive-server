package com.example.rentiaserver.geolocation.tool;

import com.example.rentiaserver.geolocation.api.LocationType;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GeocodingResponseConvertHelper {

    public static String extractAddressFromPositionStackSingleResponse(JSONObject jsonObject) {
        return jsonObject.get("name") + ", " + jsonObject.get("locality") + ", " + jsonObject.get("country");
    }

    public static String extractAddressFromMapquestResponse(JSONObject jsonObject) {
        return jsonObject.get("street") + ", " + jsonObject.get("adminArea5") +", " + jsonObject.get("adminArea1");
    }

    public static LocationTo mapPositionStackResponseToLocation(JSONObject jsonObject) {
        return LocationTo.getBuilder()
                .setAddress(extractAddressFromPositionStackSingleResponse(jsonObject))
                .setLatitude(Double.valueOf(String.valueOf(jsonObject.get("latitude"))))
                .setLongitude(Double.valueOf(String.valueOf(jsonObject.get("longitude"))))
                .setLocationType(LocationType.FULL)
                .build();
    }

    public static LocationTo mapMapquestResponseToLocation(JSONObject jsonObject) {
        return LocationTo.getBuilder()
                .setAddress(extractAddressFromMapquestResponse(jsonObject))
                .setLatitude(Double.valueOf(String.valueOf(((JSONObject) jsonObject.get("latLng")).get("lat"))))
                .setLongitude(Double.valueOf(String.valueOf(((JSONObject) jsonObject.get("latLng")).get("lng"))))
                .setLocationType(LocationType.FULL)
                .build();
    }

    public static List<LocationTo> mapPositionStackResponseToLocations(JSONArray response) {
        return mapResponseToLocations(response, GeocodingResponseConvertHelper::mapPositionStackResponseToLocation);
    }

    public static List<LocationTo> mapMapquestResponseToLocations(JSONArray response) {
        return mapResponseToLocations(response, GeocodingResponseConvertHelper::mapMapquestResponseToLocation);
    }

    private static List<LocationTo> mapResponseToLocations(
            JSONArray response,
            Function<JSONObject, LocationTo> singleMapper) {

        List<LocationTo> result = new ArrayList<>();
        for (Object obj : response) {
            JSONObject jsonObject = mapToJSONObject(obj);
            result.add(singleMapper.apply(jsonObject));
        }

        return result;
    }

    private static JSONObject mapToJSONObject(Object obj) {
        return (JSONObject) obj;
    }
}
