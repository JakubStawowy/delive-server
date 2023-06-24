package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.api.IGeocodingService;
import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;
import com.example.rentiaserver.geolocation.to.LocationTo;

abstract class BaseGeocodingService implements IGeocodingService {

    protected final IHttpClientService httpClientService;

    protected final IResponseJsonConverter converter;

    public BaseGeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        this.httpClientService = httpClientService;
        this.converter = converter;
    }

    protected abstract String prepareQuery(LocationTo locationTo);

    protected abstract HasValue getGeocodingType();

    protected interface HasValue {

        String getValue();
    }
}
