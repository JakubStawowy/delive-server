package com.example.rentiaserver.data.controllers.order;

import com.example.rentiaserver.data.helpers.PackagesWeightCounterHelper;
import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.helpers.OrderMapper;
import com.example.rentiaserver.data.to.OrderTo;
import com.example.rentiaserver.data.util.EntityNotFoundException;
import com.example.rentiaserver.data.util.EntityNotFoundExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        return orderService.getAllOrders()
                .stream()
                .map(OrderMapper::mapPersistentObjectToTransfer)
                .collect(Collectors.toList());
    }

    @GetMapping("/order")
    public ResponseEntity<OrderTo> getOrderById(@RequestParam Long orderId) {
        try {
            OrderTo order = orderService.getOrderById(orderId)
                    .map(OrderMapper::mapPersistentObjectToTransfer)
                    .orElseThrow(() -> createOrderNotFound(orderId));

            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (EntityNotFoundException ex) {
            return EntityNotFoundExceptionHandler.handle(ex);
        }
    }

    @GetMapping("/filtered")
    public List<OrderTo> getFilteredOrders(@RequestParam String initialAddress,
                                           @RequestParam String finalAddress,
                                           @RequestParam String minimalSalary,
                                           @RequestParam String maxWeight,
                                           @RequestParam Boolean requireTransportWithClient,
                                           @RequestParam Boolean sortBySalary,
                                           @RequestParam Boolean sortByWeight) {

        List<OrderPo> orders = orderService.findOrdersByAddresses(initialAddress,
                finalAddress,
                minimalSalary,
                requireTransportWithClient,
                sortBySalary);

        List<OrderTo> orderTos = orders.stream()
                .map(OrderMapper::mapPersistentObjectToTransfer)
                .filter(order -> isBelowMaxWeight(order, maxWeight))
                .collect(Collectors.toList());

        // TODO put this in Criteria query
        if (Boolean.TRUE.equals(sortByWeight)) {
            orderTos.sort(Comparator.comparing(orderTo -> new BigDecimal(orderTo.getWeight())));
        }

        return orderTos;
    }

    private boolean isBelowMaxWeight(OrderTo order, String maxWeight) {
        return !(maxWeight != null && !maxWeight.isEmpty())
                || PackagesWeightCounterHelper.sumPackagesWeights(order.getPackages())
                .compareTo(new BigDecimal(maxWeight)) < 0;
    }

    private EntityNotFoundException createOrderNotFound(long orderId) {
        return new EntityNotFoundException(OrderPo.class, orderId);
    }
}
