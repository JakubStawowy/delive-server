package com.example.rentiaserver.geolocation.service.geocoding;

import com.example.rentiaserver.http.IHttpClientService;
import com.example.rentiaserver.http.IHttpResponseJsonConverter;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import static com.example.rentiaserver.base.ApplicationConstants.Paths.*;

@Service
@PropertySource(APPLICATION_PROPERTIES)
final class ReversePositionStackGeocodingService
        extends PositionStackGeocodingService
        implements IReverseGeocodingService {

    @Autowired
    public ReversePositionStackGeocodingService(
            IHttpClientService httpClientService,
            IHttpResponseJsonConverter converter,
            @Value(PROPERTY_POSITION_STACK_API_KEY) String apiKey) {
        super(httpClientService, converter, apiKey);
    }

    @Override
    protected GeocodingType getGeocodingType() {
        return GeocodingType.REVERSE;
    }

    @Override
    protected String prepareQuery(LocationTo locationTo) {

        Double latitude = locationTo.getLatitude();
        Double longitude = locationTo.getLongitude();

        if (longitude == null || latitude == null) {
            throw new IllegalArgumentException("Reverse geocoding requires two params: latitude and longitude.");
        }
        return latitude + "," + longitude;
    }
}
