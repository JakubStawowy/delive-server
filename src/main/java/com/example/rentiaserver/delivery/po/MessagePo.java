package com.example.rentiaserver.delivery.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.enums.MessageType;

import javax.persistence.*;

@Entity
@Table(name = "MESSAGES")
public class MessagePo extends BaseEntityPo {

    private String message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ANNOUNCEMENT_ID")
    private AnnouncementPo announcementPo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID")
    private UserPo senderPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RECEIVER_ID")
    private UserPo receiverPo;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "MESSAGE_TYPE")
    private MessageType messageType;

    private boolean replied;

    public MessagePo(String message, AnnouncementPo announcementPo, UserPo senderPo, UserPo receiverPo, MessageType messageType) {
        this.message = message;
        this.announcementPo = announcementPo;
        this.senderPo = senderPo;
        this.receiverPo = receiverPo;
        this.messageType = messageType;
    }

    public MessagePo() {}

    @Override
    public void init() {
        super.init();
        replied = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AnnouncementPo getAnnouncementPo() {
        return announcementPo;
    }

    public void setAnnouncementPo(AnnouncementPo announcementPo) {
        this.announcementPo = announcementPo;
    }

    public UserPo getSenderPo() {
        return senderPo;
    }

    public void setSenderPo(UserPo senderPo) {
        this.senderPo = senderPo;
    }

    public UserPo getReceiverPo() {
        return receiverPo;
    }

    public void setReceiverPo(UserPo receiverPo) {
        this.receiverPo = receiverPo;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public boolean isReplied() {
        return replied;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }
}
