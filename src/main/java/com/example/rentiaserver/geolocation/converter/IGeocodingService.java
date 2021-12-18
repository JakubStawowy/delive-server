package com.example.rentiaserver.geolocation.converter;

import com.example.rentiaserver.geolocation.to.LocationTo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface IGeocodingService {

    JSONArray getLocationsData(LocationTo locationTo) throws IOException, InterruptedException, ParseException;

    JSONObject getFullLocationData(LocationTo locationTo) throws IOException, InterruptedException, ParseException;
}
