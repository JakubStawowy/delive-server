package com.example.rentiaserver.delivery.to;

public class IncomingMessageTo extends MessageTo {

    private final boolean consent;
    private final Long replyMessageId;

    public IncomingMessageTo(Long id, String createdAt, Long orderId, Long senderId, Long receiverId, String message, String vehicleRegistrationNumber, String phoneNumber, boolean consent, Long replyMessageId) {
        super(id, createdAt, orderId, senderId, receiverId, message, vehicleRegistrationNumber, phoneNumber);
        this.consent = consent;
        this.replyMessageId = replyMessageId;
    }

    public boolean isConsent() {
        return consent;
    }

    public Long getReplyMessageId() {
        return replyMessageId;
    }
}
