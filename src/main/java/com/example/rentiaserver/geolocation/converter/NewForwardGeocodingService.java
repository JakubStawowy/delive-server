package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class NewForwardGeocodingService {

    private static final String BASE_URI = "http://open.mapquestapi.com/geocoding/v1/address";
    private static final String API_KEY = "aFNv2te6iS61qABztEDdKAcQjMbv25dO";

    private final IHttpClientService httpClientService;
    private final IResponseJsonConverter converter;

    public NewForwardGeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        this.httpClientService = httpClientService;
        this.converter = converter;
    }


    public JSONArray getLocationsData(LocationTo locationTo) throws IOException, InterruptedException, ParseException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("key", API_KEY);
        paramsMap.put("location", locationTo.getAddress().replaceAll(",", " "));
        return converter.convertResponseToJSONArray(httpClientService
                .getHttpResponse(BASE_URI, paramsMap), "results");
    }

    public JSONObject getFullLocationData(LocationTo locationTo)
            throws IOException, InterruptedException, ParseException {
        JSONArray response = getLocationsData(locationTo);
        if (response.isEmpty()) {
            return null;
        }
        return (JSONObject) ((JSONArray)((JSONObject) response.get(0)).get("locations")).get(0);
    }

}
