package com.example.rentiaserver.delivery.model.mapper;

import com.example.rentiaserver.delivery.model.po.MessagePo;
import com.example.rentiaserver.delivery.model.to.OutgoingMessageTo;

public class MessageMapper {

    public static OutgoingMessageTo mapMessagePoToOutgoingMessageTo(MessagePo messagePo) {

        if (messagePo == null) {
            return null;
        }

        return OutgoingMessageTo.builder()
                .id(messagePo.getId())
                .createdAt(messagePo.getCreatedAt())
                .orderId(messagePo.getOrderPo().getId())
                .senderId(messagePo.getSenderPo() != null ? messagePo.getSenderPo().getId() : null)
                .receiverId(messagePo.getReceiverPo().getId())
                .message(messagePo.getMessage())
                .vehicleRegistrationNumber(messagePo.getVehicleRegistrationNumber())
                .phoneNumber(messagePo.getPhoneNumber())
                .messageType(messagePo.getMessageType())
                .replied(messagePo.isReplied())
                .build();
    }
}
