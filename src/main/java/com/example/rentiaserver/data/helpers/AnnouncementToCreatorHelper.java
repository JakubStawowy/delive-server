package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.maps.po.LocationPo;
import com.example.rentiaserver.data.to.*;
import com.example.rentiaserver.maps.to.LocationTo;

import java.util.stream.Collectors;

public final class AnnouncementToCreatorHelper {
    public static AnnouncementTo create(AnnouncementPo announcementPo) {
        LocationPo destinationFrom = announcementPo.getInitialLocationPo();
        LocationPo destinationTo = announcementPo.getFinalLocationPo();
        return new AnnouncementTo(
                announcementPo.getId(),
                String.valueOf(announcementPo.getCreatedAt()),
                new LocationTo(
                        destinationFrom.getId(),
                        String.valueOf(destinationFrom.getCreatedAt()),
                        destinationFrom.getLatitude(),
                        destinationFrom.getLongitude(),
                        destinationFrom.getAddress()
                ),
                new LocationTo(
                        destinationTo.getId(),
                        String.valueOf(destinationTo.getCreatedAt()),
                        destinationTo.getLatitude(),
                        destinationTo.getLongitude(),
                        destinationTo.getAddress()
                ),
                announcementPo.getPackagesPos().stream().map(packagePo -> new PackageTo(
                        packagePo.getId(),
                        String.valueOf(packagePo.getCreatedAt()),
                        String.valueOf(packagePo.getPackageLength()),
                        String.valueOf(packagePo.getPackageWidth()),
                        String.valueOf(packagePo.getPackageHeight())
                )).collect(Collectors.toSet()),
                announcementPo.getAuthorPo().getId(),
                announcementPo.getAmount() != null ? announcementPo.getAmount().toString() : null,
                announcementPo.isRequireTransportWithClient()
        );
    }
}
