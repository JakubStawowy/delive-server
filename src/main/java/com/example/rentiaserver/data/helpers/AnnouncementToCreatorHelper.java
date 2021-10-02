package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.to.*;

import java.util.stream.Collectors;

public final class AnnouncementToCreatorHelper {
    public static AnnouncementTo create(AnnouncementPo announcementPo) {
        return new AnnouncementTo(
                announcementPo.getId(),
                new DestinationTo(
                        announcementPo.getDestinationFrom().getLatitude(),
                        announcementPo.getDestinationFrom().getLongitude(),
                        announcementPo.getDestinationFrom().getAddress()
                ),
                new DestinationTo(
                        announcementPo.getDestinationTo().getLatitude(),
                        announcementPo.getDestinationTo().getLongitude(),
                        announcementPo.getDestinationTo().getAddress()
                ),
                announcementPo.getPackages().stream().map(packagePo -> new PackageTo(
                        packagePo.getPackageLength().toString(),
                        packagePo.getPackageWidth().toString(),
                        packagePo.getPackageHeight().toString()
                )).collect(Collectors.toSet()),
                announcementPo.getAuthor().getId(),
                announcementPo.getDate() != null ? announcementPo.getDate().toString() : null
        );
    }
}
