package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.geolocation.po.LocationPo;
import com.example.rentiaserver.data.to.*;
import com.example.rentiaserver.geolocation.to.LocationTo;

import java.util.Set;
import java.util.stream.Collectors;

public final class OrderToCreatorHelper {
    public static AnnouncementTo create(AnnouncementPo announcementPo) {
        LocationPo destinationFrom = announcementPo.getInitialLocationPo();
        LocationPo destinationTo = announcementPo.getFinalLocationPo();
        Set<PackageTo> packageTos =
                announcementPo.getPackagesPos().stream().map(packagePo -> new PackageTo(
                        packagePo.getId(),
                        String.valueOf(packagePo.getCreatedAt()),
                        String.valueOf(packagePo.getPackageLength()),
                        packagePo.getLengthUnit(),
                        String.valueOf(packagePo.getPackageWidth()),
                        packagePo.getWidthUnit(),
                        String.valueOf(packagePo.getPackageHeight()),
                        packagePo.getHeightUnit(),
                        String.valueOf(packagePo.getWeight())
                )).collect(Collectors.toSet());
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
                packageTos,
                announcementPo.getAuthorPo().getId(),
                announcementPo.getAmount() != null ? announcementPo.getAmount().toString() : null,
                announcementPo.isRequireTransportWithClient(),
                String.valueOf(PackagesWeightCounterHelper.sumPackagesWeights(packageTos)),
                announcementPo.getWeightUnit()
        );
    }
}
