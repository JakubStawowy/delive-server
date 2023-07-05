package com.example.rentiaserver.delivery.service;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.api.MessageType;
import com.example.rentiaserver.delivery.model.mapper.MessageMapper;
import com.example.rentiaserver.delivery.model.po.DeliveryPo;
import com.example.rentiaserver.delivery.model.to.IncomingMessageTo;
import com.example.rentiaserver.delivery.repository.MessageRepository;
import com.example.rentiaserver.delivery.model.po.MessagePo;
import com.example.rentiaserver.delivery.api.IMessageService;
import com.example.rentiaserver.delivery.model.to.MessageTo;
import com.example.rentiaserver.order.model.po.OrderPo;
import com.example.rentiaserver.order.service.OrderService;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import com.example.rentiaserver.user.api.IUserService;
import com.example.rentiaserver.user.model.po.UserPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {

    private final MessageRepository messageDao;

    private final IUserService userService;

    private final OrderService orderService;

    /**
     * TODO to remove
     * */
    @Deprecated
    private DeliveryService deliveryService;

    @Autowired
    public MessageService(
            MessageRepository messageDao,
            IUserService userService,
            OrderService orderService) {
        this.messageDao = messageDao;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Override
    public void save(MessagePo messagePo) {
        messageDao.save(messagePo);
    }

    @Override
    public void save(List<MessagePo> messagePos) {
        messageDao.saveAll(messagePos);
    }

    @Override
    public MessageTo getById(Long id) throws EntityNotFoundException {
        return messageDao.findById(id)
                .map(MessageMapper::mapMessagePoToOutgoingMessageTo)
                .orElseThrow(() -> new EntityNotFoundException(MessageTo.class, id));
    }

    @Override
    public MessagePo getPoById(Long id) throws EntityNotFoundException {
        return Optional.ofNullable(getPoByIdOrNull(id))
                .orElseThrow(() -> new EntityNotFoundException(MessagePo.class, id));
    }

    @Override
    public MessagePo getPoByIdOrNull(Long id) {
        return messageDao.findById(id)
                .orElse(null);
    }

    @Override
    public List<MessageTo> getUserSentMessages(Long userId) {
        return messageDao.findAllBySender(userId)
                .stream()
                .map(MessageMapper::mapMessagePoToOutgoingMessageTo)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageTo> getUserReceivedMessages(Long userId) {
        return messageDao.findAllByReceiver(userId)
                .stream()
                .map(MessageMapper::mapMessagePoToOutgoingMessageTo)
                .collect(Collectors.toList());
    }

    @Override
    public void register(MessageTo message) throws EntityNotFoundException {

        OrderPo order = orderService.getOrderById(message.getOrderId());
        UserPo sender = userService.getUserPoById(message.getSenderId());
        UserPo receiver = userService.getUserPoById(message.getReceiverId());

        MessagePo messagePo = new MessagePo(
                message.getMessage(),
                order,
                sender,
                receiver,
                MessageType.REQUEST);

        messagePo.setVehicleRegistrationNumber(message.getVehicleRegistrationNumber());
        messagePo.setPhoneNumber(message.getPhoneNumber());

        save(messagePo);
    }

    public void reply(IncomingMessageTo incomingMessageTo) throws EntityNotFoundException {

        OrderPo order = orderService.getOrderById(incomingMessageTo.getOrderId());
        UserPo sender = userService.getUserPoById(incomingMessageTo.getSenderId());
        UserPo receiver = userService.getUserPoById(incomingMessageTo.getReceiverId());

        MessagePo repliedMessage = getPoById(incomingMessageTo.getReplyMessageId());

        boolean isIncomingMessageConsent = incomingMessageTo.isConsent();


        MessageType messageType = isIncomingMessageConsent ? MessageType.CONSENT : MessageType.DISCORD;

        // TODO load message from messageSource
        String messageContent = isIncomingMessageConsent
                ? "Your delivery request has been approved. You can now start your delivery!"
                : "Your delivery request has been rejected";

        // TODO to remove
        //  better idea would be to register delivery by API and then register a message.
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

        save(reply);

        repliedMessage.setReplied(true);
        save(repliedMessage);
    }
}
