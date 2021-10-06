package com.example.rentiaserver.delivery.to;

public class IncomingMessageTo extends MessageTo {

    private final boolean consent;
    private final Long replyMessageId;

    public IncomingMessageTo(Long id, String createdAt, Long announcementId, Long senderId, Long receiverId, String message, boolean consent, Long replyMessageId) {
        super(id, createdAt, announcementId, senderId, receiverId, message);
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
