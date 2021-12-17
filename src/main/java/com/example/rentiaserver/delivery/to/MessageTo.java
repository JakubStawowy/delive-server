package com.example.rentiaserver.delivery.to;

import com.example.rentiaserver.data.api.BaseEntityTo;

public abstract class MessageTo extends BaseEntityTo {

    private final Long announcementId;
    private final Long senderId;
    private final Long receiverId;
    private final String message;
    private final String vehicleRegistrationNumber;
    private final String phoneNumber;

    public MessageTo(Long id, String createdAt, Long announcementId, Long senderId, Long receiverId, String message, String vehicleRegistrationNumber, String phoneNumber) {
        super(id, createdAt);
        this.announcementId = announcementId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.phoneNumber = phoneNumber;
    }

    public Long getAnnouncementId() {
        return announcementId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public String getMessage() {
        return message;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
