package com.example.rentiaserver.delivery.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.delivery.service.DeliveryService;
import com.example.rentiaserver.delivery.model.to.DeliveryTo;
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
@RequestMapping(value = DeliveryLoadController.BASE_ENDPOINT)
public class DeliveryLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/delivery";

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryLoadController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/deliverer")
    public List<DeliveryTo> getDelivererDeliveries(HttpServletRequest request) {
        return deliveryService.getAllDelivererDeliveryTos(AuthenticationHelper.getUserId(request));
    }

    @GetMapping("/principal")
    public List<DeliveryTo> getPrincipalDeliveries(HttpServletRequest request) {
        return deliveryService.getAllPrincipalDeliveryTos(AuthenticationHelper.getUserId(request));
    }
}
