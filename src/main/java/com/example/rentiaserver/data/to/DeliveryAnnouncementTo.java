package com.example.rentiaserver.data.to;

public class DeliveryAnnouncementTo extends AnnouncementTo {

    private final String date;

    public DeliveryAnnouncementTo(String fromLatitude, String fromLongitude, String toLatitude, String toLongitude, Long authorId, String date) {
        super(fromLatitude, fromLongitude, toLatitude, toLongitude, authorId);
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
