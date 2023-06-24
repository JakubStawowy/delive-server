package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.geolocation.tool.GeocodingResponseConvertHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

abstract class MapQuestGeocodingService extends BaseGeocodingService {

    private static final String BASE_URI = "http://open.mapquestapi.com/geocoding/v1/";

    private final String apiKey;

    public MapQuestGeocodingService(
            IHttpClientService httpClientService,
            IResponseJsonConverter converter,
            String apiKey) {
        super(httpClientService, converter);
        this.apiKey = apiKey;
    }

    @Override
    public final boolean isEmergencyService() {
        return true;
    }

    @Override
    public List<LocationTo> getConvertedLocations(LocationTo locationTo) throws IOException, InterruptedException {
        JSONArray results = getLocationResponse(locationTo);
        return GeocodingResponseConvertHelper.mapMapquestResponseToLocations(results);
    }

    @Override
    public Optional<LocationTo> getSingleLocationData(LocationTo locationTo)
            throws IOException, InterruptedException {
        return Optional.ofNullable(getLocationResponse(locationTo))
                .map(response -> response.get(0))
                .map(response -> (JSONObject) response)
                .map(response -> response.get("locations"))
                .map(response -> (JSONArray) response)
                .map(response -> response.get(0))
                .map(response -> (JSONObject) response)
                .map(GeocodingResponseConvertHelper::mapMapquestResponseToLocation);

    }

    private JSONArray getLocationResponse(LocationTo locationTo) throws IOException, InterruptedException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("key", apiKey);
        paramsMap.put("location", prepareQuery(locationTo));
        HttpResponse<String> httpResponse = httpClientService.getHttpResponse(
                BASE_URI + getGeocodingType().getValue(),
                paramsMap);

        return converter.convertResponseToJSONArray(
                httpResponse,
                "results");
    }

    protected enum GeocodingType implements HasValue {

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
