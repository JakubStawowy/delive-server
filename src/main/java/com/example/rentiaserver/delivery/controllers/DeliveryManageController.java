package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.delivery.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = DeliveryManageController.BASE_ENDPOINT)
public class DeliveryManageController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/delivery";

    private final DeliveryService deliveryService;
    private final MessageService messageService;

    @Autowired
    public DeliveryManageController(DeliveryService deliveryService, MessageService messageService) {
        this.deliveryService = deliveryService;
        this.messageService = messageService;
    }

    @PutMapping("/change")
    public void changeDeliveryState(@RequestParam String actionName, @RequestParam Long deliveryId) {
        DeliveryState.getNextStateAfterAction(actionName).getAction()
                .startAction(deliveryService, messageService, deliveryId);
    }
}