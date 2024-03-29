package com.example.rentiaserver.delivery.model.po;

import com.example.rentiaserver.base.model.po.BaseEntityPo;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.delivery.api.MessageType;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
@Table(name = "TB_MESSAGE")
public class MessagePo extends BaseEntityPo {

    private String message;

    @Column(name = "VEHICLE_REGISTRATION_NUMBER")
    private String vehicleRegistrationNumber;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID")
    private OrderPo orderPo;

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

    public MessagePo(String message, OrderPo orderPo, UserPo senderPo, UserPo receiverPo, MessageType messageType) {
        this.message = message;
        this.orderPo = orderPo;
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

    public OrderPo getOrderPo() {
        return orderPo;
    }

    public void setOrderPo(OrderPo orderPo) {
        this.orderPo = orderPo;
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

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
