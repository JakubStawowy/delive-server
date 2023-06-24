package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import static com.example.rentiaserver.ApplicationConstants.Paths.APPLICATION_PROPERTIES;
import static com.example.rentiaserver.ApplicationConstants.Paths.PROPERTY_MAPQUEST_API_KEY;

@Service
@PropertySource(APPLICATION_PROPERTIES)
final class ForwardMapQuestGeocodingService
        extends MapQuestGeocodingService
        implements IForwardGeocodingService{

    @Autowired
    public ForwardMapQuestGeocodingService(
            IHttpClientService httpClientService,
            IResponseJsonConverter converter,
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
