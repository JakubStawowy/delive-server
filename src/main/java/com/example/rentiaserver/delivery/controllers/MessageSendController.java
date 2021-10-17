package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.services.AnnouncementService;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.data.services.UserService;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.enums.MessageType;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.delivery.services.MessageService;
import com.example.rentiaserver.delivery.to.IncomingMessageTo;
import com.example.rentiaserver.delivery.to.IncomingPackageMessageTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = MessageSendController.BASE_ENDPOINT)
public class MessageSendController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/messages";

    private final UserService userService;
    private final AnnouncementService announcementService;
    private final MessageService messageService;
    private final DeliveryDao deliveryRepository;

    @Autowired
    public MessageSendController(
            UserService userService,
            AnnouncementService announcementService,
            MessageService messageService,
            DeliveryDao deliveryRepository
    ) {
        this.userService = userService;
        this.announcementService = announcementService;
        this.messageService = messageService;
        this.deliveryRepository = deliveryRepository;
    }

    @PostMapping("/register/normal")
    public void registerMessageNormal(@RequestBody IncomingMessageTo incomingMessageTo) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = userService.findUserById(incomingMessageTo.getSenderId());
        Optional<UserPo> optionalReceiverPo = userService.findUserById(incomingMessageTo.getReceiverId());
        optionalAnnouncementPo.flatMap(announcementPo -> optionalSenderPo).flatMap(senderPo -> optionalReceiverPo)
                .ifPresent(receiver -> messageService.saveMessage(
                        new MessagePo(
                                incomingMessageTo.getMessage(),
                                optionalAnnouncementPo.get(),
                                optionalSenderPo.get(),
                                optionalReceiverPo.get(),
                                MessageType.REQUEST)));
    }

    @Deprecated
    @PostMapping("/register/delivery")
    public void registerMessageDelivery(@RequestBody IncomingPackageMessageTo incomingMessageTo) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = userService.findUserById(incomingMessageTo.getSenderId());
        Optional<UserPo> optionalReceiverPo = userService.findUserById(incomingMessageTo.getReceiverId());
        if (optionalAnnouncementPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent()) {

            MessagePo message = new MessagePo(
                    incomingMessageTo.getMessage(),
                    optionalAnnouncementPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    MessageType.REQUEST
            );
            messageService.saveMessage(message);
            Set<PackagePo> packages = new HashSet<>();
            incomingMessageTo.getPackages().forEach(singlePackage -> packages.add(new MessagePackagePo(
                    new BigDecimal(singlePackage.getPackageLength()),
                    new BigDecimal(singlePackage.getPackageWidth()),
                    new BigDecimal(singlePackage.getPackageHeight()),
                    message
            )));
            announcementService.saveAllPackages(packages);
        }
    }

    @PostMapping("/reply")
    public void replyOnMessage(@RequestBody IncomingMessageTo incomingMessageTo) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = userService.findUserById(incomingMessageTo.getSenderId());
        Optional<UserPo> optionalReceiverPo = userService.findUserById(incomingMessageTo.getReceiverId());
        Optional<MessagePo> optionalRepliedMessage = messageService.findById(incomingMessageTo.getReplyMessageId());
        if (optionalAnnouncementPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent() && optionalRepliedMessage.isPresent()) {

            MessageType messageType;

            if (incomingMessageTo.isConsent()) {
                messageType = MessageType.CONSENT;
                AnnouncementPo announcementPo = optionalAnnouncementPo.get();
                deliveryRepository.save(new DeliveryPo(
                        optionalReceiverPo.get(),
                        announcementPo
                ));
            }
            else {
                messageType = MessageType.DISCORD;
            }

            MessagePo repliedMessage = optionalRepliedMessage.get();
            repliedMessage.setReplied(true);
            messageService.saveAllMessages(Arrays.asList(repliedMessage, new MessagePo(
                    incomingMessageTo.getMessage(),
                    optionalAnnouncementPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    messageType
            )));
        }
    }
}
