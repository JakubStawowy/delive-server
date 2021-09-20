package com.example.rentiaserver.data.to;

import java.util.Set;

public class NormalAnnouncementTo extends AnnouncementTo {


    public NormalAnnouncementTo(Long announcementId, DestinationTo destinationFrom, DestinationTo destinationTo, Set<PackageTo> packages, Long authorId) {
        super(announcementId, destinationFrom, destinationTo, packages, authorId);
    }
}
