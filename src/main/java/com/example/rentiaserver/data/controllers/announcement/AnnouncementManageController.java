package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.data.dao.AnnouncementService;
import com.example.rentiaserver.data.dao.PackageRepository;
import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.to.AnnouncementTo;
import com.example.rentiaserver.data.po.*;
import com.example.rentiaserver.constants.EndpointConstants;
import com.example.rentiaserver.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@CrossOrigin(origins = ApplicationConstants.Origins.LOCALHOST_ORIGIN)
@RestController
@RequestMapping(value = AnnouncementManageController.BASE_ENDPOINT)
public final class AnnouncementManageController {

    public static final String BASE_ENDPOINT = ApplicationConstants.Urls.BASE_API_URL + "/announcements";
    private final UserRepository userRepository;
    private final AnnouncementService announcementService;
    private final PackageRepository packageRepository;

    @Autowired
    public AnnouncementManageController(UserRepository userRepository, AnnouncementService announcementService, PackageRepository packageRepository) {
        this.userRepository = userRepository;
        this.announcementService = announcementService;
        this.packageRepository = packageRepository;
    }

    @PostMapping(value = EndpointConstants.ADD_DELIVERY_ANNOUNCEMENTS_ENDPOINT)
    public void addDeliveryAnnouncement(@RequestBody AnnouncementTo announcementTo) {
        userRepository.findById(announcementTo.getAuthorId()).ifPresent(author -> announcementService.save(new DeliveryAnnouncementPo(
                new DestinationPo(announcementTo.getDestinationFrom().getLatitude(), announcementTo.getDestinationFrom().getLongitude()),
                new DestinationPo(announcementTo.getDestinationTo().getLatitude(), announcementTo.getDestinationTo().getLongitude()),
                author,
                LocalDateTime.parse(announcementTo.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        )));
    }

    @PostMapping(value = EndpointConstants.ADD_NORMAL_ANNOUNCEMENTS_ENDPOINT)
    public void addNormalAnnouncement(@RequestBody AnnouncementTo announcementTo) {
        userRepository.findById(announcementTo.getAuthorId()).ifPresent(author -> saveAnnouncementWithPackages(author, announcementTo));
    }

    @PutMapping(value = EndpointConstants.EDIT_ANNOUNCEMENT_ENDPOINT)
    public void editAnnouncement(@RequestBody AnnouncementTo announcementTransferObject, @PathVariable("id") Long id) {
        // TODO
    }

    private void saveAnnouncementWithPackages(UserPo author, AnnouncementTo announcementTo) {
        NormalAnnouncementPo announcement = new NormalAnnouncementPo(
                new DestinationPo(announcementTo.getDestinationFrom().getLatitude(), announcementTo.getDestinationFrom().getLongitude()),
                new DestinationPo(announcementTo.getDestinationTo().getLatitude(), announcementTo.getDestinationTo().getLongitude()),
                author
        );
        announcementService.save(announcement);
        Set<PackagePo> packagePos = new HashSet<>();
        announcementTo.getPackages().forEach(packageTo ->
                packagePos.add(new AnnouncementPackagePo(
                        new BigDecimal(packageTo.getPackageLength()),
                        new BigDecimal(packageTo.getPackageWidth()),
                        new BigDecimal(packageTo.getPackageHeight()),
                        announcement
                )));
        packageRepository.saveAll(packagePos);
    }
}
