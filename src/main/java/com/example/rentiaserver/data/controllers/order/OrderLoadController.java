package com.example.rentiaserver.data.controllers.order;

import com.example.rentiaserver.data.helpers.PackagesWeightCounterHelper;
import com.example.rentiaserver.data.services.order.OrderService;
import com.example.rentiaserver.data.to.*;
import com.example.rentiaserver.ApplicationConstants;
import com.example.rentiaserver.data.helpers.OrderToCreatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@CrossOrigin
@RestController
@RequestMapping(value = OrderLoadController.BASE_ENDPOINT)
public final class OrderLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_ENDPOINT_PREFIX + "/announcements";


    private final OrderService announcementService;

    @Autowired
    public OrderLoadController(OrderService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping("/all")
    public List<AnnouncementTo> getAllAnnouncements() {
        List<AnnouncementTo> announcementTransferObjects = new LinkedList<>();
        announcementService.getAllAnnouncements()
                .forEach(announcement -> announcementTransferObjects.add(OrderToCreatorHelper.create(announcement)));
        return announcementTransferObjects;
    }

    @GetMapping("/announcement")
    public AnnouncementTo getAnnouncementById(@RequestParam Long announcementId) {
        return announcementService.getAnnouncementById(announcementId).map(OrderToCreatorHelper::create).orElse(null);
    }

    @GetMapping("/filtered")
    public List<AnnouncementTo> getFilteredAnnouncements(@RequestParam String initialAddress, @RequestParam String finalAddress,
                                                  @RequestParam String minimalSalary, @RequestParam String maxWeight,
                                                         @RequestParam String requireTransportWithClient, @RequestParam boolean sortBySalary,
                                                         @RequestParam boolean sortByWeight) {
        List<AnnouncementTo> announcementTos = announcementService
                .findAnnouncementsByAddresses(initialAddress, finalAddress, minimalSalary, requireTransportWithClient, sortBySalary)
                .stream().map(OrderToCreatorHelper::create).collect(Collectors.toList());
        List<AnnouncementTo> result;

        if (maxWeight != null && !maxWeight.isEmpty()) {
            result = new ArrayList<>();
            for (AnnouncementTo announcementTo: announcementTos) {
                BigDecimal weight = PackagesWeightCounterHelper.sumPackagesWeights(announcementTo.getPackages());
                if (weight.compareTo(new BigDecimal(maxWeight)) < 0) {
                    result.add(announcementTo);
                }
            }
        } else {
            result = new ArrayList<>(announcementTos);
        }

        if (sortByWeight) {
            result.sort(Comparator.comparing(announcementTo -> new BigDecimal(announcementTo.getWeight())));
        }

        return result;
    }

}
