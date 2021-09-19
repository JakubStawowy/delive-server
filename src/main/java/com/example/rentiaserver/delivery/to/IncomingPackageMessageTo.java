package com.example.rentiaserver.delivery.to;

import com.example.rentiaserver.data.to.PackageTo;

import java.util.Set;

public class IncomingPackageMessageTo extends MessageTo {

    private Set<PackageTo> packages;

    public IncomingPackageMessageTo(Long announcementId, Long senderId, Long receiverId, String message, Set<PackageTo> packages) {
        super(announcementId, senderId, receiverId, message);
        this.packages = packages;
    }

    public Set<PackageTo> getPackages() {
        return packages;
    }
}
