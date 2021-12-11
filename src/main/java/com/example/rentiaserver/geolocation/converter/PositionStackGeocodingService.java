package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Deprecated
public final class PositionStackGeocodingService implements IGeocodingService {

    private static final String BASE_URI = "http://api.positionstack.com/v1/";
    private static final String API_KEY = "094f2137e9a96a52f38142d2c3729a35";

    private final IHttpClientService httpClientService;
    private final IResponseJsonConverter converter;

    private enum GeocodingType {

        FORWARD("forward"),
        REVERSE("reverse");

        private final String value;

        GeocodingType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @Autowired
    public PositionStackGeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        this.httpClientService = httpClientService;
        this.converter = converter;
    }

    @Override
    public JSONObject getLocationDataFromCoordinates(final double longitude, final double latitude) throws IOException, InterruptedException, ParseException {
        return getAddress(GeocodingType.REVERSE, latitude + "," + longitude);
    }

    @Override
    public JSONObject getLocationDataFromAddress(String address) throws IOException, InterruptedException, ParseException {
        return getAddress(GeocodingType.FORWARD, address.replaceAll(",", " "));
    }

    private JSONObject getAddress(GeocodingType geocodingType, String query) throws IOException, InterruptedException, ParseException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("access_key", API_KEY);
        paramsMap.put("query", query);
        JSONArray response = converter.convertResponseToJSONArray(httpClientService
                .getHttpResponse(BASE_URI + geocodingType, paramsMap), "data");

        if (response.isEmpty()) {
            return null;
        }

        return (JSONObject) response.get(0);
    }
}
