package com.example.rentiaserver.data.to;

import java.util.Set;

public class DeliveryAnnouncementTo extends AnnouncementTo {

    private final String date;

    public DeliveryAnnouncementTo(Long announcementId, String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, Set<PackageTo> packages, Long authorId, String date) {
        super(announcementId, fromLatitude, fromLongitude, toLatitude, toLongitude, packages, authorId);
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
