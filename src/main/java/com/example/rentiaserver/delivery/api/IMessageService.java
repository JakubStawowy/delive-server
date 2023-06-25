package com.example.rentiaserver.delivery.api;

import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.model.po.MessagePo;
import com.example.rentiaserver.delivery.model.to.MessageTo;

import java.util.List;

public interface IMessageService {

    void saveMessage(MessagePo messagePo);
    void saveAllMessages(Iterable<MessagePo> messagePos);

    MessageTo getById(Long id) throws EntityNotFoundException;

    MessagePo getPoById(Long id) throws EntityNotFoundException;

    MessagePo getPoByIdOrNull(Long id);

    List<MessageTo> getUserSentMessages(Long userId);

    List<MessageTo> getUserReceivedMessages(Long userId);
}
