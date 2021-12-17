package com.example.rentiaserver.geolocation.http;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public final class HttpResponseJsonConverter implements IResponseJsonConverter {

    private final JSONParser jsonParser = new JSONParser();

    @Override
    public JSONObject convertResponseToJSONObject(HttpResponse<String> response) throws ParseException {
        return (JSONObject) jsonParser.parse(response.body());
    }

    @Override
    public JSONArray convertResponseToJSONArray(HttpResponse<String> response, String rootElement) throws ParseException {

        try {
            JSONObject rootObject = convertResponseToJSONObject(response);
            return (JSONArray) jsonParser.parse(String.valueOf(rootObject.get(rootElement)));
        } catch (Exception ex) {
            Logger.getGlobal().log(Level.WARNING, "ParseException occurred. Returning empty JSONArray.");
            return new JSONArray();
        }
    }
}
