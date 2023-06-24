package com.example.rentiaserver.geolocation.http;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.Optional;

@Component
public final class HttpResponseJsonConverter implements IResponseJsonConverter {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseJsonConverter.class);
    private final JSONParser jsonParser = new JSONParser();

    @Override
    public Optional<JSONObject> convertResponseToJSONObject(HttpResponse<String> response) throws ParseException {
        return Optional.ofNullable((JSONObject) jsonParser.parse(response.body()));
    }

    @Override
    public JSONArray convertResponseToJSONArray(HttpResponse<String> response, String rootElement) {
        try {
            String responseString = convertResponseToJSONObject(response)
                    .map(object -> object.get(rootElement))
                    .map(String::valueOf)
                    .orElse(null);


            return (JSONArray) jsonParser.parse(responseString);

        } catch (ParseException ex) {
            logger.warn("ParseException occurred. Returning empty JSONArray.");
            return new JSONArray();
        }
    }
}
