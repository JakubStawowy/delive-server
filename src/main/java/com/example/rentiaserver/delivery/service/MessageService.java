package com.example.rentiaserver.delivery.service;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.model.mapper.MessageMapper;
import com.example.rentiaserver.delivery.repository.MessageRepository;
import com.example.rentiaserver.delivery.model.mapper.DeliveryMapper;
import com.example.rentiaserver.delivery.model.po.MessagePo;
import com.example.rentiaserver.delivery.api.IMessageService;
import com.example.rentiaserver.delivery.model.to.MessageTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {

    private final MessageRepository messageDao;

    @Autowired
    public MessageService(MessageRepository messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public void saveMessage(MessagePo messagePo) {
        messageDao.save(messagePo);
    }

    @Override
    public void saveAllMessages(Iterable<MessagePo> messagePos) {
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
}
