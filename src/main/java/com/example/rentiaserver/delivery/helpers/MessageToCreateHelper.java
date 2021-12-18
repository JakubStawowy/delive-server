package com.example.rentiaserver.delivery.helpers;

import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.delivery.to.OutgoingMessageTo;

public class MessageToCreateHelper {
    public static OutgoingMessageTo create(MessagePo messagePo) {
        return new OutgoingMessageTo(
                messagePo.getId(),
                String.valueOf(messagePo.getCreatedAt()),
                messagePo.getOrderPo().getId(),
                messagePo.getSenderPo() != null ? messagePo.getSenderPo().getId() : null,
                messagePo.getReceiverPo().getId(),
                messagePo.getMessage(),
                messagePo.getVehicleRegistrationNumber(),
                messagePo.getPhoneNumber(),
                messagePo.getMessageType().name(),
                messagePo.isReplied()
        );
    }
}
