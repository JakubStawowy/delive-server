package com.example.rentiaserver.geolocation.distance;

import com.example.rentiaserver.geolocation.to.LocationTo;

public interface IDistanceCalculator {
    double getDistance(LocationTo initialLocation, LocationTo finalLocation);
}



