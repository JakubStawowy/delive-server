package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.delivery.dao.MessageDao;
import com.example.rentiaserver.delivery.helpers.MessageToCreateHelper;
import com.example.rentiaserver.delivery.to.MessageTo;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = MessageLoadController.BASE_ENDPOINT)
public class MessageLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/messages";

    private final MessageDao messageDao;

    @Autowired
    public MessageLoadController(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @GetMapping("/sent")
    public List<MessageTo> loadMessagesSent(HttpServletRequest request) {
        return messageDao.findAllBySender(JsonWebTokenHelper.getRequesterId(request))
                .stream()
                .map(MessageToCreateHelper::create).collect(Collectors.toList());
    }

    @GetMapping("/received")
    public List<MessageTo> loadMessagesReceived(HttpServletRequest request) {
        return messageDao.findAllByReceiver(JsonWebTokenHelper.getRequesterId(request))
                .stream()
                .map(MessageToCreateHelper::create).collect(Collectors.toList());
    }
}
