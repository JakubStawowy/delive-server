package com.example.rentiaserver.delivery.helpers;

import com.example.rentiaserver.data.to.PackageTo;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.delivery.to.OutgoingMessageTo;

import java.util.stream.Collectors;

public class MessageToCreateHelper {
    public static OutgoingMessageTo create(MessagePo messagePo) {
        return new OutgoingMessageTo(
                messagePo.getAnnouncementPo().getId(),
                messagePo.getSender().getId(),
                messagePo.getReceiver().getId(),
                messagePo.getMessage(),
                messagePo.getId(),
                messagePo.getCreatedAt(),
                messagePo.getMessageType().name(),
                messagePo.isReplied(),
                messagePo.getPackages().stream().map(messagePackagePo -> new PackageTo(
                        messagePackagePo.getPackageLength().toString(),
                        messagePackagePo.getPackageWidth().toString(),
                        messagePackagePo.getPackageHeight().toString()
                )).collect(Collectors.toSet())
        );
    }
}
