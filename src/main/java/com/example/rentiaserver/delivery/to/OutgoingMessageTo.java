package com.example.rentiaserver.delivery.to;

import com.example.rentiaserver.data.to.PackageTo;

import java.util.Date;
import java.util.Set;

public class OutgoingMessageTo extends MessageTo {

    private final Long messageId;
    private final Date createdAt;
    private final String messageType;
    private final boolean replied;
    private final Set<PackageTo> packages;

    public OutgoingMessageTo(Long announcementId, Long senderId, Long receiverId, String message, Long messageId, Date createdAt, String messageType, boolean replied, Set<PackageTo> packages) {
        super(announcementId, senderId, receiverId, message);
        this.messageId = messageId;
        this.createdAt = createdAt;
        this.messageType = messageType;
        this.replied = replied;
        this.packages = packages;
    }

    public Long getMessageId() {
        return messageId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getMessageType() {
        return messageType;
    }

    public boolean isReplied() {
        return replied;
    }

    public Set<PackageTo> getPackages() {
        return packages;
    }
}
