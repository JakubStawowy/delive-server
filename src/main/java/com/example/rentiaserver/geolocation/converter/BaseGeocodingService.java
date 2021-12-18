package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.http.IHttpClientService;
import com.example.rentiaserver.geolocation.http.IResponseJsonConverter;

public abstract class BaseGeocodingService implements IGeocodingService{

    protected final IHttpClientService httpClientService;
    protected final IResponseJsonConverter converter;

    public BaseGeocodingService(IHttpClientService httpClientService, IResponseJsonConverter converter) {
        this.httpClientService = httpClientService;
        this.converter = converter;
    }

    protected interface IGeocodingType {
        String getValue();
    }
}
