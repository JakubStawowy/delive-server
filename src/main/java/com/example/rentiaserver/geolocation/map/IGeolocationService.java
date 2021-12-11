package com.example.rentiaserver.geolocation.map;

interface IGeolocationService {
    double getHalfwayPoint(double firstCoordinate, double secondCoordinate);
//    long getZoomLevel(double fromLongitude, double toLongitude, double mapWidth);
    long getZoomLevel(final double fromLongitude, final double fromLatitude,
                      final double toLongitude, final double toLatitude);
//    long getZoomLevel(double fromLongitude, double fromLatitude,
//                      double toLongitude, double toLatitude, double mapWidth);
}
