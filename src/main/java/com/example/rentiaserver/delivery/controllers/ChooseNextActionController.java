package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.delivery.api.IChooseNextActionService;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.security.helpers.JsonWebTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = ChooseNextActionController.BASE_ENDPOINT)
public class ChooseNextActionController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/delivery";

    private final IChooseNextActionService nextActionService;

    @Autowired
    public ChooseNextActionController(IChooseNextActionService nextActionService) {
        this.nextActionService = nextActionService;
    }

    @GetMapping("/actionName")
    public Set<IChooseNextActionService.ActionPack> getNextActionNames(@RequestParam DeliveryState deliveryState, @RequestParam Long orderAuthorId,
                                                                       @RequestParam Long delivererId, HttpServletRequest request) {
        final Long loggedUserId = JsonWebTokenHelper.getRequesterId(request);
        final boolean isUserPrincipal = orderAuthorId.compareTo(loggedUserId) == 0;
        final boolean isUserDeliverer = delivererId.compareTo(loggedUserId) == 0;
        return nextActionService.getNextActionNames(deliveryState, isUserPrincipal, isUserDeliverer);
    }
}
