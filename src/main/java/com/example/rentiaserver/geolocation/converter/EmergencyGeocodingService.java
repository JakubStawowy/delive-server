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

public abstract class EmergencyGeocodingService extends BaseGeocodingService {

    private static final String BASE_URI = "http://open.mapquestapi.com/geocoding/v1/";
    private static final String API_KEY = "aFNv2te6iS61qABztEDdKAcQjMbv25dO";

    public EmergencyGeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        super(httpClientService, converter);
    }

    protected abstract String prepareQuery(LocationTo locationTo);
    protected abstract IGeocodingType getGeocodingType();

    @Override
    public JSONArray getLocationsData(LocationTo locationTo) throws IOException, InterruptedException, ParseException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("key", API_KEY);
        paramsMap.put("location", prepareQuery(locationTo));
        return converter.convertResponseToJSONArray(httpClientService
                .getHttpResponse(BASE_URI + getGeocodingType().getValue(), paramsMap), "results");
    }

    @Override
    public JSONObject getFullLocationData(LocationTo locationTo)
            throws IOException, InterruptedException, ParseException {
        JSONArray response = getLocationsData(locationTo);
        if (response.isEmpty()) {
            return null;
        }
        return (JSONObject) ((JSONArray)((JSONObject) response.get(0)).get("locations")).get(0);
    }

    protected enum GeocodingType implements IGeocodingType {
        FORWARD("address"),
        REVERSE("reverse");
        private final String value;

        GeocodingType(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }
    }
}
