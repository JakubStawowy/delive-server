package com.example.rentiaserver.maps.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.helpers.AnnouncementToCreatorHelper;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.services.AnnouncementService;
import com.example.rentiaserver.data.to.AnnouncementTo;
import com.example.rentiaserver.maps.services.DistanceService;
import com.example.rentiaserver.maps.to.LocationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = DistanceController.BASE_ENDPOINT)
public class DistanceController {
    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/distance";

    private final AnnouncementService announcementService;
    private final DistanceService distanceService;

    @Autowired
    public DistanceController(AnnouncementService announcementService, DistanceService distanceService) {
        this.announcementService = announcementService;
        this.distanceService = distanceService;
    }

    @GetMapping("/isInArea")
    public boolean isInArea(@RequestParam Long announcementId, @RequestParam double clientLatitude, @RequestParam double clientLongitude) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(announcementId);
        LocationTo currentClientLocalization = new LocationTo(null, null, clientLatitude, clientLongitude, null, null, null);
        if (optionalAnnouncementPo.isPresent()) {
            AnnouncementTo announcementTo = AnnouncementToCreatorHelper.create(optionalAnnouncementPo.get());
            double RADIUS = 2;
            return distanceService.getDistance(announcementTo.getDestinationTo(), currentClientLocalization) <= RADIUS;
        }
        return false;
    }

    @GetMapping("/get")
    public double getDistance(@RequestParam Long announcementId, @RequestParam double clientLatitude, @RequestParam double clientLongitude) {
        Optional<AnnouncementPo> optionalAnnouncementPo = announcementService.getAnnouncementById(announcementId);
        LocationTo currentClientLocalization = new LocationTo(null, null, clientLatitude, clientLongitude, null, null, null);
        if (optionalAnnouncementPo.isPresent()) {
            AnnouncementTo announcementTo = AnnouncementToCreatorHelper.create(optionalAnnouncementPo.get());
            return distanceService.getDistance(announcementTo.getDestinationTo(), currentClientLocalization);
        }
        return -1;
    }

}
