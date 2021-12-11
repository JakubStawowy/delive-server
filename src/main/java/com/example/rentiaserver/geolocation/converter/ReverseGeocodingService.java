package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class ReverseGeocodingService extends GeocodingService {

    @Autowired
    public ReverseGeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        super(httpClientService, converter);
    }

    @Override
    protected GeocodingType getGeocodingType() {
        return GeocodingType.REVERSE;
    }

    @Override
    protected String prepareQuery(LocationTo locationTo) {
        if (locationTo.getLongitude() == null || locationTo.getLatitude() == null) {
            throw new IllegalArgumentException("Reverse geocoding requires two params: latitude and longitude.");
        }
        return locationTo.getLatitude() + "," + locationTo.getLongitude();
    }
}
