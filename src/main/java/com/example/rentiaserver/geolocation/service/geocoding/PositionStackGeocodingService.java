package com.example.rentiaserver.geolocation.service.geocoding;

import com.example.rentiaserver.http.IHttpClientService;
import com.example.rentiaserver.http.IHttpResponseJsonConverter;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import com.example.rentiaserver.geolocation.tool.GeocodingResponseConvertHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

abstract class PositionStackGeocodingService extends BaseGeocodingService {

    private final String apiKey;

    private final String baseUri;

    public PositionStackGeocodingService(
            IHttpClientService httpClientService,
            IHttpResponseJsonConverter converter,
            String apiKey,
            String baseUri) {

        super(httpClientService, converter);
        this.apiKey = apiKey;
        this.baseUri = baseUri;
    }

    public List<LocationTo> getConvertedLocations(LocationTo locationTo)
            throws IOException, InterruptedException {
        JSONArray results = getLocationResponse(locationTo);
        return GeocodingResponseConvertHelper.mapPositionStackResponseToLocations(results);
    }

    @Override
    public Optional<LocationTo> getSingleLocationData(LocationTo locationTo)
            throws IOException, InterruptedException {
        return Optional.ofNullable(getLocationResponse(locationTo))
                .map(response -> response.get(0))
                .map(obj -> (JSONObject) obj)
                .map(GeocodingResponseConvertHelper::mapPositionStackResponseToLocation);
    }

    private JSONArray getLocationResponse(LocationTo locationTo) throws IOException, InterruptedException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("access_key", apiKey);
        paramsMap.put("query", prepareQuery(locationTo));
        HttpResponse<String> httpResponse = httpClientService.getHttpResponse(
                baseUri + getGeocodingType().getValue(),
                paramsMap);

        return converter.convertResponseToJSONArray(
                httpResponse,
                "data");
    }

    protected enum GeocodingType implements HasValue {

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
