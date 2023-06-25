package com.example.rentiaserver.geolocation.service.geocoding;

import com.example.rentiaserver.http.IHttpClientService;
import com.example.rentiaserver.http.IHttpResponseJsonConverter;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import static com.example.rentiaserver.base.ApplicationConstants.Paths.APPLICATION_PROPERTIES;
import static com.example.rentiaserver.base.ApplicationConstants.Paths.PROPERTY_MAPQUEST_API_KEY;

@Service
@PropertySource(APPLICATION_PROPERTIES)
final class ForwardMapQuestGeocodingService
        extends MapQuestGeocodingService
        implements IForwardGeocodingService{

    @Autowired
    public ForwardMapQuestGeocodingService(
            IHttpClientService httpClientService,
            IHttpResponseJsonConverter converter,
            @Value(PROPERTY_MAPQUEST_API_KEY) String apiKey) {

        super(httpClientService, converter, apiKey);
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
