package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.delivery.service.DeliveryService;
import com.example.rentiaserver.geolocation.model.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = ChangeDeliveryStateController.BASE_ENDPOINT)
public class ChangeDeliveryStateController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/delivery";

    private final DeliveryService deliveryService;

    @Autowired
    public ChangeDeliveryStateController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PutMapping("/pick")
    public void pickPackage(@RequestParam Long deliveryId) throws EntityNotFoundException {
        deliveryService.pickPackage(deliveryId);
    }

    @PutMapping("/start")
    public void startDelivery(@RequestParam Long deliveryId) throws EntityNotFoundException {
        deliveryService.startDelivery(deliveryId);
    }

    @PutMapping("/finish")
    public void finishDelivery(
            @RequestParam Long deliveryId,
            @RequestParam double clientLatitude,
            @RequestParam double clientLongitude) throws EntityNotFoundException {

        LocationTo clientLocation = LocationTo.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();

        deliveryService.finishDelivery(deliveryId, clientLocation);
    }

    @PutMapping("/accept")
    public void acceptDelivery(@RequestParam Long deliveryId) throws EntityNotFoundException {
        deliveryService.acceptDelivery(deliveryId);
    }

    @PutMapping("/discard")
    public void discardDelivery(@RequestParam Long deliveryId) throws EntityNotFoundException {
        deliveryService.discardDelivery(deliveryId);
    }

    @PutMapping("/close")
    public void closeDelivery(@RequestParam Long deliveryId) throws EntityNotFoundException {
        deliveryService.closeDelivery(deliveryId);
    }
}
