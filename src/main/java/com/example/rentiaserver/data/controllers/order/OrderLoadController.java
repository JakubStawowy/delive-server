package com.example.rentiaserver.data.controllers.order;

import com.example.rentiaserver.data.helpers.PackagesWeightCounterHelper;
import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.data.to.*;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.helpers.OrderToCreatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
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
        List<OrderTo> orderTos = new LinkedList<>();
        orderService.getAllOrders()
                .forEach(orderPo -> orderTos.add(OrderToCreatorHelper.create(orderPo)));
        return orderTos;
    }

    @GetMapping("/order")
    public OrderTo getOrderById(@RequestParam Long orderId) {
        return orderService.getOrderById(orderId).map(OrderToCreatorHelper::create).orElse(null);
    }

    @GetMapping("/filtered")
    public List<OrderTo> getFilteredOrders(@RequestParam String initialAddress, @RequestParam String finalAddress,
                                           @RequestParam String minimalSalary, @RequestParam String maxWeight,
                                           @RequestParam String requireTransportWithClient, @RequestParam boolean sortBySalary,
                                           @RequestParam boolean sortByWeight) {
        List<OrderTo> orderTos = orderService
                .findOrdersByAddresses(initialAddress, finalAddress, minimalSalary, requireTransportWithClient, sortBySalary)
                .stream().map(OrderToCreatorHelper::create).collect(Collectors.toList());
        List<OrderTo> result;

        if (maxWeight != null && !maxWeight.isEmpty()) {
            result = new ArrayList<>();
            for (OrderTo orderTo : orderTos) {
                BigDecimal weight = PackagesWeightCounterHelper.sumPackagesWeights(orderTo.getPackages());
                if (weight.compareTo(new BigDecimal(maxWeight)) < 0) {
                    result.add(orderTo);
                }
            }
        } else {
            result = new ArrayList<>(orderTos);
        }

        if (sortByWeight) {
            result.sort(Comparator.comparing(orderTo -> new BigDecimal(orderTo.getWeight())));
        }

        return result;
    }

}
