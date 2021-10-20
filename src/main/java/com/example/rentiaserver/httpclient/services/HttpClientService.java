package com.example.rentiaserver.httpclient.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

@Service
public class HttpClientService {

    private static final long REQUEST_TIMEOUT = 10000;

    public HttpResponse<String> getHttpResponse(String baseUri, Map<String, String> params) throws IOException, InterruptedException {

        String finalUri = buildUri(baseUri, params);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(finalUri))
                .timeout(Duration.ofMillis(REQUEST_TIMEOUT)).build();

        return httpClient.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());
    }

    private String buildUri(String baseUri, Map<String, String> params) {
        StringBuilder uriWithParams = new StringBuilder(baseUri);
        if (!params.isEmpty()) {
            uriWithParams.append("?");
            params.forEach((key, value) -> uriWithParams.append(key)
                    .append("=")
                    .append(value)
                    .append("&"));
            return uriWithParams
                    .substring(0, uriWithParams.length());
        }
        else {
            return uriWithParams.toString();
        }
    }
}