package com.example.rentiaserver.delivery.services;

import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.UserPo;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.enums.MessageType;
import com.example.rentiaserver.delivery.po.MessagePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    private final MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void saveMessage(MessagePo messagePo) {
        messageDao.save(messagePo);
    }

    public void saveAllMessages(Iterable<MessagePo> messagePos) {
        messageDao.saveAll(messagePos);
    }

    public Optional<MessagePo> findById(Long id) {
        return messageDao.findById(id);
    }
}
