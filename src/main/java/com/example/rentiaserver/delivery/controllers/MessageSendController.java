package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.dao.AnnouncementService;
import com.example.rentiaserver.data.dao.PackageRepository;
import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.enums.AnnouncementType;
import com.example.rentiaserver.data.po.*;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = MessageSendController.BASE_ENDPOINT)
public class MessageSendController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/messages";

    private final UserRepository userRepository;
    private final AnnouncementService announcementService;
    private final MessageService messageService;
    private final DeliveryDao deliveryRepository;
    private final PackageRepository packageRepository;

    @Autowired
    public MessageSendController(
            UserRepository userRepository,
            AnnouncementService announcementService,
            MessageService messageService,
            DeliveryDao deliveryRepository,
            PackageRepository packageRepository
    ) {
        this.userRepository = userRepository;
        this.announcementService = announcementService;
        this.messageService = messageService;
        this.deliveryRepository = deliveryRepository;
        this.packageRepository = packageRepository;
    }

    @PostMapping("/register/normal")
    public void registerMessageNormal(@RequestBody IncomingMessageTo incomingMessageTo) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = userRepository.findById(incomingMessageTo.getSenderId());
        Optional<UserPo> optionalReceiverPo = userRepository.findById(incomingMessageTo.getReceiverId());
        if (optionalAnnouncementPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent()) {
            messageService.saveMessage(
                    incomingMessageTo.getMessage(),
                    optionalAnnouncementPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    MessageType.REQUEST);
        }
    }

    @PostMapping("/register/delivery")
    public void registerMessageDelivery(@RequestBody IncomingPackageMessageTo incomingMessageTo) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = userRepository.findById(incomingMessageTo.getSenderId());
        Optional<UserPo> optionalReceiverPo = userRepository.findById(incomingMessageTo.getReceiverId());
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
            packageRepository.saveAll(packages);
        }
    }

    @PostMapping("/reply")
    public void replyOnMessage(@RequestBody IncomingMessageTo incomingMessageTo) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = userRepository.findById(incomingMessageTo.getSenderId());
        Optional<UserPo> optionalReceiverPo = userRepository.findById(incomingMessageTo.getReceiverId());
        Optional<MessagePo> optionalRepliedMessage = messageService.findById(incomingMessageTo.getReplyMessageId());
        if (optionalAnnouncementPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent() && optionalRepliedMessage.isPresent()) {

            MessageType messageType;

            if (incomingMessageTo.isConsent()) {
                messageType = MessageType.CONSENT;
                AnnouncementPo announcementPo = optionalAnnouncementPo.get();
                if (AnnouncementType.NORMAL.equals(announcementPo.getAnnouncementType())) {
                    deliveryRepository.save(new DeliveryPo(
                            optionalReceiverPo.get(),
                            announcementPo
                    ));
                } else if (AnnouncementType.DELIVERY.equals(announcementPo.getAnnouncementType())) {
                    optionalRepliedMessage.ifPresent(repliedMessage -> associateSenderWithAnnouncement(repliedMessage, announcementPo));
                }
                else {
                    throw new IllegalArgumentException();
                }
            }
            else {
                messageType = MessageType.DISCORD;
            }

            MessagePo repliedMessage = optionalRepliedMessage.get();
            repliedMessage.setReplied(true);
            messageService.saveMessage(repliedMessage);
            messageService.saveMessage(
                    incomingMessageTo.getMessage(),
                    optionalAnnouncementPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    messageType
            );
        }
    }

    private void associateSenderWithAnnouncement(MessagePo messagePo, AnnouncementPo announcementPo) {
        Set<AnnouncementPackagePo> packages = new HashSet<>();
        messagePo.getPackages().forEach(packagePo ->
                packages.add(new AnnouncementPackagePo(
                        packagePo.getPackageLength(),
                        packagePo.getPackageWidth(),
                        packagePo.getPackageHeight(),
                        announcementPo
                )));
        packageRepository.saveAll(packages);
        UserPo sender = messagePo.getSender();
        sender.getRelatedAnnouncements().add(announcementPo);
        userRepository.save(sender);
    }
}
