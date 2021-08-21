package com.example.rentiaserver.data.to;
import java.io.Serializable;

public class AnnouncementTo implements Serializable {

    private final String fromLatitude;
    private final String fromLongitude;
    private final String toLatitude;
    private final String toLongitude;
    private final Long authorId;

    public AnnouncementTo(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, Long authorId) {
        this.fromLatitude = fromLatitude;
        this.fromLongitude = fromLongitude;
        this.toLatitude = toLatitude;
        this.toLongitude = toLongitude;
        this.authorId = authorId;
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
}
