package com.example.rentiaserver.data.to;
import java.io.Serializable;
import java.util.Set;

public class AnnouncementTo implements Serializable {

    private final Long announcementId;
    private final String fromLatitude;
    private final String fromLongitude;
    private final String toLatitude;
    private final String toLongitude;
    private final Set<PackageTo> packages;
    private final Long authorId;

    public AnnouncementTo(Long announcementId, String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, Set<PackageTo> packages, Long authorId) {
        this.announcementId = announcementId;
        this.fromLatitude = fromLatitude;
        this.fromLongitude = fromLongitude;
        this.toLatitude = toLatitude;
        this.toLongitude = toLongitude;
        this.packages = packages;
        this.authorId = authorId;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public String getFromLatitude() {
        return fromLatitude;
    }

    public String getFromLongitude() {
        return fromLongitude;
    }

    public String getToLatitude() {
        return toLatitude;
    }

    public String getToLongitude() {
        return toLongitude;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Set<PackageTo> getPackages() {
        return packages;
    }
}
