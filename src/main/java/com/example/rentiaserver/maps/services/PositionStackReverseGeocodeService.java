package com.example.rentiaserver.maps.services;

import com.example.rentiaserver.maps.httpclient.json.HttpResponseJsonConverter;
import com.example.rentiaserver.maps.httpclient.services.HttpClientService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PositionStackReverseGeocodeService {

    private static final String BASE_URI = "http://api.positionstack.com/v1";
    private static final String API_KEY = "094f2137e9a96a52f38142d2c3729a35";

    private final HttpClientService httpClientService;
    private final HttpResponseJsonConverter converter;

    @Autowired
    public PositionStackReverseGeocodeService(HttpClientService httpClientService, HttpResponseJsonConverter converter) {
        this.httpClientService = httpClientService;
        this.converter = converter;
    }

    public JSONObject getAddressFromCoordinates(Double longitude, Double latitude) throws IOException, InterruptedException, ParseException {
        Map<String, String> params = new HashMap<>();
        params.put("access_key", API_KEY);
        params.put("query", latitude + "," + longitude);
        JSONArray response = converter.convertResponseToJSONArray(httpClientService.getHttpResponse(BASE_URI + "/reverse", params), "data");

        return (JSONObject) response.get(0);
    }

    public JSONObject getAddressFromData(String address) throws IOException, InterruptedException, ParseException {
        Map<String, String> params = new HashMap<>();
        params.put("access_key", API_KEY);
        params.put("query", address.replaceAll(",", " "));
        JSONArray response = converter.convertResponseToJSONArray(httpClientService.getHttpResponse(BASE_URI + "/forward", params), "data");

        return (JSONObject) response.get(0);
    }
}
