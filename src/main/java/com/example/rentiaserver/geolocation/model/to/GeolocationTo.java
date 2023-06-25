package com.example.rentiaserver.geolocation.model.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeolocationTo implements Serializable {
    private double latitude;
    private double longitude;
    private long zoomLevel;
}
