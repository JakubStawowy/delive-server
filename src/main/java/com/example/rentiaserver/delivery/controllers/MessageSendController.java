package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.dao.AnnouncementService;
import com.example.rentiaserver.data.dao.PackageRepository;
import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.delivery.dao.DeliveryRepository;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.enums.MessageType;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.po.MessagePo;
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

    private final MessageDao messageDao;
    private final UserRepository userRepository;
    private final AnnouncementService announcementService;
    private final DeliveryRepository deliveryRepository;
    private final PackageRepository packageRepository;

    @Autowired
    public MessageSendController(
            MessageDao messageDao,
            UserRepository userRepository,
            AnnouncementService announcementService,
            DeliveryRepository deliveryRepository,
            PackageRepository packageRepository
    ) {
        this.messageDao = messageDao;
        this.userRepository = userRepository;
        this.announcementService = announcementService;
        this.deliveryRepository = deliveryRepository;
        this.packageRepository = packageRepository;
    }

    @PostMapping("/register/normal")
    public void registerMessageNormal(@RequestBody IncomingMessageTo incomingMessageTo) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = userRepository.findById(incomingMessageTo.getSenderId());
        Optional<UserPo> optionalReceiverPo = userRepository.findById(incomingMessageTo.getReceiverId());
        if (optionalAnnouncementPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent()) {

            messageDao.save(new MessagePo(
                    incomingMessageTo.getMessage(),
                    optionalAnnouncementPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    MessageType.REQUEST
            ));
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
            messageDao.save(message);
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
        Optional<MessagePo> optionalRepliedMessage = messageDao.findById(incomingMessageTo.getReplyMessageId());
        if (optionalAnnouncementPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent() && optionalRepliedMessage.isPresent()) {

            MessageType messageType;

            if (incomingMessageTo.isConsent()) {
                messageType = MessageType.CONSENT;
                AnnouncementPo announcementPo = optionalAnnouncementPo.get();
                Set<AnnouncementPackagePo> packages = new HashSet<>();
                optionalRepliedMessage.get().getPackages().forEach(packagePo -> {
                    packages.add(new AnnouncementPackagePo(
                            packagePo.getPackageLength(),
                            packagePo.getPackageWidth(),
                            packagePo.getPackageHeight(),
                            announcementPo
                    ));
                });
                packageRepository.saveAll(packages);

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
            messageDao.save(repliedMessage);
            messageDao.save(new MessagePo(
                    incomingMessageTo.getMessage(),
                    optionalAnnouncementPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    messageType
            ));
        }
    }
}
