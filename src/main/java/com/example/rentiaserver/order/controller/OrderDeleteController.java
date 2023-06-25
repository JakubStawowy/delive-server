package com.example.rentiaserver.order.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = OrderDeleteController.BASE_ENDPOINT)
public final class OrderDeleteController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/orders";
    private final OrderService orderService;

    @Autowired
    public OrderDeleteController(OrderService orderService) {
        this.orderService = orderService;
    }

    @DeleteMapping(value = "/delete")
    public void deleteOrder(@RequestParam Long orderId) throws EntityNotFoundException {
        orderService.archiveById(orderId);
    }
}
