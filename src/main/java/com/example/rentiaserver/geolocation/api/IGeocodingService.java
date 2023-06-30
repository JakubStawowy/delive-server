package com.example.rentiaserver.geolocation.api;

import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IGeocodingService {

    LocationType getAcceptedLocationType();

    List<LocationTo> getConvertedLocations(LocationTo locationTo) throws IOException, InterruptedException, ParseException;

    Optional<LocationTo> getSingleLocationData(LocationTo locationTo) throws IOException, InterruptedException, ParseException;
}
