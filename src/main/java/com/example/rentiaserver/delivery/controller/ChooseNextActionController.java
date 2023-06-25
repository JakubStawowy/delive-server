package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.delivery.api.DeliveryState;
import com.example.rentiaserver.delivery.service.DeliveryService;
import com.example.rentiaserver.delivery.service.NextActionBuilder;
import com.example.rentiaserver.security.helpers.AuthenticationHelper;
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

    private final DeliveryService deliveryService;

    @Autowired
    public ChooseNextActionController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/actionName")
    public Set<NextActionBuilder.ActionPack> getNextActionNames(
            @RequestParam DeliveryState deliveryState,
            @RequestParam Long orderAuthorId,
            @RequestParam Long delivererId,
            HttpServletRequest request) {

        Long loggedUserId = AuthenticationHelper.getUserId(request);

        return deliveryService.getNextActions(
                deliveryState,
                orderAuthorId,
                delivererId,
                loggedUserId);
    }
}
