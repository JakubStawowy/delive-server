package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.DestinationPo;
import com.example.rentiaserver.data.to.*;

import java.util.stream.Collectors;

public final class AnnouncementToCreatorHelper {
    public static AnnouncementTo create(AnnouncementPo announcementPo) {
        DestinationPo destinationFrom = announcementPo.getDestinationFrom();
        DestinationPo destinationTo = announcementPo.getDestinationTo();
        return new AnnouncementTo(
                announcementPo.getId(),
                String.valueOf(announcementPo.getCreatedAt()),
                new DestinationTo(
                        destinationFrom.getId(),
                        String.valueOf(destinationFrom.getCreatedAt()),
                        destinationFrom.getLatitude(),
                        destinationFrom.getLongitude(),
                        destinationFrom.getAddress(),
                        destinationFrom.getLocality(),
                        destinationFrom.getCountry()
                ),
                new DestinationTo(
                        destinationTo.getId(),
                        String.valueOf(destinationTo.getCreatedAt()),
                        destinationTo.getLatitude(),
                        destinationTo.getLongitude(),
                        destinationTo.getAddress(),
                        destinationTo.getLocality(),
                        destinationTo.getCountry()
                ),
                announcementPo.getPackages().stream().map(packagePo -> new PackageTo(
                        packagePo.getId(),
                        String.valueOf(packagePo.getCreatedAt()),
                        String.valueOf(packagePo.getPackageLength()),
                        String.valueOf(packagePo.getPackageWidth()),
                        String.valueOf(packagePo.getPackageHeight())
                )).collect(Collectors.toSet()),
                announcementPo.getAuthor().getId(),
                announcementPo.getDate() != null ? announcementPo.getDate().toString() : null,
                announcementPo.getAmount() != null ? announcementPo.getAmount().toString() : null
        );
    }
}
