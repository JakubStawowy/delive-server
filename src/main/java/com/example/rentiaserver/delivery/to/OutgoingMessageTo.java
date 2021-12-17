package com.example.rentiaserver.delivery.to;

public class OutgoingMessageTo extends MessageTo {

    private final String messageType;
    private final boolean replied;

    public OutgoingMessageTo(Long id, String createdAt, Long announcementId, Long senderId, Long receiverId, String message, String vehicleRegistrationNumber, String phoneNumber, String messageType, boolean replied) {
        super(id, createdAt, announcementId, senderId, receiverId, message, vehicleRegistrationNumber, phoneNumber);
        this.messageType = messageType;
        this.replied = replied;
    }

    public String getMessageType() {
        return messageType;
    }

    public boolean isReplied() {
        return replied;
    }

}
