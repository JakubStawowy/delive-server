package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.service.OrderService;
import com.example.rentiaserver.user.model.po.UserPo;
import com.example.rentiaserver.base.model.to.ResponseTo;
import com.example.rentiaserver.user.service.UserService;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.api.MessageType;
import com.example.rentiaserver.delivery.model.po.DeliveryPo;
import com.example.rentiaserver.delivery.model.po.MessagePo;
import com.example.rentiaserver.delivery.service.DeliveryService;
import com.example.rentiaserver.delivery.service.MessageService;
import com.example.rentiaserver.delivery.model.to.IncomingMessageTo;
import com.example.rentiaserver.delivery.model.to.MessageTo;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = MessageSendController.BASE_ENDPOINT)
public class MessageSendController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/messages";

    private final OrderService orderService;

    private final MessageService messageService;

    private final DeliveryService deliveryService;

    private final UserService userService;


    @Autowired
    public MessageSendController(
            OrderService orderService,
            MessageService messageService,
            DeliveryService deliveryService,
            UserService userService
    ) {
        this.orderService = orderService;
        this.messageService = messageService;
        this.deliveryService = deliveryService;
        this.userService = userService;
    }

    // TODO to refactor
    @PostMapping("/register/normal")
    public ResponseTo registerMessageNormal(@RequestBody MessageTo message, HttpServletRequest request)
            throws EntityNotFoundException {

        OrderPo order = orderService.getOrderById(message.getOrderId());
        UserPo sender = userService.getUserPoById(AuthenticationHelper.getUserId(request));
        UserPo receiver = userService.getUserPoById(message.getReceiverId());

        Optional<DeliveryPo> optionalDeliveryPo = deliveryService.findDeliveryByOrderPoAndUserPo(order, sender);
        if (optionalDeliveryPo.isPresent()) {
            return ResponseTo.builder()
                    .operationSuccess(false)
                    .status(HttpStatus.NOT_FOUND)
                    .message("Delivery not found for order: " + order.getId() + " and user: " + sender.getId())
                    .build();
        }

        MessagePo messagePo = new MessagePo(
                message.getMessage(),
                order,
                sender,
                receiver,
                MessageType.REQUEST);

        messagePo.setVehicleRegistrationNumber(message.getVehicleRegistrationNumber());
        messagePo.setPhoneNumber(message.getPhoneNumber());

        messageService.saveMessage(messagePo);

        return ResponseTo.builder()
                .operationSuccess(true)
                .status(HttpStatus.OK)
                .build();
    }

    // TODO to refactor
    @PostMapping("/reply")
    public void replyOnMessage(@RequestBody IncomingMessageTo incomingMessageTo, HttpServletRequest request)
            throws EntityNotFoundException {

        OrderPo order = orderService.getOrderById(incomingMessageTo.getOrderId());
        UserPo sender = userService.getUserPoById(AuthenticationHelper.getUserId(request));
        UserPo receiver = userService.getUserPoById(incomingMessageTo.getReceiverId());

        MessagePo repliedMessage = messageService.getPoById(incomingMessageTo.getReplyMessageId());

        boolean isIncomingMessageConsent = incomingMessageTo.isConsent();


        MessageType messageType = isIncomingMessageConsent ? MessageType.CONSENT : MessageType.DISCORD;
        String messageContent = isIncomingMessageConsent
                ? "Your delivery request has been approved. You can now start your delivery!"
                : "Your delivery request has been rejected";

        if (isIncomingMessageConsent) {
            DeliveryPo delivery = new DeliveryPo(receiver, order);
            deliveryService.save(delivery);
        }

        MessagePo reply = new MessagePo(
                messageContent,
                order,
                sender,
                receiver,
                messageType);

        messageService.saveMessage(reply);

        repliedMessage.setReplied(true);
        messageService.saveMessage(repliedMessage);
    }
}
