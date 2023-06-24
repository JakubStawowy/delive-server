package com.example.rentiaserver.geolocation.http;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.http.HttpResponse;
import java.util.Optional;

public interface IResponseJsonConverter {
    Optional<JSONObject> convertResponseToJSONObject(HttpResponse<String> response) throws ParseException;
    JSONArray convertResponseToJSONArray(HttpResponse<String> response, String rootElement);
}
