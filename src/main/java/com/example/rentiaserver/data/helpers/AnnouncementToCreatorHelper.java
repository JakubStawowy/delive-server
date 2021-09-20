package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.to.DeliveryAnnouncementTo;
import com.example.rentiaserver.data.to.DestinationTo;
import com.example.rentiaserver.data.to.NormalAnnouncementTo;
import com.example.rentiaserver.data.po.DeliveryAnnouncementPo;
import com.example.rentiaserver.data.po.NormalAnnouncementPo;
import com.example.rentiaserver.data.to.PackageTo;

import java.util.HashSet;
import java.util.Set;

public final class AnnouncementToCreatorHelper {
    public static DeliveryAnnouncementTo create(DeliveryAnnouncementPo deliveryAnnouncement) {
        Set<PackageTo> packageTos = new HashSet<>();
        deliveryAnnouncement.getPackages().forEach(packagePo -> packageTos.add(new PackageTo(
                packagePo.getPackageLength().toString(), packagePo.getPackageWidth().toString(), packagePo.getPackageHeight().toString())));
        return new DeliveryAnnouncementTo(
                deliveryAnnouncement.getId(),
                new DestinationTo(
                        deliveryAnnouncement.getDestinationFrom().getLatitude(),
                        deliveryAnnouncement.getDestinationFrom().getLongitude(),
                        null
                ),
                new DestinationTo(
                        deliveryAnnouncement.getDestinationTo().getLatitude(),
                        deliveryAnnouncement.getDestinationTo().getLongitude(),
                        null
                ),
                packageTos,
                deliveryAnnouncement.getAuthor().getId(),
                deliveryAnnouncement.getDate().toString());
    }

    public static NormalAnnouncementTo create(NormalAnnouncementPo normalAnnouncement) {
        Set<PackageTo> packageTos = new HashSet<>();
        normalAnnouncement.getPackages().forEach(packagePo -> packageTos.add(new PackageTo(
                packagePo.getPackageLength().toString(), packagePo.getPackageWidth().toString(), packagePo.getPackageHeight().toString())));
        return new NormalAnnouncementTo(
                normalAnnouncement.getId(),
                new DestinationTo(
                        normalAnnouncement.getDestinationFrom().getLatitude(),
                        normalAnnouncement.getDestinationFrom().getLongitude(),
                        null
                ),
                new DestinationTo(
                        normalAnnouncement.getDestinationTo().getLatitude(),
                        normalAnnouncement.getDestinationTo().getLongitude(),
                        null
                ),
                packageTos,
                normalAnnouncement.getAuthor().getId()
                );
    }
}
