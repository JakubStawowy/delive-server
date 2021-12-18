package com.example.rentiaserver.data.controllers.order;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = OrderRemoveController.BASE_ENDPOINT)
public final class OrderRemoveController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/orders";
    private final OrderService orderService;

    @Autowired
    public OrderRemoveController(OrderService orderService) {
        this.orderService = orderService;
    }

    @DeleteMapping(value = "/delete")
    public void removeOrder(@RequestParam Long orderId) {
        orderService.deleteById(orderId);
    }
}
