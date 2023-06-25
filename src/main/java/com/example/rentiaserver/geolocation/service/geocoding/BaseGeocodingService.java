package com.example.rentiaserver.geolocation.service.geocoding;

import com.example.rentiaserver.geolocation.api.IGeocodingService;
import com.example.rentiaserver.http.IHttpClientService;
import com.example.rentiaserver.http.IHttpResponseJsonConverter;
import com.example.rentiaserver.geolocation.model.to.LocationTo;

abstract class BaseGeocodingService implements IGeocodingService {

    protected final IHttpClientService httpClientService;

    protected final IHttpResponseJsonConverter converter;

    public BaseGeocodingService(IHttpClientService httpClientService, IHttpResponseJsonConverter converter) {
        this.httpClientService = httpClientService;
        this.converter = converter;
    }

    protected abstract String prepareQuery(LocationTo locationTo);

    protected abstract HasValue getGeocodingType();

    protected interface HasValue {

        String getValue();
    }
}
