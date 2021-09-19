package com.example.rentiaserver.delivery.to;

public class IncomingMessageTo extends MessageTo {

    private final boolean consent;
    private final Long replyMessageId;

    public IncomingMessageTo(Long announcementId, Long senderId, Long receiverId, String message, boolean consent, Long replyMessageId) {
        super(announcementId, senderId, receiverId, message);
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
