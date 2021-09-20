package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.to.PackageTo;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.delivery.to.MessageTo;
import com.example.rentiaserver.delivery.to.OutgoingMessageTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = MessageLoadController.BASE_ENDPOINT)
public class MessageLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/messages";

    private final MessageDao messageDao;

    @Autowired
    public MessageLoadController(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @GetMapping("/sent")
    public List<MessageTo> loadMessagesSent(@RequestParam(value = "userId") Long userId) {
        List<MessageTo> messageTos = new ArrayList<>();

        messageDao.findAllBySender(userId).forEach(messagePo -> {
            Set<PackageTo> packages = new HashSet<>();
            messagePo.getPackages().forEach(singlePackage -> packages.add(new PackageTo(
                    singlePackage.getPackageLength().toString(),
                    singlePackage.getPackageWidth().toString(),
                    singlePackage.getPackageHeight().toString()
            )));
            messageTos.add(new OutgoingMessageTo(
                    messagePo.getAnnouncementPo().getId(),
                    messagePo.getSender().getId(),
                    messagePo.getReceiver().getId(),
                    messagePo.getMessage(),
                    messagePo.getId(),
                    messagePo.getCreatedAt(),
                    messagePo.getMessageType().name(),
                    messagePo.isReplied(),
                    packages
            ));
        });

        return messageTos;
    }

    @GetMapping("/received")
    public List<MessageTo> loadMessagesReceived(@RequestParam(value = "userId") Long userId) {
        List<MessageTo> messageTos = new ArrayList<>();

        messageDao.findAllByReceiver(userId).forEach(messagePo -> {
            Set<PackageTo> packages = new HashSet<>();
            messagePo.getPackages().forEach(singlePackage -> packages.add(new PackageTo(
                    singlePackage.getPackageLength().toString(),
                    singlePackage.getPackageWidth().toString(),
                    singlePackage.getPackageHeight().toString()
            )));
            messageTos.add(new OutgoingMessageTo(
                    messagePo.getAnnouncementPo().getId(),
                    messagePo.getSender().getId(),
                    messagePo.getReceiver().getId(),
                    messagePo.getMessage(),
                    messagePo.getId(),
                    messagePo.getCreatedAt(),
                    messagePo.getMessageType().name(),
                    messagePo.isReplied(),
                    packages
            ));
        });

        return messageTos;
    }
}