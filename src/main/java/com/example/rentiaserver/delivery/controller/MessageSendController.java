package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.service.MessageService;
import com.example.rentiaserver.delivery.model.to.IncomingMessageTo;
import com.example.rentiaserver.delivery.model.to.MessageTo;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = MessageSendController.BASE_ENDPOINT)
public class MessageSendController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/messages";

    private final MessageService messageService;

    @Autowired
    public MessageSendController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/register/normal")
    public void registerMessageNormal(@RequestBody MessageTo message, HttpServletRequest request)
            throws EntityNotFoundException {

        // TODO to remove
        if (message.getSenderId() == null) {
            message.setSenderId(AuthenticationHelper.getUserId(request));
        }

        messageService.register(message);
    }

    @PostMapping("/reply")
    public void replyOnMessage(@RequestBody IncomingMessageTo incomingMessageTo, HttpServletRequest request)
            throws EntityNotFoundException {

        // TODO to remove
        if (incomingMessageTo.getSenderId() == null) {
            incomingMessageTo.setSenderId(AuthenticationHelper.getUserId(request));
        }

        messageService.reply(incomingMessageTo);
    }
}
