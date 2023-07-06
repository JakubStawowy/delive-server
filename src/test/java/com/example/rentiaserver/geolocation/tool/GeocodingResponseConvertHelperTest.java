package com.example.rentiaserver.geolocation.tool;

import com.example.rentiaserver.geolocation.api.LocationType;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeocodingResponseConvertHelperTest {

    @Test
    public void testExtractAddressFromPositionStackSingleResponse() {

        // Given
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Sample Name");
        jsonObject.put("locality", "Sample Locality");
        jsonObject.put("country", "Sample Country");

        String expectedAddress = "Sample Name, Sample Locality, Sample Country";

        // When
        String actualAddress = GeocodingResponseConvertHelper.extractAddressFromPositionStackSingleResponse(jsonObject);

        // Then
        assertEquals(expectedAddress, actualAddress);
    }

    @Test
    public void testExtractAddressFromMapquestResponse() {

        // Given
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("street", "Sample Street");
        jsonObject.put("adminArea5", "Sample AdminArea5");
        jsonObject.put("adminArea1", "Sample AdminArea1");

        String expectedAddress = "Sample Street, Sample AdminArea5, Sample AdminArea1";

        // When
        String actualAddress = GeocodingResponseConvertHelper.extractAddressFromMapquestResponse(jsonObject);

        // Then
        assertEquals(expectedAddress, actualAddress);
    }

    @Test
    public void testMapPositionStackResponseToLocation() {

        // Given
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Sample Name");
        jsonObject.put("locality", "Sample Locality");
        jsonObject.put("country", "Sample Country");
        jsonObject.put("latitude", 1.234567);
        jsonObject.put("longitude", 2.345678);

        LocationTo expectedLocation = LocationTo.getBuilder()
                .setAddress("Sample Name, Sample Locality, Sample Country")
                .setLatitude(1.234567)
                .setLongitude(2.345678)
                .setLocationType(LocationType.FULL)
                .build();

        // When
        LocationTo actualLocation = GeocodingResponseConvertHelper.mapPositionStackResponseToLocation(jsonObject);

        // Then
        assertTrue(equalsLocations(expectedLocation, actualLocation));
    }

    @Test
    public void testMapMapquestResponseToLocation() {

        // Given
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("street", "Sample Street");
        jsonObject.put("adminArea5", "Sample AdminArea5");
        jsonObject.put("adminArea1", "Sample AdminArea1");

        JSONObject latLlng = new JSONObject();
        latLlng.put("lat", 1.234567);
        latLlng.put("lng", 2.345678);
        jsonObject.put("latLng", latLlng);

        LocationTo expectedLocation = LocationTo.getBuilder()
                .setAddress("Sample Street, Sample AdminArea5, Sample AdminArea1")
                .setLatitude(1.234567)
                .setLongitude(2.345678)
                .setLocationType(LocationType.FULL)
                .build();

        // When
        LocationTo actualLocation = GeocodingResponseConvertHelper.mapMapquestResponseToLocation(jsonObject);

        // Then
        assertTrue(equalsLocations(expectedLocation, actualLocation));
    }

    @Test
    public void testMapPositionStackResponseToLocations() {

        // Given
        JSONArray response = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "Sample Name 1");
        jsonObject1.put("locality", "Sample Locality 1");
        jsonObject1.put("country", "Sample Country 1");
        jsonObject1.put("latitude", 1.234567);
        jsonObject1.put("longitude", 2.345678);
        response.add(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name", "Sample Name 2");
        jsonObject2.put("locality", "Sample Locality 2");
        jsonObject2.put("country", "Sample Country 2");
        jsonObject2.put("latitude", 3.456789);
        jsonObject2.put("longitude", 4.567890);
        response.add(jsonObject2);

        List<LocationTo> expectedLocations = List.of(
                LocationTo.getBuilder()
                        .setAddress("Sample Name 1, Sample Locality 1, Sample Country 1")
                        .setLatitude(1.234567)
                        .setLongitude(2.345678)
                        .setLocationType(LocationType.FULL)
                        .build(),
                LocationTo.getBuilder()
                        .setAddress("Sample Name 2, Sample Locality 2, Sample Country 2")
                        .setLatitude(3.456789)
                        .setLongitude(4.567890)
                        .setLocationType(LocationType.FULL)
                        .build()
        );

        // When
        List<LocationTo> actualLocations = GeocodingResponseConvertHelper.mapPositionStackResponseToLocations(response);

        // Then
        assertTrue(equalsLocations(expectedLocations, actualLocations));
    }

    @Test
    public void testMapMapquestResponseToLocations() {

        // Given
        JSONArray response = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("street", "Sample Street 1");
        jsonObject1.put("adminArea5", "Sample AdminArea5 1");
        jsonObject1.put("adminArea1", "Sample AdminArea1 1");

        JSONObject latLng = new JSONObject();
        latLng.put("lat", 1.234567);
        latLng.put("lng", 2.345678);

        jsonObject1.put("latLng", latLng);
        response.add(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("street", "Sample Street 2");
        jsonObject2.put("adminArea5", "Sample AdminArea5 2");
        jsonObject2.put("adminArea1", "Sample AdminArea1 2");

        JSONObject latLng2 = new JSONObject();
        latLng2.put("lat", 3.456789);
        latLng2.put("lng", 4.567890);
        jsonObject2.put("latLng", latLng2);
        response.add(jsonObject2);

        List<LocationTo> expectedLocations = List.of(
                LocationTo.getBuilder()
                        .setAddress("Sample Street 1, Sample AdminArea5 1, Sample AdminArea1 1")
                        .setLatitude(1.234567)
                        .setLongitude(2.345678)
                        .setLocationType(LocationType.FULL)
                        .build(),
                LocationTo.getBuilder()
                        .setAddress("Sample Street 2, Sample AdminArea5 2, Sample AdminArea1 2")
                        .setLatitude(3.456789)
                        .setLongitude(4.567890)
                        .setLocationType(LocationType.FULL)
                        .build()
        );

        // When
        List<LocationTo> actualLocations = GeocodingResponseConvertHelper.mapMapquestResponseToLocations(response);

        // Then
        assertTrue(equalsLocations(expectedLocations, actualLocations));
    }

    private static boolean equalsLocations(LocationTo expectedLocation, LocationTo actualLocation) {
        return expectedLocation.getLatitude().equals(actualLocation.getLatitude())
                && expectedLocation.getLongitude().equals(actualLocation.getLongitude())
                && expectedLocation.getAddress().equals(actualLocation.getAddress());
    }

    private static boolean equalsLocations(List<LocationTo> expectedLocations, List<LocationTo> actualLocations) {
        if (expectedLocations.size() != actualLocations.size()) {
            return false;
        }

        Iterator<LocationTo> expectedLocationIt = expectedLocations.iterator();
        Iterator<LocationTo> actualLocationIt = actualLocations.iterator();

        while (expectedLocationIt.hasNext() && actualLocationIt.hasNext()) {
            LocationTo expectedLocation = expectedLocationIt.next();
            LocationTo actualLocation = actualLocationIt.next();

            if (!equalsLocations(expectedLocation, actualLocation)) {
                return false;
            }
        }

        return true;
    }
}
