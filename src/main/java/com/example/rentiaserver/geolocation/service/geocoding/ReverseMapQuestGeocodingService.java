package com.example.rentiaserver.geolocation.service.geocoding;

import com.example.rentiaserver.http.IHttpClientService;
import com.example.rentiaserver.http.IHttpResponseJsonConverter;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import static com.example.rentiaserver.base.ApplicationConstants.Properties.APPLICATION_PROPERTIES;
import static com.example.rentiaserver.base.ApplicationConstants.Properties.PROPERTY_MAPQUEST_API_KEY;
import static com.example.rentiaserver.base.ApplicationConstants.Properties.PROPERTY_MAPQUEST_API_URI;

@GeocodingService(GeocodingServiceLayer.SECOND)
@PropertySource(APPLICATION_PROPERTIES)
final class ReverseMapQuestGeocodingService
        extends MapQuestGeocodingService
        implements IReverseGeocodingService {

    @Autowired
    public ReverseMapQuestGeocodingService(
            IHttpClientService httpClientService,
            IHttpResponseJsonConverter converter,
            @Value(PROPERTY_MAPQUEST_API_KEY) String apiKey,
            @Value(PROPERTY_MAPQUEST_API_URI) String baseUri) {

        super(httpClientService,
                converter,
                apiKey,
                baseUri);
    }

    @Override
    protected GeocodingType getGeocodingType() {
        return GeocodingType.REVERSE;
    }

    @Override
    protected String prepareQuery(LocationTo locationTo) {

        Double longitude = locationTo.getLongitude();
        Double latitude = locationTo.getLatitude();

        if (longitude == null || latitude == null) {
            throw new IllegalArgumentException("Reverse geocoding requires two params: latitude and longitude.");
        }

        return latitude + "," + longitude;
    }
}
