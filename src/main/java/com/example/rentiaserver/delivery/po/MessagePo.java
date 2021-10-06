package com.example.rentiaserver.delivery.po;

import com.example.rentiaserver.data.api.BaseEntityPo;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.MessagePackagePo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.enums.MessageType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MESSAGES")
public class MessagePo extends BaseEntityPo {

    private String message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ANNOUNCEMENT_ID")
    private AnnouncementPo announcementPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SENDER_ID")
    private UserPo sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RECEIVER_ID")
    private UserPo receiver;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "MESSAGE_TYPE")
    private MessageType messageType;

    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MessagePackagePo> packages;

    private boolean replied;

    public MessagePo(String message, AnnouncementPo announcementPo, UserPo sender, UserPo receiver, MessageType messageType) {
        this.message = message;
        this.announcementPo = announcementPo;
        this.sender = sender;
        this.receiver = receiver;
        this.messageType = messageType;
    }

    @Override
    public void init() {
        super.init();
        replied = false;
    }

    public MessagePo() {
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

    public UserPo getSender() {
        return sender;
    }

    public void setSender(UserPo sender) {
        this.sender = sender;
    }

    public UserPo getReceiver() {
        return receiver;
    }

    public void setReceiver(UserPo receiver) {
        this.receiver = receiver;
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

    public void setReplied(boolean archived) {
        this.replied = archived;
    }

    public Set<MessagePackagePo> getPackages() {
        return packages;
    }

    public void setPackages(Set<MessagePackagePo> packages) {
        this.packages = packages;
    }
}
