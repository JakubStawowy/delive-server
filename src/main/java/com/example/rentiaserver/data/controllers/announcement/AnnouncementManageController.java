package com.example.rentiaserver.data.controllers.announcement;

import com.example.rentiaserver.data.dao.AnnouncementService;
import com.example.rentiaserver.data.dao.PackageRepository;
import com.example.rentiaserver.data.dao.UserRepository;
import com.example.rentiaserver.data.to.AnnouncementTo;
import com.example.rentiaserver.data.to.DeliveryAnnouncementTo;
import com.example.rentiaserver.data.to.NormalAnnouncementTo;
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
    public ResponseEntity<?> addDeliveryAnnouncement(@RequestBody DeliveryAnnouncementTo announcementTo) {

        Optional<UserPo> author = userRepository.findById(announcementTo.getAuthorId());
        if(author.isPresent()) {
            // TODO
            DeliveryAnnouncementPo announcement = new DeliveryAnnouncementPo(
                    new DestinationPo(announcementTo.getDestinationFrom().getLatitude(), announcementTo.getDestinationFrom().getLongitude()),
                    new DestinationPo(announcementTo.getDestinationTo().getLatitude(), announcementTo.getDestinationTo().getLongitude()),
                    author.get(),
                    LocalDateTime.parse(announcementTo.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            );
            announcementService.save(announcement);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = EndpointConstants.ADD_NORMAL_ANNOUNCEMENTS_ENDPOINT)
    public ResponseEntity<?> addNormalAnnouncement(@RequestBody NormalAnnouncementTo announcementTo) {

        Optional<UserPo> author = userRepository.findById(announcementTo.getAuthorId());

        if(author.isPresent()) {
            NormalAnnouncementPo announcement = new NormalAnnouncementPo(
                    new DestinationPo(announcementTo.getDestinationFrom().getLatitude(), announcementTo.getDestinationFrom().getLongitude()),
                    new DestinationPo(announcementTo.getDestinationTo().getLatitude(), announcementTo.getDestinationTo().getLongitude()),
                    author.get()
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

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = EndpointConstants.EDIT_ANNOUNCEMENT_ENDPOINT, consumes = "application/json")
    public ResponseEntity<?> editAnnouncement(@RequestBody AnnouncementTo announcementTransferObject, @PathVariable("id") Long id) {

        // TODO
        return null;
    }
}
