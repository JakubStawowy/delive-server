package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.data.helpers.PackagesWeightCounterHelper;
import com.example.rentiaserver.data.services.announcement.AnnouncementService;
import com.example.rentiaserver.data.to.*;
import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.helpers.AnnouncementToCreatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = AnnouncementLoadController.BASE_ENDPOINT)
public final class AnnouncementLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/announcements";

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementLoadController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/all")
    public List<AnnouncementTo> getAllAnnouncements() {
        List<AnnouncementTo> announcementTransferObjects = new LinkedList<>();
        announcementService.getAllAnnouncements()
                .forEach(announcement -> announcementTransferObjects.add(AnnouncementToCreatorHelper.create(announcement)));
        return announcementTransferObjects;
    }

    @GetMapping("/announcement")
    public AnnouncementTo getAnnouncementById(@RequestParam Long announcementId) {
        return announcementService.getAnnouncementById(announcementId).map(AnnouncementToCreatorHelper::create).orElse(null);
    }

    @GetMapping("/filtered")
    public List<AnnouncementTo> getFilteredAnnouncements(@RequestParam String initialAddress, @RequestParam String finalAddress,
                                                  @RequestParam String minimalSalary, @RequestParam String maxWeight,
                                                         @RequestParam String requireTransportWithClient) {
        List<AnnouncementTo> announcementTos = announcementService
                .findAnnouncementsByAddresses(initialAddress, finalAddress, minimalSalary, requireTransportWithClient)
                .stream().map(AnnouncementToCreatorHelper::create).collect(Collectors.toList());
        if (maxWeight != null && !maxWeight.isEmpty()) {
            return announcementTos.stream().filter(announcementTo ->
                    PackagesWeightCounterHelper.sumPackagesWeights(announcementTo.getPackages()).compareTo(new BigDecimal(maxWeight)) < 0)
                    .collect(Collectors.toList());
        }
        return announcementTos;
    }

}
