package com.example.rentiaserver.order.controller;

import com.example.rentiaserver.base.ApplicationConstants;
import com.example.rentiaserver.base.exception.EntityNotFoundException;
import com.example.rentiaserver.order.model.to.OrderTo;
import com.example.rentiaserver.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = OrderLoadController.BASE_ENDPOINT)
public final class OrderLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/orders";

    private final OrderService orderService;

    @Autowired
    public OrderLoadController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<OrderTo> getAllOrders() {
        return orderService.getAllOrderTos();
    }

    @GetMapping("/order")
    public ResponseEntity<OrderTo> getOrderById(@RequestParam Long orderId) throws EntityNotFoundException {
        OrderTo order = orderService.getOrderToById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/filtered")
    public List<OrderTo> getFilteredOrders(@RequestParam String initialAddress,
                                           @RequestParam String finalAddress,
                                           @RequestParam String minimalSalary,
                                           @RequestParam String maxWeight,
                                           @RequestParam Boolean requireTransportWithClient,
                                           @RequestParam Boolean sortBySalary,
                                           @RequestParam Boolean sortByWeight) {

        return orderService.queryOrderTos(
                initialAddress,
                finalAddress,
                minimalSalary,
                maxWeight,
                requireTransportWithClient,
                sortBySalary,
                sortByWeight);
    }
}
