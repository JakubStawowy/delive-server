package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.delivery.api.IChangeDeliveryStateService;
import com.example.rentiaserver.delivery.enums.DeliveryState;
import com.example.rentiaserver.delivery.services.DeliveryService;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.security.to.ResponseTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = ChangeDeliveryStateController.BASE_ENDPOINT)
public class ChangeDeliveryStateController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/delivery";

    private final DeliveryService deliveryService;
    private final IChangeDeliveryStateService changeDeliveryStateService;

    @Autowired
    public ChangeDeliveryStateController(DeliveryService deliveryService, IChangeDeliveryStateService changeDeliveryStateService) {
        this.deliveryService = deliveryService;
        this.changeDeliveryStateService = changeDeliveryStateService;
    }

    @PutMapping("/pick")
    public void pickPackage(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(deliveryPo -> changeDeliveryStateService.changeDeliveryState(deliveryPo, DeliveryState.TO_START));
    }

    @PutMapping("/start")
    public void startDelivery(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(changeDeliveryStateService::startDelivery);
    }

    @PutMapping("/finish")
    public ResponseTo finishDelivery(@RequestParam Long deliveryId, @RequestParam double clientLatitude, @RequestParam double clientLongitude) {

        LocationTo clientLocation = LocationTo.Builder.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();
        return deliveryService.findDeliveryById(deliveryId)
                .map(deliveryPo -> changeDeliveryStateService.finishDelivery(deliveryPo, clientLocation))
                .orElse(new ResponseTo(false, "Delivery not found", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/accept")
    public void acceptDelivery(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(changeDeliveryStateService::acceptDeliveryFinishRequest);
    }

    @PutMapping("/discard")
    public void discardDelivery(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(deliveryPo -> changeDeliveryStateService.changeDeliveryState(deliveryPo, DeliveryState.STARTED));
    }

    @PutMapping("/close")
    public void closeDelivery(@RequestParam Long deliveryId) {
        deliveryService.findDeliveryById(deliveryId).ifPresent(changeDeliveryStateService::closeDelivery);
    }
}
