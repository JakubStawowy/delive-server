package com.example.rentiaserver.geolocation.distance;

import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.helpers.OrderToCreatorHelper;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.data.to.AnnouncementTo;
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

    private final OrderService announcementService;
    private final IDistanceCalculator distanceCalculator;

    @Autowired
    public DistanceController(OrderService announcementService, IDistanceCalculator distanceCalculator) {
        this.announcementService = announcementService;
        this.distanceCalculator = distanceCalculator;
    }

    @GetMapping("/isInArea")
    public boolean isInArea(@RequestParam Long announcementId, @RequestParam double clientLatitude,
                            @RequestParam double clientLongitude) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(announcementId);
        LocationTo currentClientLocalization = LocationTo.Builder.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();
        return optionalAnnouncementPo
                .map(announcementPo -> mapToDistance(announcementPo, currentClientLocalization) < MAX_RADIUS)
                .orElse(false);
    }

    @GetMapping("/get")
    public double getDistance(@RequestParam Long announcementId, @RequestParam double clientLatitude,
                              @RequestParam double clientLongitude) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(announcementId);
        LocationTo currentClientLocalization = LocationTo.Builder.getBuilder()
                .setLatitude(clientLatitude)
                .setLongitude(clientLongitude)
                .build();
        return optionalAnnouncementPo
                .map(announcementPo -> mapToDistance(announcementPo, currentClientLocalization))
                .orElse((double) -1);
    }

    private double mapToDistance(AnnouncementPo announcementPo, LocationTo locationTo) {
        AnnouncementTo announcementTo = OrderToCreatorHelper.create(announcementPo);
        return distanceCalculator.getDistance(announcementTo.getDestinationTo(), locationTo);
    }
}
