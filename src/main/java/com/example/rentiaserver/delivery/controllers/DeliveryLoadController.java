package com.example.rentiaserver.delivery.controllers;

import com.example.rentiaserver.constants.ApplicationConstants;
import com.example.rentiaserver.data.po.AnnouncementPo;
import com.example.rentiaserver.data.po.DestinationPo;
import com.example.rentiaserver.data.to.AnnouncementTo;
import com.example.rentiaserver.data.to.DestinationTo;
import com.example.rentiaserver.data.to.PackageTo;
import com.example.rentiaserver.delivery.dao.DeliveryRepository;
import com.example.rentiaserver.delivery.to.DeliveryTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = DeliveryManageController.BASE_ENDPOINT)
public class DeliveryLoadController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/delivery";

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryLoadController(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @GetMapping("/deliverer")
    public List<DeliveryTo> getUserDeliveries(@RequestParam Long userId) {
        List<DeliveryTo> result = new ArrayList<>();
        deliveryRepository.findAllByDeliverer(userId).forEach(deliveryPo -> {
            AnnouncementPo announcementPo = deliveryPo.getAnnouncement();
            DestinationPo destinationFrom = announcementPo.getDestinationFrom();
            DestinationPo destinationTo = announcementPo.getDestinationTo();
            Set<PackageTo> packages = new HashSet<>();
            announcementPo.getPackages().forEach(singlePackage -> packages.add(new PackageTo(
                    singlePackage.getPackageLength().toString(),
                    singlePackage.getPackageWidth().toString(),
                    singlePackage.getPackageHeight().toString()
            )));
            result.add(new DeliveryTo(
                    deliveryPo.getId(),
                    userId,
                    deliveryPo.getCreatedAt().toString(),
                    new AnnouncementTo(
                            announcementPo.getId(),
                            new DestinationTo(
                                    destinationFrom.getLatitude(),
                                    destinationFrom.getLongitude(),
                                    null
                            ),
                            new DestinationTo(
                                    destinationTo.getLatitude(),
                                    destinationTo.getLongitude(),
                                    null
                            ),
                            packages,
                            announcementPo.getAuthor().getId()
                    ),
                    deliveryPo.getDeliveryState().name()
            ));
        });
        return result;
    }

    @GetMapping("/principal")
    public List<DeliveryTo> getAllByPrincipal(@RequestParam Long userId) {
        List<DeliveryTo> result = new ArrayList<>();
        deliveryRepository.findAllByPrincipal(userId).forEach(deliveryPo -> {
            AnnouncementPo announcementPo = deliveryPo.getAnnouncement();
            DestinationPo destinationFrom = announcementPo.getDestinationFrom();
            DestinationPo destinationTo = announcementPo.getDestinationTo();
            Set<PackageTo> packages = new HashSet<>();
            announcementPo.getPackages().forEach(singlePackage -> packages.add(new PackageTo(
                    singlePackage.getPackageLength().toString(),
                    singlePackage.getPackageWidth().toString(),
                    singlePackage.getPackageHeight().toString()
            )));
            result.add(new DeliveryTo(
                    deliveryPo.getId(),
                    userId,
                    deliveryPo.getCreatedAt().toString(),
                    new AnnouncementTo(
                            announcementPo.getId(),
                            new DestinationTo(
                                    destinationFrom.getLatitude(),
                                    destinationFrom.getLongitude(),
                                    null
                            ),
                            new DestinationTo(
                                    destinationTo.getLatitude(),
                                    destinationTo.getLongitude(),
                                    null
                            ),
                            packages,
                            announcementPo.getAuthor().getId()
                    ),
                    deliveryPo.getDeliveryState().name()
            ));
        });
        return result;
    }

}
