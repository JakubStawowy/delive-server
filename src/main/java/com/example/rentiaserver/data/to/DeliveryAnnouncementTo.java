package com.example.rentiaserver.data.to;

import java.util.Set;

public class DeliveryAnnouncementTo extends AnnouncementTo {

    private final String date;

    public DeliveryAnnouncementTo(Long announcementId, DestinationTo destinationFrom, DestinationTo destinationTo, Set<PackageTo> packages, Long authorId, String date) {
        super(announcementId, destinationFrom, destinationTo, packages, authorId);
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
