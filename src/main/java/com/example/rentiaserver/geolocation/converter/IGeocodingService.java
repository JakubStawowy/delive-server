package com.example.rentiaserver.geolocation.converter;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface IGeocodingService {
    JSONObject getLocationDataFromCoordinates(double longitude, double latitude) throws IOException, InterruptedException, ParseException;
    JSONObject getLocationDataFromAddress(String address) throws IOException, InterruptedException, ParseException;
}
