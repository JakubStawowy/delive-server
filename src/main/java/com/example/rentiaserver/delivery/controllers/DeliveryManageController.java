package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.data.helpers.AnnouncementToCreatorHelper;
import com.example.rentiaserver.delivery.dao.DeliveryDao;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.delivery.po.DeliveryPo;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.delivery.services.MessageService;
import com.example.rentiaserver.maps.services.DistanceService;
import com.example.rentiaserver.maps.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PutMapping("/finish")
    public void tryFinishDelivery(@RequestParam Long deliveryId, @RequestParam double clientLatitude, @RequestParam double clientLongitude) {
        Optional<DeliveryPo> optionalDeliveryPo = deliveryService.findDeliveryById(deliveryId);
        optionalDeliveryPo.ifPresent(deliveryPo -> {
            LocationTo targetLocation = AnnouncementToCreatorHelper.create(deliveryPo.getAnnouncementPo()).getDestinationTo();
            LocationTo clientLocation = new LocationTo(null, null, clientLatitude, clientLongitude, null, null, null);
            final double radius = 2;
            if (deliveryService.getDistance(targetLocation, clientLocation) <= radius) {
                System.out.println("[Finish] Odleglosc: " + deliveryService.getDistance(targetLocation, clientLocation));
                DeliveryState.FINISHED.getAction().startAction(deliveryService, messageService, deliveryId);
            }
            else {
                System.out.println("[RESTART] Odleglosc: " + deliveryService.getDistance(targetLocation, clientLocation));
                DeliveryState.RESTARTED.getAction().startAction(deliveryService, messageService, deliveryId);
            }
        });
    }
}