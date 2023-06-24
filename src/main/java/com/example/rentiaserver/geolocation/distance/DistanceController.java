package com.example.rentiaserver.geolocation.distance;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.helpers.OrderMapper;
import com.example.rentiaserver.data.po.OrderPo;
import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.data.to.OrderTo;
import com.example.rentiaserver.geolocation.to.LocationTo;
import com.example.rentiaserver.geolocation.tool.HaversineDistanceCalculator;
import com.example.rentiaserver.geolocation.util.GeoConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = DistanceController.BASE_ENDPOINT)
public class DistanceController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/distance";

    private final OrderService orderService;


    @Autowired
    public DistanceController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/isInArea")
    public boolean isInArea(@RequestParam Long orderId,
                            @RequestParam double clientLatitude,
                            @RequestParam double clientLongitude) {

        Optional<OrderPo> optionalOrderPo = orderService.getOrderById(orderId);
        LocationTo currentClientLocalization = LocationTo.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();

        return optionalOrderPo.map(orderPo -> mapToDistance(orderPo, currentClientLocalization) < GeoConstants.MAX_RADIUS)
                .orElse(false);
    }

    @GetMapping("/get")
    public double getDistance(@RequestParam Long orderId,
                              @RequestParam double clientLatitude,
                              @RequestParam double clientLongitude) {

        Optional<OrderPo> optionalOrderPo = orderService.getOrderById(orderId);
        LocationTo currentClientLocalization = LocationTo.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();

        return optionalOrderPo
                .map(orderPo -> mapToDistance(orderPo, currentClientLocalization))
                .orElse((double) -1);
    }

    private double mapToDistance(OrderPo orderPo, LocationTo locationTo) {
        OrderTo orderTo = OrderMapper.mapPersistentObjectToTransfer(orderPo);
        return HaversineDistanceCalculator.getDistance(orderTo.getDestinationTo(), locationTo);
    }
}
