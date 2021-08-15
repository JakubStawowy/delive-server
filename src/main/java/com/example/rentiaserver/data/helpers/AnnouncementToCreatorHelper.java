package com.example.rentiaserver.data.helpers;

import com.example.rentiaserver.data.to.DeliveryAnnouncementTo;
import com.example.rentiaserver.data.to.NormalAnnouncementTo;
import com.example.rentiaserver.data.po.DeliveryAnnouncementPo;
import com.example.rentiaserver.data.po.NormalAnnouncementPo;

public final class AnnouncementToCreatorHelper {
    public static DeliveryAnnouncementTo create(DeliveryAnnouncementPo deliveryAnnouncement) {
        return new DeliveryAnnouncementTo(
                deliveryAnnouncement.getDestinationFrom().getLatitude().toString(),
                deliveryAnnouncement.getDestinationFrom().getLongitude().toString(),
                deliveryAnnouncement.getDestinationTo().getLatitude().toString(),
                deliveryAnnouncement.getDestinationTo().getLongitude().toString(),
                deliveryAnnouncement.getAuthor().getId(),
                deliveryAnnouncement.getDate().toString()
        );
    }

    public static NormalAnnouncementTo create(NormalAnnouncementPo normalAnnouncement) {
        return new NormalAnnouncementTo(
                normalAnnouncement.getDestinationFrom().getLatitude().toString(),
                normalAnnouncement.getDestinationFrom().getLongitude().toString(),
                normalAnnouncement.getDestinationTo().getLatitude().toString(),
                normalAnnouncement.getDestinationTo().getLongitude().toString(),
                normalAnnouncement.getAuthor().getId(),
                normalAnnouncement.getPackageLength().toString(),
                normalAnnouncement.getPackageWidth().toString(),
                normalAnnouncement.getPackageHeight().toString()
        );
    }
}
