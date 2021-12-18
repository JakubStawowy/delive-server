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

public abstract class GeocodingService extends BaseGeocodingService {

    private static final String BASE_URI = "http://api.positionstack.com/v1/";
    private static final String API_KEY = "674d875372303ee0f00d7cf5e87946c1";

    public GeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        super(httpClientService, converter);
    }

    protected abstract String prepareQuery(LocationTo locationTo);
    protected abstract IGeocodingType getGeocodingType();

    @Override
    public JSONArray getLocationsData(LocationTo locationTo) throws IOException, InterruptedException, ParseException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("access_key", API_KEY);
        paramsMap.put("query", prepareQuery(locationTo));
        return converter.convertResponseToJSONArray(httpClientService
                .getHttpResponse(BASE_URI + getGeocodingType().getValue(), paramsMap), "data");
    }

    @Override
    public JSONObject getFullLocationData(LocationTo locationTo)
            throws IOException, InterruptedException, ParseException {
        JSONArray response = getLocationsData(locationTo);
        if (response.isEmpty()) {
            return null;
        }
        return (JSONObject) response.get(0);
    }

    protected enum GeocodingType implements IGeocodingType {
        FORWARD("forward"),
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
