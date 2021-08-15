package com.example.rentiaserver.data.enums;

public enum AnnouncementStatus {
    FREE,
    RENTED,
    RETURNED,
    RESERVED,
    BLOCKED;
    public static AnnouncementStatus get(String status) {
        if (status.equals(FREE.toString())) return AnnouncementStatus.FREE;
        if (status.equals(RENTED.toString())) return AnnouncementStatus.RENTED;
        if (status.equals(RETURNED.toString())) return AnnouncementStatus.RETURNED;
        if (status.equals(BLOCKED.toString())) return AnnouncementStatus.BLOCKED;
        if (status.equals(RESERVED.toString())) return AnnouncementStatus.RESERVED;
        return null;
    }
}
