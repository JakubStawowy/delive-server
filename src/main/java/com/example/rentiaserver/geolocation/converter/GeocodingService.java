package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class GeocodingService {

    private static final String BASE_URI = "http://api.positionstack.com/v1/";
    private static final String API_KEY = "094f2137e9a96a52f38142d2c3729a35";

    private final IHttpClientService httpClientService;
    private final IResponseJsonConverter converter;

    public GeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        this.httpClientService = httpClientService;
        this.converter = converter;
    }

    protected abstract GeocodingType getGeocodingType();
    protected abstract String prepareQuery(LocationTo locationTo);

    public JSONObject getFullLocationData(LocationTo locationTo)
            throws IOException, InterruptedException, ParseException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("access_key", API_KEY);
        paramsMap.put("query", prepareQuery(locationTo));
        JSONArray response = converter.convertResponseToJSONArray(httpClientService
                .getHttpResponse(BASE_URI + getGeocodingType(), paramsMap), "data");
        if (response.isEmpty()) {
            return null;
        }

        return (JSONObject) response.get(0);
    }

    protected enum GeocodingType {
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
}
