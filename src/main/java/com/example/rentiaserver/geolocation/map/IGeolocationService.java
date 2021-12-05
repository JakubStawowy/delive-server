package com.example.rentiaserver.geolocation.map;

interface IGeolocationService {
    double getHalfwayPoint(double firstCoordinate, double secondCoordinate);
    long getZoomLevel(double fromLongitude, double toLongitude, double mapWidth);
    long getZoomLevel(double fromLongitude, double fromLatitude,
                      double toLongitude, double toLatitude, double mapWidth);
}
