package com.example.rentiaserver.geolocation.distance;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.helpers.OrderToCreatorHelper;
import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.data.to.OrderTo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = DistanceController.BASE_ENDPOINT)
public class DistanceController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/distance";
    private static final double MAX_RADIUS = 1;

    private final OrderService orderService;
    private final IDistanceCalculator distanceCalculator;

    @Autowired
    public DistanceController(OrderService orderService, IDistanceCalculator distanceCalculator) {
        this.orderService = orderService;
        this.distanceCalculator = distanceCalculator;
    }

    @GetMapping("/isInArea")
    public boolean isInArea(@RequestParam Long orderId, @RequestParam double clientLatitude,
                            @RequestParam double clientLongitude) {
        Optional<OrderPo> optionalOrderPo = orderService.getOrderById(orderId);
        LocationTo currentClientLocalization = LocationTo.Builder.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();
        return optionalOrderPo
                .map(orderPo -> mapToDistance(orderPo, currentClientLocalization) < MAX_RADIUS)
                .orElse(false);
    }

    @GetMapping("/get")
    public double getDistance(@RequestParam Long orderId, @RequestParam double clientLatitude,
                              @RequestParam double clientLongitude) {
        Optional<OrderPo> optionalOrderPo = orderService.getOrderById(orderId);
        LocationTo currentClientLocalization = LocationTo.Builder.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();
        return optionalOrderPo
                .map(orderPo -> mapToDistance(orderPo, currentClientLocalization))
                .orElse((double) -1);
    }

    private double mapToDistance(OrderPo orderPo, LocationTo locationTo) {
        OrderTo orderTo = OrderToCreatorHelper.create(orderPo);
        return distanceCalculator.getDistance(orderTo.getDestinationTo(), locationTo);
    }
}
