package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.delivery.service.MessageService;
import com.example.rentiaserver.delivery.model.to.MessageTo;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = MessageLoadController.BASE_ENDPOINT)
public class MessageLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/messages";

    private final MessageService messageService;

    @Autowired
    public MessageLoadController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/sent")
    public List<MessageTo> getSentMessages(HttpServletRequest request) {
        Long userId = AuthenticationHelper.getUserId(request);
        return messageService.getUserSentMessages(userId);
    }

    @GetMapping("/received")
    public List<MessageTo> getReceivedMessages(HttpServletRequest request) {
        Long userId = AuthenticationHelper.getUserId(request);
        return messageService.getUserReceivedMessages(userId);
    }
}
