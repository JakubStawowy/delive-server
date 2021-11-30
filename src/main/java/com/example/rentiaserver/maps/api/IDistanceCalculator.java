package com.example.rentiaserver.maps.api;

import com.example.rentiaserver.maps.to.LocationTo;

public interface IDistanceCalculator {
    double getDistance(LocationTo initialLocation, LocationTo finalLocation);
}
