package com.example.rentiaserver.geolocation.service.geocoding;

import com.example.rentiaserver.http.IHttpClientService;
import com.example.rentiaserver.http.IHttpResponseJsonConverter;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import static com.example.rentiaserver.base.ApplicationConstants.Properties.APPLICATION_PROPERTIES;
import static com.example.rentiaserver.base.ApplicationConstants.Properties.PROPERTY_POSITION_STACK_API_KEY;
import static com.example.rentiaserver.base.ApplicationConstants.Properties.PROPERTY_POSITION_STACK_API_URI;

@GeocodingService(GeocodingServiceLayer.FIRST)
@PropertySource(APPLICATION_PROPERTIES)
final class ForwardPositionStackGeocodingService
        extends PositionStackGeocodingService
        implements IForwardGeocodingService{

    @Autowired
    public ForwardPositionStackGeocodingService(
            IHttpClientService httpClientService,
            IHttpResponseJsonConverter converter,
            @Value(PROPERTY_POSITION_STACK_API_KEY) String apiKey,
            @Value(PROPERTY_POSITION_STACK_API_URI) String baseUri) {

        super(httpClientService,
                converter,
                apiKey,
                baseUri);
    }

    @Override
    protected GeocodingType getGeocodingType() {
        return GeocodingType.FORWARD;
    }

    @Override
    protected String prepareQuery(LocationTo locationTo) {
        String address = locationTo.getAddress();
        if (address == null || address.length() < 3) {
            throw new IllegalArgumentException(
                    "Forward geocoding requires not empty address param with at least 3 characters.");
        }
        return address.replaceAll(",", " ");
    }
}
