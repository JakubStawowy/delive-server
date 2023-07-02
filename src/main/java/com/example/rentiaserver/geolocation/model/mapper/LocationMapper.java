package com.example.rentiaserver.geolocation.model.mapper;

import com.example.rentiaserver.geolocation.model.po.LocationPo;
import com.example.rentiaserver.geolocation.model.to.LocationTo;

public class LocationMapper {

    public static LocationTo mapLocationPoToTo(LocationPo location) {
        return LocationTo.getBuilder()
                .setAddress(location.getAddress())
                .setLatitude(location.getLatitude())
                .setLongitude(location.getLongitude())
                .setId(location.getId())
                .setCreatedAt(location.getCreatedAt())
                .build();
    }

}
