package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import static com.example.rentiaserver.ApplicationConstants.Paths.*;

@Service
@PropertySource(APPLICATION_PROPERTIES)
final class ReversePositionStackGeocodingService
        extends PositionStackGeocodingService
        implements IReverseGeocodingService {

    @Autowired
    public ReversePositionStackGeocodingService(
            IHttpClientService httpClientService,
            IResponseJsonConverter converter,
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
