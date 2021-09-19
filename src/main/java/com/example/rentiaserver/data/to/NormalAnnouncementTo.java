package com.example.rentiaserver.data.to;

import java.util.Set;

public class NormalAnnouncementTo extends AnnouncementTo {


    public NormalAnnouncementTo(Long announcementId, String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, Set<PackageTo> packages, Long authorId) {
        super(announcementId, fromLatitude, fromLongitude, toLatitude, toLongitude, packages, authorId);
    }
}
