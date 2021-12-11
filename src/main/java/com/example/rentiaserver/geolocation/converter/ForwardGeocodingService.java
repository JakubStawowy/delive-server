package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class ForwardGeocodingService extends GeocodingService {

    @Autowired
    public ForwardGeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        super(httpClientService, converter);
    }

    @Override
    protected GeocodingType getGeocodingType() {
        return GeocodingType.FORWARD;
    }

    @Override
    protected String prepareQuery(LocationTo locationTo) {
        String address = locationTo.getAddress();
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Forward geocoding requires not empty address param.");
        }
        return address.replaceAll(",", " ");
    }
}




