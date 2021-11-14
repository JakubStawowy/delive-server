package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.services.announcement.AnnouncementService;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.delivery.enums.MessageType;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.po.MessagePo;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.delivery.services.MessageService;
import com.example.rentiaserver.delivery.to.IncomingMessageTo;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = MessageSendController.BASE_ENDPOINT)
public class MessageSendController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/messages";

    private final AnnouncementService announcementService;
    private final MessageService messageService;
    private final DeliveryService deliveryService;

    @Autowired
    public MessageSendController(
            AnnouncementService announcementService,
            MessageService messageService,
            DeliveryService deliveryService
    ) {
        this.announcementService = announcementService;
        this.messageService = messageService;
        this.deliveryService = deliveryService;
    }

    @PostMapping("/register/normal")
    public boolean registerMessageNormal(@RequestBody IncomingMessageTo incomingMessageTo, HttpServletRequest request) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = deliveryService.findUserById(JsonWebTokenHelper.getRequesterId(request));
        Optional<UserPo> optionalReceiverPo = deliveryService.findUserById(incomingMessageTo.getReceiverId());
        if (optionalAnnouncementPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent()) {
            AnnouncementPo announcementPo = optionalAnnouncementPo.get();
            UserPo senderPo = optionalSenderPo.get();
            Optional<DeliveryPo> optionalDeliveryPo = deliveryService.findDeliveryByAnnouncementPoAndUserPo(announcementPo, senderPo);
            if (optionalDeliveryPo.isPresent()) {
                return false;
            }

            messageService.saveMessage(
                    new MessagePo(
                            incomingMessageTo.getMessage(),
                            optionalAnnouncementPo.get(),
                            optionalSenderPo.get(),
                            optionalReceiverPo.get(),
                            MessageType.REQUEST));
            return true;
        }
        throw new IllegalArgumentException("Data not found");
    }

    @PostMapping("/reply")
    public void replyOnMessage(@RequestBody IncomingMessageTo incomingMessageTo, HttpServletRequest request) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(incomingMessageTo.getAnnouncementId());
        Optional<UserPo> optionalSenderPo = deliveryService.findUserById(JsonWebTokenHelper.getRequesterId(request));
        Optional<UserPo> optionalReceiverPo = deliveryService.findUserById(incomingMessageTo.getReceiverId());
        Optional<MessagePo> optionalRepliedMessage = messageService.findById(incomingMessageTo.getReplyMessageId());
        if (optionalAnnouncementPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent() && optionalRepliedMessage.isPresent()) {

            MessageType messageType;
            String messageContent;

            if (incomingMessageTo.isConsent()) {
                messageType = MessageType.CONSENT;
                messageContent = "Your delivery request has been approved. You can now start your delivery!";
                AnnouncementPo announcementPo = optionalAnnouncementPo.get();
                deliveryService.save(new DeliveryPo(
                        optionalReceiverPo.get(),
                        announcementPo
                ));
            }
            else {
                messageType = MessageType.DISCORD;
                messageContent = "Your delivery request has been rejected";
            }

            MessagePo repliedMessage = optionalRepliedMessage.get();
            repliedMessage.setReplied(true);
            messageService.saveAllMessages(Arrays.asList(repliedMessage, new MessagePo(
                    messageContent,
                    optionalAnnouncementPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    messageType
            )));
        }
    }
}
