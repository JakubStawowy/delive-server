package com.example.rentiaserver.delivery.to;

import java.io.Serializable;

public class MessageTo implements Serializable {

    private final Long announcementId;
    private final Long senderId;
    private final Long receiverId;
    private final String message;

    public MessageTo(Long announcementId, Long senderId, Long receiverId, String message) {
        this.announcementId = announcementId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
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
}
