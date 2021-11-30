package com.example.rentiaserver.maps.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.http.HttpResponse;

public interface IResponseJsonConverter {
    JSONObject convertResponseToJSONObject(HttpResponse<String> response) throws ParseException;
    JSONArray convertResponseToJSONArray(HttpResponse<String> response, String rootElement) throws ParseException;
}
