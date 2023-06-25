package com.example.rentiaserver.http;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

@Component
final class HttpClientService implements IHttpClientService {

    private static final long REQUEST_TIMEOUT = 10000;

    @Override
    public HttpResponse<String> getHttpResponse(String baseUri, Map<String, String> params)
            throws IOException, InterruptedException {

        String finalUri = buildUri(baseUri, params);
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(finalUri))
                .timeout(Duration.ofMillis(REQUEST_TIMEOUT))
                .build();

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    private String buildUri(String baseUri, Map<String, String> params) {

        StringBuilder uriWithParams = new StringBuilder(baseUri);
        if (!params.isEmpty()) {
            uriWithParams.append("?");
            params.forEach((key, value) -> uriWithParams.append(key)
                    .append("=")
                    .append(value)
                    .append("&"));
        }

        return uriWithParams.toString()
                .replace(" ", "+");
    }
}
