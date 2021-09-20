package com.example.rentiaserver.data.to;
import java.io.Serializable;
import java.util.Set;

public class AnnouncementTo implements Serializable {

    private final Long announcementId;
    private final DestinationTo destinationFrom;
    private final DestinationTo destinationTo;
    private final Set<PackageTo> packages;
    private final Long authorId;

    public AnnouncementTo(Long announcementId, DestinationTo destinationFrom, DestinationTo destinationTo, Set<PackageTo> packages, Long authorId) {
        this.announcementId = announcementId;
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.packages = packages;
        this.authorId = authorId;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public DestinationTo getDestinationFrom() {
        return destinationFrom;
    }

    public DestinationTo getDestinationTo() {
        return destinationTo;
    }

    public Set<PackageTo> getPackages() {
        return packages;
    }

    public Long getAuthorId() {
        return authorId;
    }
}