package com.example.rentiaserver.maps.api;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

public interface IHttpClientService {
    HttpResponse<String> getHttpResponse(String baseUri, Map<String, String> params) throws IOException, InterruptedException;
}
