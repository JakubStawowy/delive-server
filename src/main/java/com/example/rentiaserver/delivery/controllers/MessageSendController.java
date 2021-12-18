package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.services.order.OrderService;
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

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = MessageSendController.BASE_ENDPOINT)
public class MessageSendController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/messages";

    private final OrderService orderService;
    private final MessageService messageService;
    private final DeliveryService deliveryService;

    @Autowired
    public MessageSendController(
            OrderService orderService,
            MessageService messageService,
            DeliveryService deliveryService
    ) {
        this.orderService = orderService;
        this.messageService = messageService;
        this.deliveryService = deliveryService;
    }

    @PostMapping("/register/normal")
    public boolean registerMessageNormal(@RequestBody IncomingMessageTo incomingMessageTo, HttpServletRequest request) {
        Optional<OrderPo> optionalOrderPo = orderService.getOrderById(incomingMessageTo.getOrderId());
        Optional<UserPo> optionalSenderPo = deliveryService.findUserById(JsonWebTokenHelper.getRequesterId(request));
        Optional<UserPo> optionalReceiverPo = deliveryService.findUserById(incomingMessageTo.getReceiverId());
        if (optionalOrderPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent()) {
            OrderPo orderPo = optionalOrderPo.get();
            UserPo senderPo = optionalSenderPo.get();
            Optional<DeliveryPo> optionalDeliveryPo = deliveryService.findDeliveryByOrderPoAndUserPo(orderPo, senderPo);
            if (optionalDeliveryPo.isPresent()) {
                return false;
            }

            MessagePo messagePo = new MessagePo(
                    incomingMessageTo.getMessage(),
                    optionalOrderPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    MessageType.REQUEST);
            messagePo.setVehicleRegistrationNumber(incomingMessageTo.getVehicleRegistrationNumber());
            messagePo.setPhoneNumber(incomingMessageTo.getPhoneNumber());
            messageService.saveMessage(messagePo);
            return true;
        }
        throw new IllegalArgumentException("Data not found");
    }

    @PostMapping("/reply")
    public void replyOnMessage(@RequestBody IncomingMessageTo incomingMessageTo, HttpServletRequest request) {
        Optional<OrderPo> optionalOrderPo = orderService.getOrderById(incomingMessageTo.getOrderId());
        Optional<UserPo> optionalSenderPo = deliveryService.findUserById(JsonWebTokenHelper.getRequesterId(request));
        Optional<UserPo> optionalReceiverPo = deliveryService.findUserById(incomingMessageTo.getReceiverId());
        Optional<MessagePo> optionalRepliedMessage = messageService.findById(incomingMessageTo.getReplyMessageId());
        if (optionalOrderPo.isPresent() && optionalSenderPo.isPresent() && optionalReceiverPo.isPresent() && optionalRepliedMessage.isPresent()) {

            MessageType messageType;
            String messageContent;

            if (incomingMessageTo.isConsent()) {
                messageType = MessageType.CONSENT;
                messageContent = "Your delivery request has been approved. You can now start your delivery!";
                OrderPo orderPo = optionalOrderPo.get();
                deliveryService.save(new DeliveryPo(
                        optionalReceiverPo.get(),
                        orderPo
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
                    optionalOrderPo.get(),
                    optionalSenderPo.get(),
                    optionalReceiverPo.get(),
                    messageType
            )));
        }
    }
}
