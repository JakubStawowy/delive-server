package com.example.rentiaserver.delivery.helpers;

import com.example.rentiaserver.data.to.PackageTo;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.delivery.to.OutgoingMessageTo;

import java.util.stream.Collectors;

public class MessageToCreateHelper {
    public static OutgoingMessageTo create(MessagePo messagePo) {
        return new OutgoingMessageTo(
                messagePo.getId(),
                String.valueOf(messagePo.getCreatedAt()),
                messagePo.getAnnouncementPo().getId(),
                messagePo.getSenderPo().getId(),
                messagePo.getReceiverPo().getId(),
                messagePo.getMessage(),
                messagePo.getMessageType().name(),
                messagePo.isReplied(),
                messagePo.getPackagePos().stream().map(messagePackagePo -> new PackageTo(
                        messagePackagePo.getId(),
                        String.valueOf(messagePackagePo.getCreatedAt()),
                        String.valueOf(messagePackagePo.getPackageLength()),
                        String.valueOf(messagePackagePo.getPackageWidth()),
                        String.valueOf(messagePackagePo.getPackageHeight())
                )).collect(Collectors.toSet())
        );
    }
}
