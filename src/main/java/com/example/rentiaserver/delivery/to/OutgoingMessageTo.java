package com.example.rentiaserver.delivery.to;

import com.example.rentiaserver.data.to.PackageTo;

import java.util.Date;
import java.util.Set;

public class OutgoingMessageTo extends MessageTo {

    private final String messageType;
    private final boolean replied;

    public OutgoingMessageTo(Long id, String createdAt, Long announcementId, Long senderId, Long receiverId, String message, String messageType, boolean replied) {
        super(id, createdAt, announcementId, senderId, receiverId, message);
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
